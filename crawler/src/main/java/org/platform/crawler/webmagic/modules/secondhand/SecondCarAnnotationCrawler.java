package org.platform.crawler.webmagic.modules.secondhand;

import org.platform.crawler.webmagic.modules.secondhand.entity.SecondCar;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;

public class SecondCarAnnotationCrawler {

	public static void main(String[] args) {
		Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);
		OOSpider.create(site, new ConsolePageModelPipeline(), SecondCar.class)
//			.addUrl("http://bj.58.com/ershouche/pn2/?PGTID=0d30001d-0000-1960-5b56-2545d8973746").thread(1).run();
			.addUrl("http://cd.58.com/ershouche/pn1/?utm_source=market&spm=b-31580022738699-me-f-824.bdpz_biaoti").thread(1).run();
	}
}
