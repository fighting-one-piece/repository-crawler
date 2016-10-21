package org.platform.crawler.webmagic.modules.secondhand.ganji.house;


import java.util.List;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component("gjwHousePageProcessor")
public class GjwHousePageProcessor implements PageProcessor {
	

	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);
	@Override
	public void process(Page page) {
		page.putField("介绍", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/h1/text()").toString());
		if(page.getResultItems().get("介绍") == null){
			page.setSkip(true);
		}	
		page.putField("售价",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div[2]/ul/li[1]/b/text()").toString());
		if(page.getResultItems().get("售价") == null){
			page.putField("售价", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[1]/b/text()").toString());
		}					  
		String xiaoqu="//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[6]/a/text()";
		page.putField("单价",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[2]/text()").toString());
		page.putField("户型",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[3]/text()").toString());
		page.putField("概况",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[4]/text()").toString());
		page.putField("楼层",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[5]/text()").toString());
		page.putField("小区",page.getHtml().xpath(xiaoqu).toString());
		if(page.getResultItems().get("小区")==null){
			page.putField("小区",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[6]/span[2]/text()").toString());
			if(page.getResultItems().get("小区")==null){
				page.putField("小区",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div[1]/div[2]/ul/li[6]/a/text()").toString());
				if(page.getResultItems().get("小区")==null){
					page.putField("小区",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div[1]/div[2]/ul/li[7]/a/text()").toString());
					if(page.getResultItems().get("小区")==null){
						page.putField("小区",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div[2]/ul/li[7]/span[2]/text()").toString());
						if(page.getResultItems().get("小区")==null){
							page.putField("小区",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[7]/a/text()").toString());
						}									   
					}
				}
			}
		}else if(page.getResultItems().get("小区").equals("  ()     ")){
			page.putField("小区","暂无小区信息");
		}
		page.putField("位置",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[7]/allText()").toString());		
		page.putField("详细地址",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/ul/li[8]/span[2]/text()").toString());
		if(page.getResultItems().get("详细地址")==null){
			page.putField("详细地址",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div[2]/ul/li[9]/span[2]/text()").toString());
			if(page.getResultItems().get("详细地址")==null){
				page.putField("详细地址",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div[1]/div[2]/ul/li[9]/span[2]/text()").toString());
				if(page.getResultItems().get("详细地址")==null){
					page.putField("详细地址","无详细地址");					
				}
			}
		}
		page.putField("联系人",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div/div/div/div/div[1]/span[1]/i[1]/text()").toString());
		page.putField("联系方式",page.getHtml().xpath("//*[@id='contact-phone']/span[2]/em/text()").toString());		
		if(page.getResultItems().get("联系方式")==null){
			page.putField("联系方式","无联系方式");					
		}
		page.putField("发布时间",page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div[1]/div[1]/ul[2]/li[1]/i/text()").toString());
		if(page.getResultItems().get("发布时间") == null){
			page.putField("发布时间", page.getHtml().xpath("//*[@id='wrapper']/div[2]/div[1]/div[2]/div[1]/ul[2]/li[1]/i/text()").toString());
		}
		page.putField("id",page.getRequest().getUrl().toString());		
		List<String> url1=page.getHtml().links().regex("http://\\w+.ganji.com").all();
		List<String> url2=page.getHtml().links().regex("http://\\w+.ganji.com/fang5/").all();
		List<String> url=page.getHtml().links().regex("http://\\w+.ganji.com/fang5/\\w+.htm").all();
		url.addAll(url1);
		url.addAll(url2);			
		page.addTargetRequests(url);
	}	
	

	@Override
	public Site getSite() {
		
		return site;
	}

}
