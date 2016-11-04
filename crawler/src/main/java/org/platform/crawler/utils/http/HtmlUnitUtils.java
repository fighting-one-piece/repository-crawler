package org.platform.crawler.utils.http;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(HtmlUnitUtils.class);
			
	public static String get(String url) {
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setTimeout(35000);
		webClient.getOptions().setDoNotTrackEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		try {
			HtmlPage page = webClient.getPage(url);
			return page.asXml();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			webClient.close();
		}
		return null;
	}

	public static void downloadPage(String url, String dest, String encode) {
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setTimeout(35000);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		try {
			HtmlPage page = webClient.getPage(url);
			page.save(new File(dest, encode));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			webClient.close();
		}
	}
	
}
