package org.platform.crawler.webmagic.modules.secondhand.ganji.car;

import java.util.ArrayList;
import java.util.List;

import org.platform.crawler.webmagic.modules.secondhand.ganji.car.entity.Car;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class CarPageProcessor implements PageProcessor{

	private Site site = Site.me().setRetryTimes(5).setSleepTime(1000);

	public Site getSite() {   
		return site;
	}

	public void process(Page page) {
		String carName = page.getHtml().xpath("//div[@class='leftmain']/div/div/h1[@class='title-name']/text()").get();  //二手车名
		String openDate = page.getHtml().xpath("//div[@class='leftmain']/div/div/div/ul/li/i[@class='f10 pr-5']/text()").get(); 	//发布时间
		String price = page.getHtml().xpath("//div[@class='col-cont']/div/div/div/ul/li[@class='iNew-price']/span/allText()").get();	//价格
		String age = page.getHtml().xpath("//div[@class='col-cont']/div/div/div/ul/li[@class='iNew-yeah']/span/allText()").get();	//车龄
		String km = page.getHtml().xpath("//div[@class='col-cont']/div/div/div/ul/li[@class='iNew-km']/span/allText()").get();	//总路程
		String linkPhone = page.getHtml().xpath("//div[@class='col-cont']/div/div/div/div/p[@class='v-p1']/span/text()").get();	//联系人电话
		String linkName = page.getHtml().xpath("//div[@class='col-cont']/div/div/div/div/p[@class='v-p2']/text()").get();	//联系人
		String url = page.getRequest().getUrl();	//本页链接
		
		String engine = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[1]/allText()").get();		//排气量
		String gearbox = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[2]/allText()").get();	//变速箱
		String emissionStandards = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[3]/allText()").get();	//排放标准
		String color = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[4]/allText()").get();		//颜色
		String structure = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[5]/allText()").get();	//车身结构
		String mileage = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[6]/allText()").get();	//行驶里程
		String date = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[7]/allText()").get();		//上牌时间
		String properties = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[8]/allText()").get();	//使用性质
		String dueTime = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[9]/allText()").get();	//年检到期
		String unscientificTime = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[10]/allText()").get();	//交强险到期
		String accidents = page.getHtml().xpath("//div[@class='col-cont mt20']/div/div/ul/li[11]/allText()").get();	//事故情况
		if(carName == null){
			page.setSkip(true);
		}
		Car car = new Car();
		car.setAge(age);
		car.setCarName(carName);
		car.setKm(km);
		car.setLinkName(linkName);
		car.setLinkPhone(linkPhone);
		car.setOpenDate(openDate);
		car.setPrice(price);
		car.set_id(url);
		car.setEngine(engine);
		car.setGearbox(gearbox);
		car.setEmissionStandards(emissionStandards);
		car.setColor(color);
		car.setStructure(structure);
		car.setMileage(mileage);
		car.setDate(date);
		car.setProperties(properties);
		car.setDueTime(dueTime);
		car.setUnscientificTime(unscientificTime);
		car.setAccidents(accidents);
		List<Car> cars = new ArrayList<Car>();
		cars.add(car);
		page.putField("cars", cars);
		
		List<String> links1 = page.getHtml().links().regex("http://\\w+.ganji.com").all();
		List<String> links2 = page.getHtml().links().regex("http://\\w+.ganji.com/ershouche/").all();
		List<String> links = page.getHtml().links().regex("http://\\w+.ganji.com/ershouche/\\w+.htm").all();
		links.addAll(links1);
		links.addAll(links2);
		page.addTargetRequests(links);

	}

}
