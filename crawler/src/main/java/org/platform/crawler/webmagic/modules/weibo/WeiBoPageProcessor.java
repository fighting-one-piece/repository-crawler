package org.platform.crawler.webmagic.modules.weibo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.platform.crawler.utils.mongodb.MongoDBUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class WeiBoPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10 * 1000);

	@Override
	public void process(Page page) {
        List<String> json_array = page.getJson().selectList(new JsonPathSelector("$.statuses")).all();
        for (int i = 0, len = json_array.size(); i < len; i++) {
        	System.out.println(json_array.get(i));
        	insert(json_array.get(i));
        }
        String current_url = page.getRequest().getUrl();
    	String regex = "page=\\d+";
    	Matcher matcher = Pattern.compile(regex).matcher(current_url);
    	if (matcher.find()) {
    		String curr_page_kv = matcher.group();
    		if (curr_page_kv.indexOf("=") != -1) {
    			int curr_page = Integer.parseInt(curr_page_kv.split("=")[1]) + 1;
    			page.addTargetRequest(current_url.replace(curr_page_kv, "page=" + curr_page));
    		}
    	}
	}
	
	public void insert(String json) {
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("test");  
        MongoCollection<Document> collection = database.getCollection("test");
        collection.insertOne(Document.parse(json));
        mongoClient.close();
	}

	@Override
	public Site getSite() {
		return site;
	}

}
