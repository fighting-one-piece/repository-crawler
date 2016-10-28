package org.platform.crawler.webmagic.modules.proxyip;

import java.util.List;
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
public class KuaiDaiLiCrawler extends GenericCrawler implements PageProcessor {	
	
	public static void main(String[] args) {
		new KuaiDaiLiCrawler().startCrawl();
	}
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);
	@Override
	public void process(Page page) {		
		List<String> url=page.getHtml().links().regex("http://www.kuaidaili.com/proxylist/\\w+").all();
		page.putField("ip",page.getHtml().xpath("//*[@id='index_free_list']/table/tbody/tr/td[1]/text()").all());
		page.putField("port",page.getHtml().xpath("//*[@id='index_free_list']/table/tbody/tr/td[2]/text()").all());
		page.putField("time",page.getHtml().xpath("//*[@id='index_free_list']/table/tbody/tr/td[8]/text()").all());
		page.addTargetRequests(url);		
	}
	
	@Override
	public Site getSite() {		
		return site;
	}
	
	@Override
	public void startCrawl() {		
		Spider.create(new KuaiDaiLiCrawler())
		.addUrl("http://www.kuaidaili.com/")
		.addPipeline(new KuaiDaiLiPipeline())		
		.thread(1)
		.run();		
	}
}

class KuaiDaiLiPipeline implements Pipeline{
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		List<String> ip=resultItems.get("ip");
		List<String> port=resultItems.get("port");
		List<String> time=resultItems.get("time");
		String str=":";
		for(int i=0;i<ip.size();i++){		
			String dizi =ip.get(i)+str+port.get(i);
			String fen=".*分钟*.";
			String shi=".*小时*.";
			if(time.get(i).matches(fen)){
				int index=time.get(i).indexOf("分");
				String fenz=time.get(i).substring(0,index);			
				long a=Long.parseLong(fenz);
				long fz=a*60;
				ProxyIPUtils.add(dizi, fz);
			}else if(time.get(i).matches(shi)){
				int index=time.get(i).indexOf("小");
				String xiao=time.get(i).substring(0,index);
				long a=Long.parseLong(xiao);
				long xs=a*3600;
				ProxyIPUtils.add(dizi, xs);
			}else{
				int index=time.get(i).indexOf("秒");
				String miao=time.get(i).substring(0,index);
				long m=Long.parseLong(miao);
				ProxyIPUtils.add(dizi, m);
			}
		}
	}
}
	





