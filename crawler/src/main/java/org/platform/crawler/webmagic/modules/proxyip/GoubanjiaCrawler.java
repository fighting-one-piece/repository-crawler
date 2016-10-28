package org.platform.crawler.webmagic.modules.proxyip;

import java.util.ArrayList;
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
public class GoubanjiaCrawler extends GenericCrawler implements PageProcessor {
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
			.setUserAgent("spider").setTimeOut(10 * 1000);

	@Override
	public void process(Page page) {
		List<String> ls = new ArrayList<String>();
		List<String> list = page.getHtml()
				.css("#list > table > tbody > tr > td.ip").all();
		for (String string : list) {
			String s = string.replace(" ", "").replace("\n", "");
			String[] w = s.split(">");
			StringBuffer sf = new StringBuffer();
			for (int i = 1; i < w.length; i++) {
				if (w[i].indexOf("< ") > 0 || w[i - 1].indexOf("none") == -1) {
					String ee = w[i].substring(0, w[i].indexOf("<"));
					if (ee.trim().length() > 0) {
						sf.append(ee);
					}
				}
			}
			ls.add(sf.toString());
		}
		page.putField(
				"time",
				page.getHtml()
						.xpath("//*[@id='list']/table/tbody/tr/td[9]/text()")
						.all());
		page.putField(
				"port",
				page.getHtml()
						.xpath("//*[@id='list']/table/tbody/tr/td[2]/text()")
						.all());
		page.putField("ip", ls);
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void startCrawl() {
		Spider.create(new GoubanjiaCrawler())
				.addUrl("http://www.goubanjia.com/")
				.addPipeline(new goubanjiaPipeline()).thread(1).run();

	}
}

class goubanjiaPipeline implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {
		List<String> ip = resultItems.get("ip");
		List<String> port = resultItems.get("port");
		List<String> time = resultItems.get("time");

		for (int i = 0; i < ip.size(); i++) {

			long tim = 0;
			if (time.get(i).indexOf("分") > 0) {
				tim = Integer.parseInt(time.get(i).substring(0,
						time.get(i).indexOf("分"))) * 60;
			} else if (time.get(i).indexOf("秒") > 0) {
				tim = Integer.parseInt(time.get(i).substring(0,
						time.get(i).indexOf("秒")));
			}

			String ipp = ip.get(i) + ":" + port.get(i);
			 ProxyIPUtils.add(ipp, tim);
		}

	}

}
