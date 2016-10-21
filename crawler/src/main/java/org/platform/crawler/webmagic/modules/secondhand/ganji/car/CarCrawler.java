package org.platform.crawler.webmagic.modules.secondhand.ganji.car;

import javax.annotation.Resource;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.platform.crawler.webmagic.modules.secondhand.ganji.car.pipeline.CarPipeline;
import org.platform.crawler.webmagic.scheduler.RedisScheduler;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;

@Component("carCrawler")
public class CarCrawler extends GenericCrawler{
	
	@Resource(name = "carPipeline")
	private CarPipeline carPipeline = null;
	
	@Resource(name = "redisScheduler")
	private RedisScheduler redisScheduler = null;

	@Override
	public void startCrawl() {
		Spider.create(new CarPageProcessor())
		.addUrl("http://www.ganji.com/index.htm")
		.setUUID("ganji.com.ershouche")
		.addPipeline(carPipeline)
		.setScheduler(redisScheduler)
		.thread(6)
		.run();
	}
	
	public static void main(String[] args) {
		new CarCrawler().run();
	}

}
