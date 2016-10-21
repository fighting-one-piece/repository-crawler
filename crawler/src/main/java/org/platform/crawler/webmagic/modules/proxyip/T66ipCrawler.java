package org.platform.crawler.webmagic.modules.proxyip;

import java.util.ArrayList;
import java.util.Calendar;
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
public class T66ipCrawler extends GenericCrawler implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(5).setSleepTime(1000);

	@Override
	public void process(Page page) {
		List<String> url = new ArrayList<String>();

		for (int i = 2; i < 6; i++) {
			url.add("http://www.66ip.cn/" + i + ".html");
		}
		page.addTargetRequests(url);
		page.putField(
				"ip",
				page.getHtml()
						.xpath("//*[@id='main']/div/div[1]/table/tbody/tr/td[1]/text()")
						.all());
		page.putField(
				"port",
				page.getHtml()
						.xpath("//*[@id='main']/div/div[1]/table/tbody/tr/td[2]/text()")
						.all());
		page.putField(
				"time",
				page.getHtml()
						.xpath("//*[@id='main']/div/div[1]/table/tbody/tr/td[5]/text()")
						.all());

	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void startCrawl() {
		String url = "http://www.66ip.cn/";
		Spider.create(new T66ipCrawler()).addUrl(url)
				.addPipeline(new T66ipCrawlerPipeline()).thread(5).run();
	}

	public static void main(String[] args) {
		new T66ipCrawler().startCrawl();
	}

}

class T66ipCrawlerPipeline implements Pipeline {
	@Override
	public void process(ResultItems resultItems, Task task) {
		List<String> ip = resultItems.get("ip");
		List<String> port = resultItems.get("port");
		List<String> times = resultItems.get("time");

		if (ip != null && port != null && times != null) {
			if (ip.size() > 1) {
				for (int i = 1; i < ip.size(); i++) {
					String ipp = ip.get(i).trim() + ":" + port.get(i).trim();
					Calendar now = Calendar.getInstance();
					String time = times.get(i);
					int year = now.get(Calendar.YEAR)
							- Integer.parseInt(time.substring(0,
									time.indexOf("年")));
					int month = now.get(Calendar.MONTH)
							+ 1
							- Integer.parseInt(time.substring(
									time.indexOf("年") + 1, time.indexOf("月")));
					int day = now.get(Calendar.DAY_OF_MONTH)
							- Integer.parseInt(time.substring(
									time.indexOf("月") + 1, time.indexOf("日")));
					int hour = now.get(Calendar.HOUR_OF_DAY)
							- Integer.parseInt(time.substring(
									time.indexOf("日") + 1, time.indexOf("时")));
					long tim = year * 31536000 + month * 2592000 + day * 86400
							+ hour * 3600;
					ProxyIPUtils.add(ipp, tim);
				}
			}

		}

	}

}