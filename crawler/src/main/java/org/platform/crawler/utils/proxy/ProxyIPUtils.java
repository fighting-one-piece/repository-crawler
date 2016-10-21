package org.platform.crawler.utils.proxy;

import java.io.IOException;
import java.util.Set;

import org.platform.crawler.utils.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyIPUtils {
	
	private final static Logger LOG = LoggerFactory.getLogger(ProxyIPUtils.class);
	
	private static final String PROXY_ID_QUEUE = "proxy_id_queue";

	private ProxyIPUtils(){
		
	}
	
	/**
	 * 新增代理IP
	 * @param proxy 格式 IP:PORT
	 * @param usedTime 已使用时间
	 */
	public static void add(String proxy, long usedTime) {
		try {
			RedisUtils.getInstance().zAdd(PROXY_ID_QUEUE, 
				Double.parseDouble(String.valueOf(usedTime)), proxy, 500, false);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} 
	}
	
	/**
	 * 获取得分区间代理IP集合
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> get(double start, double end) {
		try {
			return RedisUtils.getInstance().zRangeByScoreNoSerialize(PROXY_ID_QUEUE, start, end, false);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 获取排名区间代理IP集合
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> get(int start, int end) {
		try {
			return RedisUtils.getInstance().zRangeNoSerialize(PROXY_ID_QUEUE, start, end);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 指定值删除
	 * @param value
	 * @return
	 */
	public static long remove(Object value) {
		try {
			return RedisUtils.getInstance().zRemByValue(PROXY_ID_QUEUE, value);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return 0;
	}
	
	/**
	 * 排名区间删除
	 * @param start
	 * @param end
	 */
	public static void removeByRank(int start, int end) {
		try {
			RedisUtils.getInstance().zRemRangeByRank(PROXY_ID_QUEUE, start, end);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 得分区间删除
	 * @param start
	 * @param end
	 */
	public static void removeByScore(double start, double end) {
		try {
			RedisUtils.getInstance().zRemRangeByScore(PROXY_ID_QUEUE, start, end);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	
}
