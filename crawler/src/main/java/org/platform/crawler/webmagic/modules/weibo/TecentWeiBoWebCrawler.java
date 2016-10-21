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

@Component("tecentWeiBoWebCrawler")
public class TecentWeiBoWebCrawler extends GenericCrawler {
	
	@Override
	public void startCrawl() {
		try {
			String url = "http://api.t.qq.com/asyn/home.php?&time=1468559743&page=1&id=482484086165391&isrecom=0&apiType=14&apiHost=http%3A%2F%2Fapi.t.qq.com&_r=1475152101369&g_tk=283219015";
			Spider tecentWeiBoWebSpider = Spider.create(new TecentWeiBoWebPageProcessor())
	                .addUrl(url).addPipeline(new ConsolePipeline()).thread(1);
			SpiderMonitor.instance().register(tecentWeiBoWebSpider);
			tecentWeiBoWebSpider.run();
			List<SpiderListener> spiderListeners = tecentWeiBoWebSpider.getSpiderListeners();
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
		new TecentWeiBoWebCrawler().run();
	}

}

class TecentWeiBoWebPageProcessor implements PageProcessor {
	
	String cookie = "tvfe_boss_uuid=79f00b58115ca0a7; AMCV_248F210755B762187F000101%40AdobeOrg=793872103%7CMCIDTS%7C16953%7CMCMID%7C66043914376763604261923396171884197480%7CMCAAMLH-1465275557%7C11%7CMCAAMB-1465275557%7CNRX38WO0n5BH8Th-nqAG_A%7CMCAID%7CNONE; pac_uid=1_125906088; ptcz=9144862ee33d9a30089d6135cd94fdda6a64126f1fcd49ecc8d5df3289410284; pgv_pvi=9364808704; RK=DRHXD9GnNs; pgv_si=s2768324608; wb_regf=%3B0%3B%3Bwww.baidu.com%3B0; mb_reg_from=8; wbilang_10000=zh_CN; ts_refer=www.baidu.com/link; ptisp=ctc; luin=o0125906088; lskey=000100009a53154ea26f6d99de2189e8e321be59bcf4c6a5df831e0eda676b81e1258cb61581b7df5143b70d; pt2gguin=o0125906088; uin=o0125906088; skey=@mf4eIoB2K; p_uin=o0125906088; p_skey=38q03yP5d7FSAAERBbAlbb9gTG5etqRIVDI4iz1OLY0_; pt4_token=NuiP7B8FkMlc*7ydhCZMEU4qVF3tsROqNtCmUyegnew_; p_luin=o0125906088; p_lskey=00040000909db64578fcb6a89c88dc1a7db26f2b204482f9029df69f6300da41c9e525f4963ee18add1e43e7; wbilang_125906088=zh_CN; pgv_info=ssid=s4510562931; ts_last=t.qq.com/wulin_19880406; pgv_pvid=512697556; o_cookie=125906088; ts_uid=7948881296";
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000)
			.addCookie("Cookie", cookie)
			.addHeader("Referer", "http://api.t.qq.com/proxy.html")
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		String jsonStr = page.getJson().get();
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(jsonStr, Map.class);
		if (map.containsKey("info")) {
			Map<String, Object> info = (Map<String, Object>) map.get("info");
			System.out.println(info.get("user"));
			System.out.println(info.get("time"));
			if (info.containsKey("talk")) {
				List<Map<String, Object>> talks = (List<Map<String, Object>>) info.get("talk");
				for (Map<String, Object> talk : talks) {
					System.out.println("##########################################");
					System.out.println(talk);
					System.out.println("tid: " + talk.get("tid"));
					System.out.println("name: " + talk.get("name"));
					System.out.println("nick: " + talk.get("nick"));
					System.out.println("content: " + talk.get("content"));
					System.out.println("timestamp: " + talk.get("timestamp"));
//					for (Map.Entry<String, Object> entry : talk.entrySet()) {
//						System.out.println(entry.getKey() + " : " + entry.getValue());
//						if ("content".equalsIgnoreCase(entry.getKey())) {
//							System.out.println(entry.getValue());
//						}
//					}
				}
			}
		}
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	
}
