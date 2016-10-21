package org.platform.crawler.webmagic.modules.weibo;

import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;

@Component("weiboAndroidCrawler")
public class WeiBoAndroidCrawler extends GenericCrawler {
	
	@Override
	public void startCrawl() {
		try {
//			String url = "http://api.weibo.cn/2/contacts/get_contact_weibo?networktype=wifi&moduleID=700&user_id=5750853910&c=android&i=6190ba0&s=e6be344d&ua=Meizu-m2%20note__weibo__6.10.0__android__android5.1&wm=9848_0009&aid=01Agxg6ZhpO-AMlgCZgKzZYThMpB3RE7A9D72NKvgXHp4Fyus.&v_f=2&from=106A095010&gdid=867465022700642&gsid=_2A2567_utDeTxGeNJ7lIZ9S3FyjyIHXVXvQhlrDV6PUJbi9AKLUnTkWpRDmt7kjn8pKeWtjBXTXynxrgZNw..&lang=zh_CN&skin=default&oldwm=9848_0009&sflag=1&has_member=1";
			String url = "http://api.weibo.cn/2/contacts/get_contact_weibo?networktype=wifi&moduleID=700&user_id=3967913626&c=android&i=7755a8d&s=004e2da7&ua=Xiaomi-MI%204W__weibo__6.10.0__android__android6.0.1&wm=20005_0002&aid=01AnGTS9XIhQaoiCsNOGhMLrldXoCgY30adtxiXHRUCThells.&v_f=2&from=106A095010&gdid=864895026835080&gsid=_2A256_mH6DeTxGeVH7VUY8S3KyTqIHXVXqvIyrDV6PUJbkdAKLXinkWpjbfsmiwU4LbU7DZNKISFGmTecDA..&lang=zh_CN&skin=default&oldwm=20005_0002&sflag=1&has_member=1";
			Spider weiBoAndroidSpider = Spider.create(new WeiBoAndroidPageProcessor())
	                .addUrl(url).addPipeline(new ConsolePipeline()).thread(10);
			SpiderMonitor.instance().register(weiBoAndroidSpider);
			weiBoAndroidSpider.run();
			List<SpiderListener> spiderListeners = weiBoAndroidSpider.getSpiderListeners();
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
		new WeiBoAndroidCrawler().run();
	}

}

class WeiBoAndroidPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		String jsonStr = page.getJson().get();
//		System.out.println(jsonStr);
		Gson gson = new Gson();
		Map<String, Object> result = gson.fromJson(jsonStr, Map.class);
		List<Map<String, Object>> datas = (List<Map<String, Object>>) result.get("data");
		for (int i = 0, len = datas.size(); i < len; i++) {
			Map<String, Object> data = datas.get(i);
//			System.out.println(data.get("name"));
			if (!data.containsKey("full")) continue;
			Map<String, Object> full = gson.fromJson((String) data.get("full"), Map.class);
			if (null == full || full.isEmpty()) continue;
			for (Map.Entry<String, Object> entry : full.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
		}
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	
}
