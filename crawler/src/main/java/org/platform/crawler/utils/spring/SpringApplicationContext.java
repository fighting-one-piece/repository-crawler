package org.platform.crawler.utils.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContext {
	
	private ApplicationContext applicationContext = null;
	
	private SpringApplicationContext() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
	}
	
	private static class SpringApplicationContextHolder {
		private static final SpringApplicationContext INSTANCE = new SpringApplicationContext();
	}
	
	public static final SpringApplicationContext getInstance() {
		return SpringApplicationContextHolder.INSTANCE;
	}
	
	public synchronized ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public Object get(String name) {
		return getApplicationContext().getBean(name);
	}
	
	public <T> T get(String name, Class<T> requiredType) {
		return getApplicationContext().getBean(name, requiredType);
	}

}
