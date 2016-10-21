package org.platform.crawler.webmagic.modules.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.management.JMException;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.platform.crawler.webmagic.modules.job.pipeline.JobDBPipeline;
import org.platform.crawler.webmagic.scheduler.RedisScheduler;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderMonitor.MonitorSpiderListener;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component("jobCrawler")
public class JobCrawler extends GenericCrawler {
	
	@Resource(name = "jobDBPipeline")
	private JobDBPipeline jobDBPipeline = null;
	
	@Resource(name = "redisScheduler")
	private RedisScheduler redisScheduler = null;
	
	public void startCrawl() {
		List<String> initialize_urls = new ArrayList<String>();
		String bj_url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=010000%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=$keyword$&keywordtype=2&curr_page=$curr_page$&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9";  
		String sh_url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=020000%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=$keyword$&keywordtype=2&curr_page=$curr_page$&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9";  
		String gz_url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=030200%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=$keyword$&keywordtype=2&curr_page=$curr_page$&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9";  
		String sz_url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=040000%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=$keyword$&keywordtype=2&curr_page=$curr_page$&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9";  
		String hz_url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=080200%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=$keyword$&keywordtype=2&curr_page=$curr_page$&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9";  
		String cd_url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=090200%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=$keyword$&keywordtype=2&curr_page=$curr_page$&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9";  
		String keyword = "%E5%B7%A5%E7%A8%8B%E5%B8%88";  
		initialize_urls.add(bj_url.replace("$keyword$", keyword).replace("$curr_page$", "1"));
		initialize_urls.add(sh_url.replace("$keyword$", keyword).replace("$curr_page$", "1"));
		initialize_urls.add(gz_url.replace("$keyword$", keyword).replace("$curr_page$", "1"));
		initialize_urls.add(sz_url.replace("$keyword$", keyword).replace("$curr_page$", "1"));
		initialize_urls.add(hz_url.replace("$keyword$", keyword).replace("$curr_page$", "1"));
		initialize_urls.add(cd_url.replace("$keyword$", keyword).replace("$curr_page$", "1"));
		List<Pipeline> pipelines = new ArrayList<Pipeline>();
		pipelines.add(new ConsolePipeline());
		pipelines.add(jobDBPipeline);
		Spider jobSpider = Spider.create(new JobPageProcessor())
                .addUrl(initialize_urls.toArray(new String[0]))
                .setScheduler(redisScheduler) 
                .setPipelines(pipelines)
                .thread(4);
		try {
			SpiderMonitor.instance().register(jobSpider);
		} catch (JMException e) {
			e.printStackTrace();
		}
		jobSpider.run();
		List<SpiderListener> spiderListeners = jobSpider.getSpiderListeners();
		for (SpiderListener spiderListener : spiderListeners) {
			if (spiderListener instanceof MonitorSpiderListener) {
				MonitorSpiderListener monitorSpiderListener = (MonitorSpiderListener) spiderListener;
				System.out.println("success count: " + monitorSpiderListener.getSuccessCount());
				System.out.println("error count: " + monitorSpiderListener.getErrorCount());
				System.out.println("error urls: ");
				for (String errorUrl : monitorSpiderListener.getErrorUrls()) {
					System.out.println(errorUrl);
				}
			}
		}
	}

	public static void main(String[] args) {
		new JobCrawler().run();
	}
	
}
