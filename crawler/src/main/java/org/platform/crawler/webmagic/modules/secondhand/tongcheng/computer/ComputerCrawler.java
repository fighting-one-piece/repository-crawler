package org.platform.crawler.webmagic.modules.secondhand.tongcheng.computer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.platform.crawler.webmagic.scheduler.RedisScheduler;
import org.springframework.stereotype.Component;





import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;


@Component("computerCrawler")
public class ComputerCrawler extends GenericCrawler{
	@Resource(name = "computerPipeline")
	private ComputerPipeline computerPipeline = null;
	@Resource(name = "redisScheduler")
	private RedisScheduler redisScheduler = null;
	@Override
	public void startCrawl() {
		String url = "http://www.58.com/diannao/changecity/";
		List<Pipeline> pipelines = new ArrayList<Pipeline>();
		pipelines.add(new ConsolePipeline());
		pipelines.add(computerPipeline);
		Spider.create(new Computer()).addUrl(url)
				.setUUID("58.com.diannao")
				.setScheduler(redisScheduler)
				.setPipelines(pipelines).thread(4).run();
		
		
	}
	
	public static void main(String[] args) {
		new ComputerCrawler().run();
	
	}
	
}
