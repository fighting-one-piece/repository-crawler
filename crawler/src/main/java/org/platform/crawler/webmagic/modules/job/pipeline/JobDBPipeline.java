package org.platform.crawler.webmagic.modules.job.pipeline;

import java.util.List;

import javax.annotation.Resource;

import org.platform.crawler.webmagic.modules.abstr.mapper.GenericMapper;
import org.platform.crawler.webmagic.modules.abstr.pipeline.DBPipeline;
import org.platform.crawler.webmagic.modules.job.entity.Job;
import org.platform.crawler.webmagic.modules.job.mapper.JobMapper;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

@Component("jobDBPipeline")
public class JobDBPipeline extends DBPipeline<Job, Long> {

	@Resource(name = "jobMapper")
	private JobMapper jobMapper = null;
	
	@Override
	public GenericMapper<Job, Long> obtainMapperInstance() {
		return jobMapper;
	}
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		List<Job> jobs = resultItems.get("jobs");
		for (int i = 0, len = jobs.size(); i < len; i++) {
			jobMapper.insert(jobs.get(i));
		}
	}

}
