package org.platform.crawler.webmagic.modules.proxyip;

import java.util.List;

import org.platform.crawler.utils.proxy.ProxyIP;
import org.platform.crawler.utils.proxy.ProxyIPUtils;
import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@ProxyIP
public class KaiXinDaiLiIpCrawler extends GenericCrawler implements
		PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
			.setTimeOut(1000);

	@Override
	public void process(Page page) {
		List<String> url = page.getHtml()
				.xpath("//*[@id='nav_btn01']/div[5]/div[1]/a/@href").all();
		List<String> IP = page
				.getHtml()
				.xpath("//*[@id='nav_btn01']/div[5]/table/tbody/tr/td[1]/text()")
				.all();
		page.putField("IP", IP);
		List<String> PORT = page
				.getHtml()
				.xpath("//*[@id='nav_btn01']/div[5]/table/tbody/tr/td[2]/text()")
				.all();
		page.putField("PORT", PORT);
		List<String> tIME = page
				.getHtml()
				.xpath("//*[@id='nav_btn01']/div[5]/table/tbody/tr/td[7]/text()")
				.all();
		page.putField("tIME", tIME);
		page.addTargetRequests(url);
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void startCrawl() {
		String urll = "http://www.kxdaili.com/";
		Spider.create(new KaiXinDaiLiIpCrawler()).thread(1)
				.addPipeline(new KaiXinDaiLiIpPinpine()).addUrl(urll).run();
	}

	public static void main(String[] args) {
		new KaiXinDaiLiIpCrawler().startCrawl();
	}
}

class KaiXinDaiLiIpPinpine implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {
		List<String> ips = resultItems.get("IP");
		List<String> ports = resultItems.get("PORT");
		List<String> time = resultItems.get("tIME");

		for (int i = 0; i < ips.size(); i++) {
			long tim = 0;
			String ip = ips.get(i);
			String prot = ports.get(i);
			String ipp = ip.trim() + ":" + prot.trim();
			String times = time.get(i);
			if (times.indexOf("秒") > 0) {
				int minute = Integer.parseInt(times.substring(0,
						times.indexOf("分")));
				int second = Integer.parseInt(times.substring(
						times.indexOf("分") + 1, times.indexOf("秒")));
				tim = minute * 60 + second;
			} else {
				int minute = Integer.parseInt(times.substring(0,
						times.indexOf("分")));
				tim = minute * 60;
			}
//			System.out.println(ipp+"时间：  "+tim);
			ProxyIPUtils.add(ipp, tim);
		}
	}

}