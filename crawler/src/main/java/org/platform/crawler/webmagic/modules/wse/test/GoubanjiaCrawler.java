package org.platform.crawler.webmagic.modules.wse.test;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.platform.crawler.utils.http.HtmlUnitUtils;
import org.platform.crawler.utils.proxy.ProxyIP;
import org.platform.crawler.webmagic.modules.abstr.GenericCrawler;


@ProxyIP
public class GoubanjiaCrawler  extends GenericCrawler{
	
	public static void main(String[] args) {
		new GoubanjiaCrawler().startCrawl();
	}
	@Override
	public void startCrawl() {
		List<String> ip = new ArrayList<String>();
		List<String> port = new ArrayList<String>();
		String url="http://www.goubanjia.com/";
		String html = HtmlUnitUtils.get(url);
		Document document = Jsoup.parse(html);
		Elements elements = document.select("td.ip");
		for (Element element : elements) {
			
			String elm=element.toString();
			String  str= elm.replace(" ", "").replace("\n", "");
			String[] a=str.split("spanclass");

			String por=a[1].substring(a[1].indexOf(">")+1, a[1].indexOf("</span"));
			port.add(por);
			
			String[] w = a[0].split(">");
			StringBuffer sf = new StringBuffer();
			for (int i = 1; i < w.length; i++) {
				if (w[i].indexOf("< ") > 0 || w[i - 1].indexOf("none") == -1) {
					String ee = w[i].substring(0, w[i].indexOf("<"));
					if (ee.trim().length() > 0) {
						sf.append(ee);
					}
				}
			}
			ip.add(sf.toString());
		}
		for(int i=0;i<ip.size();i++){
			System.out.println(ip.get(i)+port.get(i));
//			ProxyIPUtils.add(ip.get(i)+port.get(i), 360);
			
		}
		
	}

}
