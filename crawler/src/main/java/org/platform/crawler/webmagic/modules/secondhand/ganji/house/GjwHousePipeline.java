package org.platform.crawler.webmagic.modules.secondhand.ganji.house;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.platform.crawler.utils.mongodb.MongoDBUtils;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component("gjwHousePipeline")
public class GjwHousePipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		
		String jieshao=resultItems.get("介绍");
		String shoujia=resultItems.get("售价")+"万";
		String danjia=resultItems.get("单价");
		String huxing=resultItems.get("户型");
		String gaikuang=resultItems.get("概况");
		String loucen=resultItems.get("楼层");
		String xiaoqu=resultItems.get("小区");
		String dizi=resultItems.get("位置");
		String wz;
		wz=dizi.substring(dizi.indexOf("：")+1);
		String xxdizi=resultItems.get("详细地址");
		String lianhxiren=resultItems.get("联系人");
		String laingxifangshi=resultItems.get("联系方式");
		String fabusj=resultItems.get("发布时间");
		String laiyuan="赶集网二手房信息";
		String ids=resultItems.get("id");
		//String duo=resultItems.get("多");
		Object[] b={jieshao,shoujia,danjia,huxing,gaikuang,loucen,xiaoqu,wz,xxdizi,lianhxiren,laingxifangshi,fabusj,laiyuan};
		for (Object object : b) {
			System.out.println(object);
		}
		
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
        MongoDatabase database = mongoClient.getDatabase("secondhand");  
        MongoCollection<Document> collection = database.getCollection("ganji_house");  
        List<Document> documents = new ArrayList<Document>();  
        Document document = null;  
        
	    document = new Document();  
	 	document.append("介绍",jieshao).append("售价",shoujia).append("单价",danjia).append("户型",huxing).append("概况",gaikuang)
	 	.append("楼层",loucen).append("小区",xiaoqu).append("位置",wz).append("详细地址",xxdizi).append("联系人",lianhxiren)
	 	.append("联系方式",laingxifangshi).append("发布时间",fabusj).append("来源",laiyuan).append("_id",ids);
	 	documents.add(document);
 	    
        collection.insertMany(documents); 
//        mongoClient.close();  
	}

}
