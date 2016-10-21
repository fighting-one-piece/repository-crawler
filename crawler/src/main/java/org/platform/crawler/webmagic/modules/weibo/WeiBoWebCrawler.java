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
import com.google.gson.internal.LinkedTreeMap;

@Component("weiboWebCrawler")
public class WeiBoWebCrawler extends GenericCrawler {
	
	@Override
	public void startCrawl() {
		try {
			String url = "http://m.weibo.cn/index/feed?format=cards";
			Spider weiBoWebSpider = Spider.create(new WeiBoWebPageProcessor())
	                .addUrl(url).addPipeline(new ConsolePipeline()).thread(1);
			SpiderMonitor.instance().register(weiBoWebSpider);
			weiBoWebSpider.run();
			List<SpiderListener> spiderListeners = weiBoWebSpider.getSpiderListeners();
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
		new WeiBoWebCrawler().run();
	}

}

class WeiBoWebPageProcessor implements PageProcessor {
	
	String cookie = "_T_WM=c0a201ef32a2aa921133651ce8c4f7db; WEIBOCN_WM=3349; H5_wentry=H5; backURL=http%3A%2F%2Fm.weibo.cn%2F; SUB=_2A2567JwcDeTxGeVH7VUY8S3KyTqIHXVWLiRUrDV6PUJbkdBeLWKjkW0MnEpsejoYM2HaVzW2s6An65iX7g..; SUHB=08JrTsKLs-V7M3; SCF=Ag-4BDFzlevT8zA-LAsNZimZInFvfUn-pNnqdn1bPsvHYgQmMR7wc-TtqbCb833Nn_dyHUHEvdehtoEFRCb3BO8.; SSOLoginState=1474882636; H5_INDEX=0_all; H5_INDEX_TITLE=%E8%A6%8B%E5%88%9D%E5%A6%82%E5%8F%AA%E8%8B%A5%E7%94%9F%E4%BB%8A; M_WEIBOCN_PARAMS=uicode%3D20000174";
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000)
			.addCookie("Cookie", cookie);

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		String jsonStr = page.getJson().get();
		System.out.println(jsonStr);
		Gson gson = new Gson();
		List<Object> list = gson.fromJson(jsonStr, List.class);
		LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) list.get(0);
		for (Map.Entry<String, Object> entry : linkedTreeMap.entrySet()) {
			if ("card_group".equalsIgnoreCase(entry.getKey())) {
				List<Map<String, Object>> cards = (List<Map<String, Object>>) entry.getValue();
				for (Map<String, Object> card : cards) {
					System.out.println("==========");
					for (Map.Entry<String, Object> card_entry : card.entrySet()) {
						System.out.println(card_entry.getKey() + ":" + card_entry.getValue());
					}
				}
			}
		}
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	
}
