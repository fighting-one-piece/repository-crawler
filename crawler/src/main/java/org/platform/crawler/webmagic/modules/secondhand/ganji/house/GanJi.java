package org.platform.crawler.webmagic.modules.secondhand.ganji.house;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

public class GanJi implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setRetrySleepTime(5000);
	
	@Override
	public void process(Page page) {
		//内容页
		List<String> url1 = page.getHtml().regex("http://\\w+.ganji.com/fang1/\\w+.htm").all();
		//下一页
		List<String> ur3 = page.getHtml().regex("http://\\w+.ganji.com/fang1/\\w+").all();
		//城市页
		List<String> ur2 = page.getHtml().regex("http://\\w+.ganji.com/").all();
		
		
		url1.addAll(ur3);
		url1.addAll(ur2);
		//转换URL
		List<String> url3 =new ArrayList<String>();
		
		for(int i=0;i<url1.size();i++){
//			System.out.println("-----------"+url.get(i));
			URI uri=null;
	        try{
	        	URL ur = new URL(url1.get(i));
	        	  uri= new URI(ur.getProtocol(), ur.getHost(), ur.getPath(), ur.getQuery(), null);
	        	  url3.add(uri.toString());
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
		}
		// 所有的链接
		page.addTargetRequests(url3);
		
		//class 选择器
		page.putField("JiaGe",page.getHtml().xpath("//b[@class='basic-info-price fl']/text()").toString());
		page.putField("HuXing", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div[2]/ul/li[2]/text()").toString());
		page.putField("LouCeng", page.getHtml().xpath("//ul[@class='basic-info-ul']/li[4]/text()").toString());
		page.putField("XiaoQu", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div[2]/ul/li[5]/div/a[1]/text()").toString());
		page.putField("XiaoQu1", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div[2]/ul/li[5]/div/span[1]/text()").toString());
		page.putField("WeiZhi", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div[2]/ul/li[6]/a/text()").toString());
		page.putField("DiZhi", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div[2]/div/div[2]/ul/li[7]/span[2]/text()").toString());
		page.putField("name", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div[2]/div[1]/div[1]/span[1]/i/text()").toString());
		page.putField("phone",page.getHtml().xpath("//*[@id='contact-phone']/span[2]/em/text()").toString());
		 if (page.getResultItems().get("phone") != null) {
			 page.putField("url",page.getRequest().getUrl());
		 }
		
	}

	@Override
	public Site getSite() {
		return site;
	}
	public static void main(String[] args) {
		Spider.create(new GanJi())
				.addUrl("http://www.ganji.com/index.htm")
				//保存方法
				.addPipeline(new GanJiMongoDB())
				//跑过的网站
				.setScheduler(new FileCacheQueueScheduler("E:\\ErrorUrl"))
				//线程5个跑起
				.thread(5).run();
	}

}
