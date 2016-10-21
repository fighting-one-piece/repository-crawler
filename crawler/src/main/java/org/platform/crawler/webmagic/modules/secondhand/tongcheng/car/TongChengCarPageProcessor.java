package org.platform.crawler.webmagic.modules.secondhand.tongcheng.car;

import java.util.List;

import javax.annotation.Resource;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.platform.crawler.webmagic.modules.secondhand.tongcheng.car.entity.TongChengCar;
import org.platform.crawler.webmagic.modules.secondhand.tongcheng.car.pipeline.TongChengCarPipeline;
import org.platform.crawler.webmagic.scheduler.RedisScheduler;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@Component("TongChengCarPageProcessor")
public class TongChengCarPageProcessor extends GenericCrawler implements PageProcessor {
	
	@Resource(name = "redisScheduler")
	private RedisScheduler redisScheduler = null;
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(3000).setTimeOut(3000);
	@Override
	public void process(Page page) {
		String carName = page.getHtml().xpath("//*[@class='content_title']/p/text()").toString();
		String carPrice = page.getHtml().xpath("//*[@class='pricein']/allText()").toString();
		String mileage = page.getHtml().xpath("//[@class='lcsp_info']/ul/li[1]/span[1]/text()").toString();
		String firstDate = page.getHtml().xpath("//[@class='lcsp_info']/ul/li[2]/span[1]/text()").toString();
		String displacement = page.getHtml().xpath("//[@class='lcsp_info']/ul/li[3]/span[1]/text()").toString();
		String sellerName = page.getHtml().xpath("//[@class='contect']/span[2]/a/text()").toString();
		String address = page.getHtml().xpath("//[@class='saleinfo']/div[2]/span[2]/text()").toString();
		String color = page.getHtml().xpath("//[@class='baseinfo_ul clearfix']/li[3]/span[2]/text()").toString();
		String maintain = page.getHtml().xpath("//[@class='baseinfo_ul clearfix']/li[4]/span[2]/text()").toString();
		String emissions = page.getHtml().xpath("//[@class='peizhi_ul clearfix']/li[5]/span[2]/text()").toString();
		String carDescribe = page.getHtml().xpath("//[@class='cardes_div']/p[1]/text()").toString();
		List<String> images = page.getHtml().xpath("//[@class='carimg_list']/img/@src").all();
		String u = page.getRequest().getUrl();

		// 创建要保存的document
		TongChengCar car = new TongChengCar();
		car.setCarName(carName);
		car.setAddress(address);
		car.setCarDescribe(carDescribe);
		car.setCarPrice(carPrice);
		car.setColor(color);
		car.setDisplacement(displacement);
		car.setEmissions(emissions);
		car.setFirstDate(firstDate);
		car.setImages(images);
		car.setMaintain(maintain);
		car.setMileage(mileage);
		car.setSellerName(sellerName);
		car.setHref(u);
		page.putField("car", car);
		
		List<String> href = page.getHtml().xpath("//*[@id='infolist']/table/tbody/tr/td[2]/a[1]/@href").all();
		List<String> list = page.getHtml().xpath("//*[@id='infolist']/div/a/@href").all();
		List<String> city = page.getHtml().xpath("//*[@id='clist']/dd/a/@href").all();
		list.addAll(city);
		href.addAll(list);
		page.addTargetRequests(href);
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	public static void main(String[] args) {
		new TongChengCarPageProcessor().run();
	}

	@Override
	public void startCrawl() {
		String url = "http://www.58.com/ershouche/changecity/";
		Spider spider = Spider.create(new TongChengCarPageProcessor()).addUrl(url).thread(5);
		spider.setScheduler(redisScheduler).addPipeline(new TongChengCarPipeline()).addPipeline(new ConsolePipeline());
		spider.run();
	}
}
