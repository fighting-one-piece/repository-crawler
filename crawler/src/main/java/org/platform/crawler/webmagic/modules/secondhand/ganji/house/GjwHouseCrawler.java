package org.platform.crawler.webmagic.modules.secondhand.ganji.house;

import javax.annotation.Resource;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.platform.crawler.webmagic.scheduler.RedisScheduler;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;

@Component("gjwHouseCrawler")
public class GjwHouseCrawler extends GenericCrawler{


	@Resource(name = "gjwHousePipeline")
	private GjwHousePipeline gjwHousePipeline = null;
	
	@Resource(name = "redisScheduler")
	private RedisScheduler redisScheduler = null;
	
	@Override
	public void startCrawl() {
		Spider.create(new GjwHousePageProcessor())
		.addUrl("http://www.ganji.com/index.htm")		
		.setUUID("ganji.com.fang5")
		.setScheduler(redisScheduler)	
		.addPipeline(gjwHousePipeline)
		.thread(1)
		.run();				
		
	}
	
	public static void main(String[] args) {
		new GjwHouseCrawler().run();
	}
}
