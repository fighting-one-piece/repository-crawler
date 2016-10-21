package org.platform.crawler.webmagic.modules.weibo;

import java.util.List;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderMonitor.MonitorSpiderListener;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@Component("weiboAndroidUploadCrawler")
public class WeiBoAndroidUploadCrawler extends GenericCrawler {
	
	@Override
	public void startCrawl() {
		try {
			String url = "http://api.weibo.cn/2/contacts/upload/sync?networktype=wifi&moduleID=700&c=android&i=7755a8d&s=004e2da7&ua=Xiaomi-MI%204W__weibo__6.10.0__android__android6.0.1&wm=20005_0002&aid=01AnGTS9XIhQaoiCsNOGhMLrldXoCgY30adtxiXHRUCThells.&uid=3967913626&v_f=2&ctag=0&from=106A095010&gsid=_2A256_mH6DeTxGeVH7VUY8S3KyTqIHXVXqvIyrDV6PUJbkdAKLXinkWpjbfsmiwU4LbU7DZNKISFGmTecDA..&lang=zh_CN&skin=default&devid=864895026835080&oldwm=20005_0002&sflag=1";
			Spider weiBoAndroidUploadSpider = Spider.create(new WeiBoAndroidUploadPageProcessor())
	                .addUrl(url).addPipeline(new ConsolePipeline()).thread(10);
			SpiderMonitor.instance().register(weiBoAndroidUploadSpider);
			weiBoAndroidUploadSpider.run();
			List<SpiderListener> spiderListeners = weiBoAndroidUploadSpider.getSpiderListeners();
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
		new WeiBoAndroidUploadCrawler().run();
	}

}

class WeiBoAndroidUploadPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);

	@Override
	public void process(Page page) {
		String jsonStr = page.getJson().get();
		System.out.println(jsonStr);
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	
}
