package org.platform.crawler.webmagic.modules.secondhand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class SecondCarPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);
	
	@Override
    public void process(Page page) {
		System.out.println(page.getHtml().xpath("//*[@id='infolist']/table/tbody/tr[1]/td[3]/b/text()").get());
		System.out.println(page.getHtml().xpath("//*[@id='infolist']/table/tbody/tr[1]/td[3]/text()[1]").get());
		System.out.println(page.getHtml().xpath("//*[@id='infolist']/table/tbody/tr[1]/td[3]/text()").get());
		System.out.println("all: " + page.getHtml().xpath("//*[@id='infolist']/table/tbody/tr[1]/td[3]/allText()").get());
		System.out.println("tidy: " + page.getHtml().xpath("//*[@id='infolist']/table/tbody/tr[1]/td[3]/tidyText()").get());
		System.out.println("and: " + page.getHtml().xpath(
				"//*[@id='infolist']/table/tbody/tr[1]/td[3]/b|//*[@id='infolist']/table/tbody/tr[1]/td[3]").get());
		System.out.println(page.getHtml().css("#infolist > table > tbody > tr:nth-child(1) > td.tc > b").get());
		System.out.println(page.getHtml().css("#infolist > table > tbody > tr:nth-child(1) > td.tc").get());
		System.out.println(page.getHtml().css("#infolist > table > tbody > tr:nth-child(1) > td.tc").all());
		System.out.println(page.getHtml().css("#infolist > table > tbody > tr:nth-child(1) > td.tc").nodes());
		Document document = Jsoup.parse(page.getHtml().css("#infolist > table > tbody > tr:nth-child(1) > td.tc").get());
		System.out.println(document.select("b").text());
		System.out.println(document.text());
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	
}
