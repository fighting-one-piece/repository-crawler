package org.platform.crawler.webmagic.modules.abstr;

import org.platform.crawler.utils.spring.SpringApplicationContext;
import org.springframework.stereotype.Component;

@Component("genericCrawler")
public abstract class GenericCrawler {
	
	protected String componentName = null;
	
	public GenericCrawler() {
		Component component = this.getClass().getAnnotation(Component.class);
		if (null != component) this.componentName = component.value();
	}

	public abstract void startCrawl();
	
	public void run() {
		GenericCrawler crawler = (GenericCrawler) SpringApplicationContext.getInstance().get(componentName);
		crawler.startCrawl();
	}
	
}
