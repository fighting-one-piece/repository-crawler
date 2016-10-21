package org.platform.crawler.webmagic.modules.secondhand.tongcheng.house;


import java.util.List;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class House implements PageProcessor {
	private Site site = Site.me().setRetryTimes(5).setSleepTime(5000)
			.setTimeOut(5000);

	@Override
	public void process(Page page) {
		List<String> url1 = page.getHtml().links()
				.regex("http://\\w+.58.com/ershoufang/").all();
		List<String> url = page.getHtml()
				.xpath("//*[@id='house-area']/a/@href").all();

		List<String> detailedUrl = page.getHtml()
				.xpath("//td[@class='t']/p/a/@href").all();
		url.addAll(url1);
		url.addAll(detailedUrl);
		page.addTargetRequests(url);
		page.putField(
				"房子介绍",
				page.getHtml()
						.xpath("//*[@id='main']/div[1]/div[1]/div[1]/allText()")
						.toString());
		page.putField("发布时间", page.getHtml()
				.xpath("//li[@class='time']/text()").toString());
		page.putField(
				"房价",
				page.getHtml()
						.xpath("//*[@id='main']/div[1]/div[2]/div/ul/li[1]/div[2]/span/text()")
						.toString());
		page.putField(
				"每平米价格",
				page.getHtml()
						.xpath("//*[@id='main']/div[1]/div[2]/div/ul/li[1]/div[2]/text()")
						.toString());

		String sf1 = page.getHtml()
				.xpath("//*[@id='J_fangdai']/span[1]/text()").toString();
		String sf2 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[2]/div[2]/span[1]/text()")
				.toString();
		if (sf1 != null && sf1.trim().length() > 0) {
			page.putField("首付价格", sf1);
		}
		if (sf2 != null && sf2.trim().length() > 0) {
			page.putField("首付价格", sf2);
		}
		String ss = page.getResultItems().get("首付价格");
		if (ss == null || ss.trim().length() == 0) {
			page.putField("首付价格", "房主暂未定价");
		}

		String yg1 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[2]/div[2]/span[2]/text()")
				.toString();
		String yg2 = page.getHtml()
				.xpath("//*[@id='J_fangdai']/span[2]/text()").toString();
		if (yg1 != null && yg1.trim().length() > 0) {
			page.putField("月供价格", yg1);
		}
		if (yg2 != null && yg2.trim().length() > 0) {
			page.putField("月供价格", yg2);
		}
		String yg = page.getResultItems().get("月供价格");
		if (yg == null || yg.trim().length() == 0) {
			page.putField("月供价格", "房主未定");
		}
		page.putField("房源图片",
				page.getHtml().xpath("//*[@id='img_smalls']/li/img/@src").all());

		String wz0 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[4]/allText()")
				.toString();
		String wz = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[5]/allText()")
				.toString();
		String wz1 = page.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div/ul/li[5]/allText()")
				.toString();
		String wz2 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[8]/allText()")
				.toString();
		String wz4 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[7]/allText()")
				.toString();

		String wz5 = page.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div/ul/li[4]/allText()")
				.toString();
		if (wz0 != null) {

			if (wz0.indexOf("位置") >= 0) {
				page.putField("位置", wz0);
			}
		}
		if (wz != null) {
			if (wz.indexOf("位置") >= 0) {
				page.putField("位置", wz);
			}
		}
		if (wz1 != null) {
			if (wz1.indexOf("位置") >= 0) {
				page.putField("位置", wz1);
			}
		}
		if (wz2 != null) {
			if (wz2.indexOf("位置") >= 0) {
				page.putField("位置", wz2);
			}
		}
		if (wz4 != null) {

			if (wz4.indexOf("位置") >= 0) {
				page.putField("位置", wz4);
			}
		}
		if (wz5 != null) {
			if (wz5.indexOf("位置") >= 0) {
				page.putField("位置", wz5);
			}
		}
		// String wz3=page.getResultItems().get("位置");
		// if(wz3==null || wz3.trim().length()==0){
		// page.putField("位置", "暂无详细位置");
		// }
		String hx1 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div/ul/li[4]/div[2]/text()")
				.toString();
		String hx2 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[4]/div[2]/text()")
				.toString();
		String hx4 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[3]/div[2]/text()")
				.toString();

		// *[@id='main']/div[1]/div[2]/div/ul/li[4]/div[2]/text()
		// *[@id='main']/div[1]/div[2]/div[2]/ul/li[4]/div[2]/text()
		if (hx1 != null && hx1.trim().length() > 0) {
			page.putField("户型", hx1);
		}
		if (hx2 != null && hx2.trim().length() > 0) {
			page.putField("户型", hx2);
		}
		if (hx4 != null && hx4.trim().length() > 0) {
			page.putField("户型", hx4);
		}
		String hx3 = page.getResultItems().get("户型");
		if (hx3 == null || hx3.trim().length() == 0) {
			page.putField("户型", "暂无详细介绍");
		}

		String dz1 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[5]/div[2]/text()")
				.toString();
		String dz2 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div/ul/li[6]/div[2]/text()")
				.toString();
		if (dz1 != null && dz1.trim().length() > 0) {
			page.putField("地址", dz1);
		}
		if (dz2 != null && dz2.trim().length() > 0) {
			page.putField("地址", dz2);
		}
		if (page.getResultItems().get("地址") == null
				|| page.getResultItems().get("地址").toString().trim().length() == 0) {
			page.putField("地址", "暂为发布具体地址");
		}

		String lxr1 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[7]/div[2]/span[1]/a/text()")
				.toString();
		String lxr2 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[8]/div[2]/span[1]/a/text()")
				.toString();
		String lxr3 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[3]/ul/li[2]/span[1]/a/text()")
				.toString();
		String lxr4 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div/ul/li[8]/div[2]/span[1]/a/text()")
				.toString();
		String lxr5 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div[2]/ul/li[6]/div[2]/span[1]/a/text()")
				.toString();

		String lxr6 = page
				.getHtml()
				.xpath("//*[@id='main']/div[1]/div[2]/div/ul/li[7]/div[2]/span[1]/a/text()")
				.toString();

		if (lxr1 != null && lxr1.trim().length() > 0) {
			page.putField("联系人", lxr1);
		}
		if (lxr2 != null && lxr2.trim().length() > 0) {
			page.putField("联系人", lxr2);
		}
		if (lxr3 != null && lxr3.trim().length() > 0) {
			page.putField("联系人", lxr3);
		}
		if (lxr4 != null && lxr4.trim().length() > 0) {
			page.putField("联系人", lxr4);
		}
		if (lxr5 != null && lxr5.trim().length() > 0) {
			page.putField("联系人", lxr5);
		}
		if (lxr6 != null && lxr6.trim().length() > 0) {
			page.putField("联系人", lxr6);
		}
		// ============================================================

		String lxdh1 = page.getHtml().xpath("//*[@id='t_phone']/img/@src")
				.toString();
		String lxdh2 = page.getHtml().xpath("//*[@id='t_phone']/text()")
				.toString();
		String lxdh3 = page.getHtml()
				.xpath("//*[@id='main']/div[1]/div[3]/ul/li[2]/span[3]/text()")
				.toString();
		if (lxdh1 != null && lxdh1.trim().length() > 0) {
			page.putField("联系电话", lxdh1);
		}
		if (lxdh2 != null && lxdh2.trim().length() > 0) {
			page.putField("联系电话", lxdh2);
		}
		if (lxdh3 != null && lxdh3.trim().length() > 0) {
			page.putField("联系电话", lxdh3);
		}
		page.putField(
				"住宅类型",
				page.getHtml()
						.xpath("//*[@id='fyms']/div[1]/div/ul/li[1]/ul/li[2]/text()")
						.toString());
		page.putField(
				"房屋类型",
				page.getHtml()
						.xpath("//*[@id='fyms']/div[1]/div/ul/li[2]/ul/li[2]/text()")
						.toString());
		page.putField(
				"建造年代",
				page.getHtml()
						.xpath("//*[@id='fyms']/div[1]/div/ul/li[3]/ul/li[2]/text()")
						.toString());
		page.putField(
				"产权",
				page.getHtml()
						.xpath("//*[@id='fyms']/div[1]/div/ul/li[4]/ul/li[2]/text()")
						.toString());
		page.putField(
				"装修程度",
				page.getHtml()
						.xpath("//*[@id='fyms']/div[1]/div/ul/li[1]/ul/li[4]/text()")
						.toString());
		page.putField(
				"建造结构",
				page.getHtml()
						.xpath("//*[@id='fyms']/div[1]/div/ul/li[2]/ul/li[4]/text()")
						.toString());
		page.putField(
				"房屋楼成",
				page.getHtml()
						.xpath("//*[@id='fyms']/div[1]/div/ul/li[3]/ul/li[4]/text()")
						.toString());
		page.putField(
				"朝向",
				page.getHtml()
						.xpath("//*[@id='fyms']/div[1]/div/ul/li[4]/ul/li[4]/text()")
						.toString());
		page.putField("房子来源网站", page.getRequest().getUrl());
	}

	@Override
	public Site getSite() {
		return site;
	}

	

}
