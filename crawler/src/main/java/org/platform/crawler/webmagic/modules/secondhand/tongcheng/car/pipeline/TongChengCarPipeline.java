package org.platform.crawler.webmagic.modules.secondhand.tongcheng.car.pipeline;

import org.bson.Document;
import org.platform.crawler.utils.mongodb.MongoDBUtils;
import org.platform.crawler.webmagic.modules.secondhand.tongcheng.car.entity.TongChengCar;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class TongChengCarPipeline implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {

		TongChengCar car = resultItems.get("car");
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();
		MongoDatabase database = mongoClient.getDatabase("secondhand");
		MongoCollection<Document> collection = database.getCollection("tongcheng_car");
		Document document = null;
		if (car.getCarName() != null) {
			document = new Document();
			document.append("href", car.getHref());
			document.append("carName", car.getCarName());
			document.append("address", car.getAddress());
			document.append("carDescribe", car.getCarDescribe());
			document.append("carPrice", car.getCarPrice());
			document.append("color", car.getColor());
			document.append("displacement", car.getDisplacement());
			document.append("emissions", car.getEmissions());
			document.append("firstDate", car.getFirstDate());
			document.append("images", car.getImages());
			document.append("maintain", car.getMaintain());
			document.append("mileage", car.getMileage());
			document.append("sellerName", car.getSellerName());

			collection.insertOne(document);
		}
	}

}
