package org.platform.crawler.webmagic.modules.company;

import java.util.ArrayList;
import java.util.List;

import javax.management.JMException;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderMonitor.MonitorSpiderListener;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component("tianYanChaCrawler")
public class TianYanChaCrawler extends GenericCrawler {
	
	public void startCrawl() {
//		String initialize_url = "http://www.tianyancha.com/search/%E5%9B%9B%E5%B7%9D%E7%A7%91%E9%9A%86%E5%BB%BA%E8%AE%BE%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8?checkFrom=searchBox";
		String initialize_url = "http://www.tianyancha.com/search/%E5%9B%9B%E5%B7%9D%E7%A7%91%E9%9A%86%E5%BB%BA%E8%AE%BE%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8.json?&pn=1";
		List<Pipeline> pipelines = new ArrayList<Pipeline>();
		pipelines.add(new ConsolePipeline());
		Spider jobSpider = Spider.create(new TianYanChaPageProcessor())
                .addUrl(initialize_url)
                .setPipelines(pipelines)
                .thread(6);
		try {
			SpiderMonitor.instance().register(jobSpider);
		} catch (JMException e) {
			e.printStackTrace();
		}
		jobSpider.run();
		List<SpiderListener> spiderListeners = jobSpider.getSpiderListeners();
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
		new TianYanChaCrawler().run();
	}
	
}
