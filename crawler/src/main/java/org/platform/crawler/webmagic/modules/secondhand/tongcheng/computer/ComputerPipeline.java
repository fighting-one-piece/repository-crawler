package org.platform.crawler.webmagic.modules.secondhand.tongcheng.computer;

import org.bson.Document;
import org.platform.crawler.utils.mongodb.MongoDBUtils;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


@Component("computerPipeline")
public class ComputerPipeline implements Pipeline {
	
	MongoClient mongoClient = MongoDBUtils.getInstance().getClient();

	@Override
	public void process(ResultItems resultItems, Task task) {
		try {
			String title = resultItems.get("title");
			String price = resultItems.get("price");
			String zone = resultItems.get("zone");
			String old = resultItems.get("old");
			String product = resultItems.get("product");
			String seller = resultItems.get("seller");
			String phone = resultItems.get("phone");
			String phoneNote = resultItems.get("phoneNote");
			String url = resultItems.get("url");

			MongoDatabase database = mongoClient.getDatabase("secondhand");
			MongoCollection<Document> collection = database
					.getCollection("tongcheng_computer");
			Document document1 = new Document();
			if (title != null) {
				document1.append("title", title).append("price", price)
						.append("zone", zone).append("old", old)
						.append("product", product).append("seller", seller)
						.append("phone", phone).append("phoneNote", phoneNote)
						.append("_id", url);
				collection.insertOne(document1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
