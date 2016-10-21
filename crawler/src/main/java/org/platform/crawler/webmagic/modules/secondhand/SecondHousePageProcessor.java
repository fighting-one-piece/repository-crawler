package org.platform.crawler.webmagic.modules.secondhand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class SecondHousePageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);
	
	@Override
    public void process(Page page) {
		System.out.println(page.getHtml().xpath("//*[@id='main']/div[1]/div[1]/div[1]/h1/text()").toString());
		System.out.println(page.getHtml().xpath("//*[@id='main']/div[1]/div[1]/div[1]/span/text()").toString());
		System.out.println(page.getHtml().xpath("//*[@id='main']/div[1]/div[1]/div[1]/allText()").toString());
		System.out.println(page.getHtml().xpath("//*[@id='main']/div[1]/div[1]/div[1]/tidyText()").toString());
		System.out.println(page.getHtml().css("#main > div.col.detailPrimary.mb15 > div.mainTitle > div.bigtitle").get());
		Document document = Jsoup.parse(page.getHtml().css("#main > div.col.detailPrimary.mb15 > div.mainTitle > div.bigtitle").get());
		System.out.println(document.select("h1").text());
		System.out.println(document.select("span").text());
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	
}
