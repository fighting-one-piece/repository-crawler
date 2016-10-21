package org.platform.crawler.webmagic.modules.weibo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderMonitor.MonitorSpiderListener;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

@Component("weiboCrawler")
public class WeiBoCrawler extends GenericCrawler {
	
	@Override
	public void startCrawl() {
		try {
			String url = "https://api.weibo.com/2/statuses/public_timeline.json";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("access_token", "2.00saxW1EWTS4UBfdc4c1be32lyEqzB");
			params.put("count", 50);
			params.put("page", 1);
			params.put("base_app", 0);
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
			url = url + "?" + sb.toString();
			Spider weiBoSpider = Spider.create(new WeiBoPageProcessor())
	                .addUrl(url).addPipeline(new ConsolePipeline()).thread(1);
			SpiderMonitor.instance().register(weiBoSpider);
			weiBoSpider.run();
			List<SpiderListener> spiderListeners = weiBoSpider.getSpiderListeners();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new WeiBoCrawler().run();
	}

}
