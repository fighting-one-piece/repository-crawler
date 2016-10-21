package org.platform.crawler.webmagic.modules.job.mapper;

import org.platform.crawler.webmagic.modules.abstr.mapper.GenericMapper;
import org.platform.crawler.webmagic.modules.job.entity.Job;
import org.springframework.stereotype.Repository;

@Repository("jobMapper")
public interface JobMapper extends GenericMapper<Job, Long> {

}
