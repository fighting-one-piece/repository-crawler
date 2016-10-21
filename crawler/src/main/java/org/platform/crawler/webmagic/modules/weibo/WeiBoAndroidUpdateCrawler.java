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

@Component("weiboAndroidUpdateCrawler")
public class WeiBoAndroidUpdateCrawler extends GenericCrawler {
	
	@Override
	public void startCrawl() {
		try {
			String url = "http://api.weibo.cn/2/account/update_privacy?networktype=wifi&mobile=0&uicode=10000048&moduleID=700&c=android&i=7755a8d&s=004e2da7&ua=Xiaomi-MI%204W__weibo__6.10.0__android__android6.0.1&wm=20005_0002&aid=01AnGTS9XIhQaoiCsNOGhMLrldXoCgY30adtxiXHRUCThells.&v_f=2&from=106A095010&gsid=_2A256_mH6DeTxGeVH7VUY8S3KyTqIHXVXqvIyrDV6PUJbkdAKLXinkWpjbfsmiwU4LbU7DZNKISFGmTecDA..&lang=zh_CN&lfid=230646&skin=default&oldwm=20005_0002&sflag=1&luicode=10000011&extend_contact_list=0";
			Spider weiBoAndroidUpdateSpider = Spider.create(new WeiBoAndroidUpdatePageProcessor())
	                .addUrl(url).addPipeline(new ConsolePipeline()).thread(10);
			SpiderMonitor.instance().register(weiBoAndroidUpdateSpider);
			weiBoAndroidUpdateSpider.run();
			List<SpiderListener> spiderListeners = weiBoAndroidUpdateSpider.getSpiderListeners();
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
		new WeiBoAndroidUpdateCrawler().run();
	}

}

class WeiBoAndroidUpdatePageProcessor implements PageProcessor {
	
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
