package org.platform.crawler.webmagic.modules.job.entity;

import org.platform.crawler.webmagic.modules.abstr.entity.PKAutoEntity;

public class Job extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 职位名称 */
	private String jobName = null;
	/** 职位链接 */
	private String jobUrl = null;
	/** 公司名称 */
	private String companyName = null;
	/** 工作地点 */
	private String workplace = null;
	/** 薪资 */
	private String salary = null;
	/** 发布时间 */
	private String publishDate = null;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobUrl() {
		return jobUrl;
	}

	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
}
