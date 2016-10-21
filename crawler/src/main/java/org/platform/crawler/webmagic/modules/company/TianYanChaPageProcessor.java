package org.platform.crawler.webmagic.modules.company;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class TianYanChaPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000)
			.addHeader("referer", "http://www.tianyancha.com/search/%E5%9B%9B%E5%B7%9D%E7%A7%91%E9%9A%86%E5%BB%BA%E8%AE%BE%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8?checkFrom=searchBox")
			.addCookie("TYCID", "0fedebe3ab994d69b67d40c639985423")
			.addCookie("tnet", "218.88.28.105")
			.addCookie("_pk_ref.1.e431", "%5B%22%22%2C%22%22%2C1473731100%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DmCug1-v66Hzp8v1SNah9udpyoy9VrdWlLw8hovXrIKjSRIXRCrDk81WkvKJJeSJG%26wd%3D%26eqid%3D9762e6ea000121060000000557d75a14%22%5D")
			.addCookie("Hm_lvt_e92c8d65d92d534b0fc290df538b4758", "1473731100")
			.addCookie("Hm_lpvt_e92c8d65d92d534b0fc290df538b4758", "1473731100")
			.addCookie("_pk_id.1.e431", "6a892f3a230792bc.1473731100.1.1473731121.1473731100.")
			.addCookie("_pk_ses.1.e431", "*")
			.addCookie("token", "a16fa650bc134370a5f38411920e8345")
			.addCookie("_utm", "0cbdd37a0d7346cd87b092651514d88c")
//			.setHttpProxy(new HttpHost("182.92.157.49", 80))
			.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");

	@Override
	public void process(Page page) {
		System.out.println(page);
		System.out.println(page.getJson());
	}

	@Override
	public Site getSite() {
		return site;
	}

	
}
