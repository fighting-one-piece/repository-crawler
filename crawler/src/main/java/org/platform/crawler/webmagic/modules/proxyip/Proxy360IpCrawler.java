package org.platform.crawler.webmagic.modules.proxyip;

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

public class Proxy360IpCrawler extends GenericCrawler implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);

	@Override
	public void process(Page page) {
		List<String> ips = page.getHtml().xpath("//div[@class='proxylistitem']/div[1]/span[1]/text()").all();
		List<String> ports = page.getHtml().xpath("//div[@class='proxylistitem']/div[1]/span[2]/text()").all();
		List<String> usedTimes = page.getHtml().xpath("//div[@class='proxylistitem']/div[1]/span[8]/text()").all();
		page.putField("ips", ips);
		page.putField("ports", ports);
		page.putField("usedTimes", usedTimes);
		List<String> urls = page.getHtml().links().regex("http://www.proxy360.cn/Region/\\w+").all();
		page.addTargetRequests(urls);
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void startCrawl() {
		Spider.create(new Proxy360IpCrawler())
		//IP没有更新
		.addUrl("http://www.proxy360.cn/default.aspx")
		.addPipeline(new Proxy360Pipeline())
		.thread(3)
		.run();
	}
	
	public static void main(String[] args) {
		new Proxy360IpCrawler().startCrawl();
	}
	
}

class Proxy360Pipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		List<String> ips = resultItems.get("ips");
		List<String> ports = resultItems.get("ports");
		List<String> usedTimes = resultItems.get("usedTimes");
		for(int i=0; i<ips.size(); i++){
			String ip = ips.get(i);
			String port = ports.get(i);
			String usedTime = usedTimes.get(i);
			String str = usedTime.substring(0,usedTime.indexOf('天')).trim();
			long time = Long.parseLong(str);
			time = time * 24 * 3600;
			String proxy = ip + ":" + port;
//			ProxyIPUtils.add(proxy, time);
			ProxyIPUtils.add(proxy, 10000000000L);
		}
	}
	
}
