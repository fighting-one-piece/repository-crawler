package org.platform.crawler.webmagic.modules.proxyip;

import java.util.ArrayList;
import java.util.List;

import org.platform.crawler.utils.proxy.ProxyIPUtils;
import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class KxLaiLiIpCrawler extends GenericCrawler implements PageProcessor {
	private Site site = Site.me().setSleepTime(3000).setRetrySleepTime(3000);

	@Override
	public void process(Page page) {
		List<String> ip = page.getHtml().xpath("//*[@id='nav_btn01']/div[6]/table/tbody/tr/td[1]/text()").all();
		List<String> port = page.getHtml().xpath("//*[@id='nav_btn01']/div[6]/table/tbody/tr/td[2]/text()").all();
		List<String> time = page.getHtml().xpath("//*[@id='nav_btn01']/div[6]/table/tbody/tr/td[7]/text()").all();
		page.putField("ip", ip);
		page.putField("port", port);
		page.putField("time", time);

		List<String> urls = new ArrayList<String>();
		for (int i = 1; i < 11; i++) {
			String href = "/dailiip/1/" + i + ".html#ip";
			urls.add(href);
		}
		page.addTargetRequests(urls);
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void startCrawl() {
		Spider.create(new KxLaiLiIpCrawler())
				// IP没有更新
				.addUrl("http://www.kxdaili.com/dailiip.html").addPipeline(new kxdailiPipeline()).thread(3).run();
	}

	public static void main(String[] args) {
		new KxLaiLiIpCrawler().startCrawl();
	}

	class kxdailiPipeline implements Pipeline {

		@Override
		public void process(ResultItems resultItems, Task task) {
			List<String> ips = resultItems.get("ip");
			List<String> ports = resultItems.get("port");
			List<String> times = resultItems.get("time");
			for (int i = 0; i < ips.size(); i++) {
				String ip = ips.get(i);
				String port = ports.get(i);
				String time = times.get(i);
				String proxy = ip.trim() + ":" + port.trim();

				int day = 0;
				int hour = 0;
				int minute = 0;
				int second = 0;
				if (time.indexOf("天") > 0) {
					day = Integer.parseInt(time.substring(0, time.indexOf("天")));
					if (time.indexOf("小时") > 0) {
						hour = Integer.parseInt(time.substring(time.indexOf("天") + 1, time.indexOf("小时")));
					}
				} else if (time.indexOf("小时") > 0) {
					hour = Integer.parseInt(time.substring(0, time.indexOf("小时")));
					if (time.indexOf("分") > 0) {
						minute = Integer.parseInt(time.substring(time.indexOf("小时") + 2, time.indexOf("分")));
					}
				} else if (time.indexOf("分") > 0) {
					minute = Integer.parseInt(time.substring(0, time.indexOf("分")));
					if (time.indexOf("秒") > 0) {
						second = Integer.parseInt(time.substring(time.indexOf("分") + 1, time.indexOf("秒")));
					}
				}
				long longtime = day * 24 * 60 * 60 + hour * 60 * 60 + minute * 60 + second;
				System.out.println(proxy + " " + longtime);
				ProxyIPUtils.add(proxy, longtime);
			}
		}

	}

}
