package org.platform.crawler.webmagic.modules.job;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.platform.crawler.webmagic.modules.job.entity.Job;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class JobPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);
    
    @Override
    public void process(Page page) {
    	List<String> divs = page.getHtml().xpath("//*[@id='resultList']/div[@class='el']").all();
    	List<Job> jobs = new ArrayList<Job>();
    	Job job = null;
    	for (int i = 1, len = divs.size(); i < len; i++) {
    		Document document = Jsoup.parse(divs.get(i));
    		Elements elements1 = document.select("p.t1 a");
    		Elements elements2 = document.select("span.t2 a");
    		Elements elements3 = document.select("span.t3");
    		Elements elements4 = document.select("span.t4");
    		Elements elements5 = document.select("span.t5");
    		for (int e = 0, elen = elements1.size(); e < elen; e++) {
    			job = new Job();
    			job.setJobName(elements1.get(e).text());
    			job.setJobUrl(elements1.get(e).attr("href"));
    			job.setCompanyName(elements2.get(e).text());
    			job.setWorkplace(elements3.get(e).text());
    			job.setSalary(elements4.get(e).text());
    			job.setPublishDate(elements5.get(e).text());
    			jobs.add(job);
    		}
    	}
    	page.putField("jobs", jobs);
    	String current_url = page.getRequest().getUrl();
    	String regex = "curr_page=\\d+";
    	Matcher matcher = Pattern.compile(regex).matcher(current_url);
    	if (matcher.find()) {
    		String curr_page_kv = matcher.group();
    		if (curr_page_kv.indexOf("=") != -1) {
    			int curr_page = Integer.parseInt(curr_page_kv.split("=")[1]) + 1;
    			page.addTargetRequest(current_url.replace(curr_page_kv, "curr_page=" + curr_page));
    		}
    	}
    }

	@Override
	public Site getSite() {
		return site;
	}
	
}

