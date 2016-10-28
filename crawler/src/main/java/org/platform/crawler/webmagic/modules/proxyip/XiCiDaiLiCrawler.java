package org.platform.crawler.webmagic.modules.proxyip;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.platform.crawler.utils.proxy.ProxyIP;
import org.platform.crawler.utils.proxy.ProxyIPUtils;
import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;

@ProxyIP
public class XiCiDaiLiCrawler extends GenericCrawler {

	@Override
	public void startCrawl() {
		// 创建HttpClient实例
		HttpClient httpClient = HttpClientBuilder.create().build();
		// 创建Get方法实例
		HttpGet httpgets = new HttpGet("http://api.xicidaili.com/free2016.txt");

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpgets);
			// 将entity当中的数据转换为字符串
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				String response = EntityUtils.toString(entity, "utf-8");
				Pattern p = Pattern
						.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d+)");
				Matcher m = p.matcher(response);
				while (m.find()) {
					String ip = m.group(1);
					int port = Integer.parseInt(m.group(2));
					String ipp = ip + ":" + port;
					ProxyIPUtils.add(ipp, 30);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
