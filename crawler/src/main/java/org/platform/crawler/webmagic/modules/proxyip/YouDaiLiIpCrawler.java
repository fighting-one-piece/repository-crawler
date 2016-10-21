package org.platform.crawler.webmagic.modules.proxyip;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
public class YouDaiLiIpCrawler extends GenericCrawler implements PageProcessor,Pipeline{

	public static void main(String[] args) {

		new YouDaiLiIpCrawler().startCrawl();
	}

	private Site site = Site.me().setRetryTimes(5).setSleepTime(1000);

	@Override
	public void process(Page page) {
		String ip;
		String port;
		Map<String, String> params = new HashMap<String, String>();
		List<String> url = page.getHtml().xpath("//div[@class='chunlist']/ul/li/p/a/@href").all();
		List<String> url1 = page.getHtml().xpath("//div[@class='pagelist']/ul/li/a/@href").all();
		url.addAll(url1);
		page.addTargetRequests(url);
		List<String> strs = page.getHtml().xpath("//div[@class='content']/p/text()").all();
		for(String str : strs){
			str = str.trim();
			System.out.print(str);
			ip = str.substring(0,str.indexOf(':'));
			port = str.substring(str.indexOf(':') + 1, str.indexOf('@'));
			params.put(ip, port);
		}
		if(params.entrySet().isEmpty()){
			page.setSkip(true);
		}
		page.putField("params", params);
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		Map<String, String> params = resultItems.get("params");

		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, String> entry = (Entry<String, String>) iterator.next();
			ProxyIPUtils.add(entry.getKey() + ":" + entry.getValue(), 10000000000L);
		}

	}

	@Override
	public void startCrawl() {
		YouDaiLiIpCrawler spPipeline = new YouDaiLiIpCrawler();
		Spider.create(new YouDaiLiIpCrawler())
		.addUrl("http://www.youdaili.net/Daili/http/")
		.addPipeline(spPipeline)
		.thread(6)
		.run();
	}
}
