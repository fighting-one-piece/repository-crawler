package org.platform.crawler.webmagic.modules.secondhand.tongcheng.house;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.platform.crawler.webmagic.scheduler.RedisScheduler;
import org.springframework.stereotype.Component;




import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;


@Component("HouseCrawler")
public class HouseCrawler extends GenericCrawler{
	@Resource(name = "HousePippine")
	private HousePippine housePippine = null;
	@Resource(name = "redisScheduler")
	private RedisScheduler redisScheduler = null;
	@Override
	public void startCrawl() {
		String url = "http://www.58.com/ershoufang/changecity/";
		List<Pipeline> pipelines = new ArrayList<Pipeline>();
		pipelines.add(new ConsolePipeline());
		pipelines.add(housePippine);
		Spider houseSpider =Spider.create(new House()).addUrl(url)
				.setUUID("58.com.ershoufang")
				.setScheduler(redisScheduler).setPipelines(pipelines).thread(4);
		System.out.println(houseSpider);
				houseSpider.run();

	}
	
	public static void main(String[] args) {
		new HouseCrawler().run();
	
	}

}
