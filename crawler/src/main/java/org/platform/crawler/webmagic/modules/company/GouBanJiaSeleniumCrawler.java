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

public class GouBanJiaSeleniumCrawler {
	
	public void seleniumProcess() {
		System.getProperties().setProperty("webdriver.chrome.driver", "F:\\develop\\crawler\\chromedriver.exe");
		WebDriver webDriver = new ChromeDriver();
		webDriver.get("http://www.goubanjia.com/");
		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		System.out.println(webElement.getAttribute("outerHTML"));
		webDriver.close();
	}

	public static void main(String[] args) {
		Spider.create(new GouBanJiaSeleniumPageProcessor())
			.addUrl("http://www.goubanjia.com/")
			.setDownloader(new SeleniumDownloader("F:\\develop\\crawler\\chromedriver.exe"))
			.thread(1).run();
	}
	
}

class GouBanJiaSeleniumPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);
	
	@Override
	public void process(Page page) {
		System.out.println(page);
		List<String> ports = page.getHtml().xpath("//*[@id='list']/table/tbody/tr/td[2]/text()").all();
		for (String port : ports) {
			System.out.println(port);
		}
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	
}
