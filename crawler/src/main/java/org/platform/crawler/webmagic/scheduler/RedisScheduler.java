package org.platform.crawler.webmagic.scheduler;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

import com.alibaba.fastjson.JSON;

@Component("redisScheduler")
public class RedisScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler, DuplicateRemover {

	private Logger LOG = LoggerFactory.getLogger(RedisScheduler.class);
	
	@Resource(name = "jedisPool")
	private JedisPool jedisPool = null;

	private static final String QUEUE_PREFIX = "queue_";

	private static final String SET_PREFIX = "set_";

	private static final String ITEM_PREFIX = "item_";

	public RedisScheduler() {
		setDuplicateRemover(this);
	}

	public RedisScheduler(String host) {
		this(new JedisPool(new JedisPoolConfig(), host));
	}

	public RedisScheduler(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
		setDuplicateRemover(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void resetDuplicateCheck(Task task) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(getSetKey(task));
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isDuplicate(Request request, Task task) {
		Jedis jedis = jedisPool.getResource();
		try {
			boolean isDuplicate = jedis.sismember(getSetKey(task),
					request.getUrl());
			if (!isDuplicate) {
				jedis.sadd(getSetKey(task), request.getUrl());
			}
			return isDuplicate;
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void pushWhenNoDuplicate(Request request, Task task) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.rpush(getQueueKey(task), request.getUrl());
			if (request.getExtras() != null) {
				String field = DigestUtils.shaHex(request.getUrl());
				String value = JSON.toJSONString(request);
				jedis.hset((ITEM_PREFIX + task.getUUID()), field, value);
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public synchronized Request poll(Task task) {
		Jedis jedis = jedisPool.getResource();
		try {
			String url = jedis.lpop(getQueueKey(task));
			if (url == null) {
				return null;
			}
			String key = ITEM_PREFIX + task.getUUID();
			String field = DigestUtils.shaHex(url);
			byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
			if (bytes != null) {
				Request o = JSON.parseObject(new String(bytes), Request.class);
				return o;
			}
			Request request = new Request(url);
			return request;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	protected String getSetKey(Task task) {
		return SET_PREFIX + task.getUUID();
	}

	protected String getQueueKey(Task task) {
		return QUEUE_PREFIX + task.getUUID();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getLeftRequestsCount(Task task) {
		Jedis jedis = jedisPool.getResource();
		try {
			Long size = jedis.llen(getQueueKey(task));
			int leftRequestsCount = size.intValue();
			LOG.info("left requests count {}", leftRequestsCount);
			return leftRequestsCount;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getTotalRequestsCount(Task task) {
		Jedis jedis = jedisPool.getResource();
		try {
			Long size = jedis.scard(getSetKey(task));
			int totalRequestsCount = size.intValue();
			LOG.info("total requests count {}", totalRequestsCount);
			return totalRequestsCount;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

}
