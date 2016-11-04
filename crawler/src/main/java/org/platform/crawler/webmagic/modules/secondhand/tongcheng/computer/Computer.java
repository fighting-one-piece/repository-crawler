package org.platform.crawler.webmagic.modules.secondhand.tongcheng.computer;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class Computer  implements PageProcessor {
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public void process(Page page) {
		List<String> mainUrls = page.getHtml()
				.xpath("//div[@class='index_bo']/dl/dd/a/@href").all();
		List<String> urls = page.getHtml().xpath("//td[@class='t']/a/@href")
				.all();
		List<String> url = page.getHtml()
				.xpath("//div[@class='pager']/a/@href").all();

		// 标题 title
		String title = page.getHtml()
				.xpath("//h1[@class='info_titile']/text()").toString();
		if (title == null || title == "") {
			title = page.getHtml()
					.xpath("//div[@class='col_sub mainTitle']/h1/text()")
					.toString();
		}
		page.putField("title", title);

		// 价格 price
		String price = page.getHtml()
				.xpath("//span[@class='price_now']/i/text()").toString();
		if (price == null || price == "") {
			price = page.getHtml().xpath("//span[@class='price c_f50']/text()")
					.toString();
		}
		page.putField("price", price + "元");

		// 区域 zone
		String zone = page.getHtml()
				.xpath("//div[@class='palce_li']/span/i/text()").toString();
		if (zone == null || zone == "") {
			// *[@id='content']/div[1]/div[1]/div[3]/ul/li[3]/div[2]/span/a
			zone = page
					.getHtml()
					.xpath("//*[@id='content']/div[1]/div[1]/div[3]/ul/li[3]/div[2]/span/a/text()")
					.toString();
		}
		page.putField("zone", zone);
		// 成色 old
		// *[@id='content']/div[1]/div[1]/div[3]/ul/li[2]/div[2]/span
		String old = page
				.getHtml()
				.xpath("//*[@id='content']/div[1]/div[1]/div[3]/ul/li[2]/div[2]/span/text()")
				.toString();
		page.putField("old", old);

		// 物品详情 product
		String product = page.getHtml()
				.xpath("//div[@class='baby_kuang clearfix']/p/text()")
				.toString();
		page.putField("product", product);

		// 卖主 账号 或 姓名 seller
		String seller = page.getHtml()
				.xpath("//div[@class='baby_talk']/p/span/i/text()").toString();
		if (seller == null || seller == "") {
			seller = page.getHtml().xpath("//ul[@class='vcard']/li/a/text()")
					.toString();
		}
		page.putField("seller", seller);

		// 联系电话 phone
		String phone = page.getHtml()
				.xpath("//div[@class='num_tan_text']/span[1]/text()")
				.toString();

		page.putField("phone", phone);

		// 电话备注 phoneNote
		String phoneNote = page.getHtml()
				.xpath("//div[@class='num_tan_text']/span[2]/text()")
				.toString();
		page.putField("phone", phoneNote);

		urls.addAll(mainUrls);
		url.addAll(urls);
		page.addTargetRequests(url);

		if (page.getResultItems().get("title") != null) {
			page.putField("url", page.getRequest().getUrl());
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

}
