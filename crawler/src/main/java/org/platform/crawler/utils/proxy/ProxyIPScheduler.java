package org.platform.crawler.utils.proxy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyIPScheduler {
	
	private Logger LOG = LoggerFactory.getLogger(ProxyIPScheduler.class);
	
	public static final int SUCCESS = 1;
	
	public static final int FAILURE = 0;
	
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	
	private List<String> packageScanner(String packagePath, boolean recursive) {
		List<String> targetClassFiles = new ArrayList<String>();
		String filePackPath = packagePath.replace('.', '/');
		try {
			Enumeration<URL> dir = Thread.currentThread().getContextClassLoader().getResources(filePackPath);
			while (dir.hasMoreElements()) {
				URL url = dir.nextElement();
				LOG.info("url: {} ", url.getPath());
				String protocol = url.getProtocol();
				LOG.info("protocol: {} ", protocol);
				if ("file".equals(protocol)) {
					File file = new File(url.getPath().substring(1));
					recursionPackageScanner(file, targetClassFiles);
				} else if ("jar".equals(protocol)) {
					jarPackageScanner(url.getPath(), targetClassFiles);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return targetClassFiles;
	}
	
	private void recursionPackageScanner(File dir, List<String> targetClassFiles) {
		File[] files = dir.listFiles();
		if (null == files) return;
		for (int i = 0, len = files.length; i < len; i++) {
			File file = files[i];
			String path = file.getAbsolutePath();
			if (path.endsWith(".class")) {
				targetClassFiles.add(path.substring(path.indexOf("org"), path.lastIndexOf(".")).replaceAll("\\\\", "."));
			} else {
				recursionPackageScanner(new File(path), targetClassFiles);
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void jarPackageScanner(String jarPath, List<String> targetClassFiles) {
		try {
			String[] jarInfo = jarPath.split("!");
			String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
			JarFile jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entrys = jarFile.entries();  
			while (entrys.hasMoreElements()) {  
				JarEntry jarEntry = entrys.nextElement();  
				String entryName = jarEntry.getName();  
				if (!entryName.contains("$") && entryName.endsWith(".class")) {
					entryName = entryName.substring(0, entryName.lastIndexOf("."))
							.replaceAll("/", ".").replaceAll("\\\\", ".");
					targetClassFiles.add(entryName);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	private int runTask() {
		try {
			List<String> classNames = packageScanner("org.platform.crawler.webmagic", true);
			for (String className : classNames) {
				final Class<?> clazz = Class.forName(className);
				ProxyIP proxyIP = clazz.getAnnotation(ProxyIP.class);
				if (null == proxyIP) continue;
				LOG.info("start crawl {}", clazz);
				long startTime = System.currentTimeMillis();
				((GenericCrawler) clazz.newInstance()).startCrawl();
				long endTime = System.currentTimeMillis();
				LOG.info("end crawl {}, spend time {} s", clazz, (endTime - startTime) / 1000);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return FAILURE;
		}
		return SUCCESS;
	}

	private int runTaskWithThread() {
		try {
			List<String> classNames = packageScanner("org.platform.crawler.webmagic", true);
			List<Future<Integer>> fs = new ArrayList<Future<Integer>>();
			for (String className : classNames) {
				final Class<?> clazz = Class.forName(className);
				ProxyIP proxyIP = clazz.getAnnotation(ProxyIP.class);
				if (null == proxyIP) continue;
				Future<Integer> future = executorService.submit(new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						try {
							LOG.info("start crawl {}", clazz);
							long startTime = System.currentTimeMillis();
							((GenericCrawler) clazz.newInstance()).startCrawl();
							long endTime = System.currentTimeMillis();
							LOG.info("end crawl {}, spend time {} s", clazz, (endTime - startTime) / 1000);
						} catch (Exception e) {
							LOG.error(e.getMessage(), e);
						} 
						return SUCCESS;
					}
				});
				fs.add(future);
			}
			for (Future<Integer> f : fs) {
				while (!f.isDone()){}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return FAILURE;
		}
		return SUCCESS;
	}

	public void schedule_1() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new CrawlProxyIPTask(), 1 * 1000, 10 * 1000);
	}
	
	public void schedule(final boolean isStartThreadPool) {
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				LOG.info("start schedule task");
				long startTime = System.currentTimeMillis();
				int finishCode = isStartThreadPool ? runTaskWithThread() : runTask();
				LOG.info("end schedule task , finish code {}, spend time {} s", finishCode,
						(System.currentTimeMillis() - startTime) / 1000);
			}
		}, 1, 5, TimeUnit.MINUTES);
	}
	
	public static void main(String[] args) {
		boolean isStartThreadPool = false;
		if (null != args && args.length > 0) {
			isStartThreadPool = Boolean.parseBoolean(args[0]);
		}
		new ProxyIPScheduler().schedule(isStartThreadPool);
	}
	
}

class CrawlProxyIPTask extends TimerTask {

	private Logger LOG = LoggerFactory.getLogger(CrawlProxyIPTask.class);
	
	private List<String> packageScanner(String packagePath, boolean recursive) {
		List<String> targetClassFiles = new ArrayList<String>();
		String filePackPath = packagePath.replace('.', '/');
		try {
			Enumeration<URL> dir = Thread.currentThread().getContextClassLoader().getResources(filePackPath);
			while (dir.hasMoreElements()) {
				URL url = dir.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					File file = new File(url.getPath().substring(1));
					recursionPackageScanner(file, targetClassFiles);
				} 
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return targetClassFiles;
	}

	private void recursionPackageScanner(File dir, List<String> targetClassFiles) {
		File[] files = dir.listFiles();
		if (null == files) return;
		for (int i = 0, len = files.length; i < len; i++) {
			File file = files[i];
			String path = file.getAbsolutePath();
			if (path.endsWith(".class")) {
				targetClassFiles.add(path.substring(path.indexOf("org"), path.lastIndexOf(".")).replaceAll("\\\\", "."));
			} else {
				recursionPackageScanner(new File(path), targetClassFiles);
			}
		}
	}
	
	@Override
	public void run() {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<String> classNames = packageScanner("org.platform.crawler.webmagic", true);
		try {
			for (String className : classNames) {
				final Class<?> clazz = Class.forName(className);
				ProxyIP proxyIP = clazz.getAnnotation(ProxyIP.class);
				if (null == proxyIP) continue;
				executorService.submit(new Runnable() {
					@Override
					public void run() {
						try {
							LOG.info("start crawl {}", clazz);
							long startTime = System.currentTimeMillis();
							((GenericCrawler) clazz.newInstance()).startCrawl();
							long endTime = System.currentTimeMillis();
							LOG.info("end crawl {}, spend time {} s", clazz, (endTime - startTime) / 1000);
						} catch (Exception e) {
							LOG.error(e.getMessage(), e);
						} 
					}
				});
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		executorService.shutdown();
	}
	
}

