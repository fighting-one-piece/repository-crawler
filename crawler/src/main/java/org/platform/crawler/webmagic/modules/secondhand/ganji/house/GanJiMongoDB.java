package org.platform.crawler.webmagic.modules.secondhand.ganji.house;

import org.bson.Document;
import org.platform.crawler.utils.mongodb.MongoDBUtils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class GanJiMongoDB implements Pipeline{
	
	 MongoClient mongoClient = MongoDBUtils.getInstance().getClient();
	    
	@Override
	public void process(ResultItems resultItems, Task task) {
		String JiaGe = resultItems.get("JiaGe");
		String HuXing =resultItems.get("HuXing");
		String LouCeng =resultItems.get("LouCeng");
		String XiaoQu =resultItems.get("XiaoQu");
		String XiaoQu1 =resultItems.get("XiaoQu1");
		String WeiZhi =resultItems.get("WeiZhi");
		String DiZhi =resultItems.get("DiZhi");
		String name =resultItems.get("name");
		String phone =resultItems.get("phone");
		String url =resultItems.get("url");
		System.out.println(phone+"~~"+name+"~~"+JiaGe);

		
		
		String XiaoQu3=null;
        MongoDatabase database = mongoClient.getDatabase("test");  
        MongoCollection<Document> collection = database.getCollection("home");
        
        if(XiaoQu != null  && !"".equals(XiaoQu)){
   		 	XiaoQu3 =XiaoQu;
	   	}else{
	   		XiaoQu3=XiaoQu1;
	   	}
        if(phone != null  && !"".equals(phone)){
        	Document document = new Document();
            document.append("url", url).append("JiaGe", JiaGe).append("HuXing", HuXing).append("LouCeng", LouCeng).append("XiaoQu", XiaoQu3).append("WeiZhi", WeiZhi).append("DiZhi", DiZhi).append("name", name).append("phone", phone);
            collection.insertOne(document); 
        }
	}
      
}

