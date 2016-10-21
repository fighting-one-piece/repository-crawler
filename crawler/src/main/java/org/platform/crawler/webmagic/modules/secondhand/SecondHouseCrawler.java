package org.platform.crawler.webmagic.modules.secondhand;

import java.util.List;

import javax.management.JMException;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderMonitor.MonitorSpiderListener;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

@Component("secondHouseCrawler")
public class SecondHouseCrawler extends GenericCrawler {
	
	public void startCrawl() {
		Spider secondHouseSpider = Spider.create(new SecondHousePageProcessor())
                .addUrl("http://cd.58.com/ershoufang/27316170689356x.shtml?PGTID=0d30000c-0006-659c-81ea-6ffcf46276bd&entinfo=27316170689356_0&local=102&apptype=0&psid=198147338193133825554657148&iuType=gz_2&ClickID=6&pubid=2873491&PGTID=0d30000c-0006-659c-81ea-6ffcf46276bd&entinfo=27316170689356_0&local=102&apptype=0&psid=198147338193133825554657148&iuType=gz_2&ClickID=6&pubid=2873491&trackkey=27316170689356_8d28b0f4-96c3-413e-9119-452391c2c6c0_20160910155619_1473494179231&fcinfotype=gz")
                .addPipeline(new ConsolePipeline())
                .thread(1);
		try {
			SpiderMonitor.instance().register(secondHouseSpider);
		} catch (JMException e) {
			e.printStackTrace();
		}
		secondHouseSpider.run();
		List<SpiderListener> spiderListeners = secondHouseSpider.getSpiderListeners();
		for (SpiderListener spiderListener : spiderListeners) {
			if (spiderListener instanceof MonitorSpiderListener) {
				MonitorSpiderListener monitorSpiderListener = (MonitorSpiderListener) spiderListener;
				System.out.println("success count: " + monitorSpiderListener.getSuccessCount());
				System.out.println("error count: " + monitorSpiderListener.getErrorCount());
				System.out.println("error urls: ");
				for (String errorUrl : monitorSpiderListener.getErrorUrls()) {
					System.out.println(errorUrl);
				}
			}
		}
	}

	public static void main(String[] args) {
		new SecondHouseCrawler().run();
	}
}
