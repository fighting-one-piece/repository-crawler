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

@Component("secondCarCrawler")
public class SecondCarCrawler extends GenericCrawler {
	
	public void startCrawl() {
		Spider secondCarSpider = Spider.create(new SecondCarPageProcessor())
                .addUrl("http://cd.58.com/ershouche/pn1/?utm_source=market&spm=b-31580022738699-me-f-824.bdpz_biaoti")
                .addPipeline(new ConsolePipeline())
                .thread(1);
		try {
			SpiderMonitor.instance().register(secondCarSpider);
		} catch (JMException e) {
			e.printStackTrace();
		}
		secondCarSpider.run();
		List<SpiderListener> spiderListeners = secondCarSpider.getSpiderListeners();
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
		new SecondCarCrawler().run();
	}
}
