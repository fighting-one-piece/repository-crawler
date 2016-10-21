package org.platform.crawler.webmagic.modules.company;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

public class TianYanChaSeleniumCrawler {
	
	public void seleniumProcess() {
		System.getProperties().setProperty("webdriver.chrome.driver", "F:\\develop\\crawler\\chromedriver.exe");
		WebDriver webDriver = new ChromeDriver();
		webDriver.get("http://www.tianyancha.com/search/%E5%9B%9B%E5%B7%9D%E7%A7%91%E9%9A%86%E5%BB%BA%E8%AE%BE%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8?checkFrom=searchBox");
		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		System.out.println(webElement.getAttribute("outerHTML"));
		webDriver.close();
	}

	public static void main(String[] args) {
		Spider.create(new TianYanChaSeleniumPageProcessor())
			.addUrl("http://www.tianyancha.com/search/%E5%9B%9B%E5%B7%9D%E7%A7%91%E9%9A%86%E5%BB%BA%E8%AE%BE%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8?checkFrom=searchBox")
			.setDownloader(new SeleniumDownloader("F:\\develop\\crawler\\chromedriver.exe"))
			.thread(1).run();
	}
	
}

class TianYanChaSeleniumPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);
	
	@Override
	public void process(Page page) {
		List<String> links = page.getHtml().links().all();
		for (String link : links) {
			System.out.println(link);
		}
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	
}
