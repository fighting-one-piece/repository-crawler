package org.platform.crawler.webmagic.modules.secondhand.ganji.car.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.platform.crawler.utils.mongodb.MongoDBUtils;
import org.platform.crawler.webmagic.modules.secondhand.ganji.car.entity.Car;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component("carPipeline")
public class CarPipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		List<Car> cars = resultItems.get("cars");
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
		MongoDatabase database = mongoClient.getDatabase("secondhand");
//		MongoDatabase database = mongoClient.getDatabase("test");
		MongoCollection<Document> collection = database.getCollection("ganji_car");  
		List<Document> documents = new ArrayList<Document>();  
		Document document = null;  
		String engineKey = "";
		String engineValue = "";
		String gearboxKey = "";
		String gearboxValue = "";
		String emissionStandardsKey = "";
		String emissionStandardsValue = "";
		String colorKey = "";
		String colorValue = "";
		String structureKey = "";
		String structureValue = "";
		String mileageKey = "";
		String mileageValue = "";
		String dateKey = "";
		String dateValue = "";
		String propertiesKey = "";
		String propertiesValue = "";
		String dueTimeKey = "";
		String dueTimeValue = "";
		String unscientificTimeKey = "";
		String unscientificTimeValue = "";
		String accidentsKey = "";
		String accidentsValue = "";
		for(Car car : cars){  
			document = new Document();  
			String engine = car.getEngine();
			String gearbox = car.getGearbox();
			String emissionStandards = car.getEmissionStandards();
			String color = car.getColor();
			String structure = car.getStructure();
			String mileage = car.getMileage();
			String date = car.getDate();
			String properties = car.getProperties();
			String dueTime = car.getDueTime();
			String unscientificTime = car.getUnscientificTime();
			String accidents = car.getAccidents();
			if(engine != null){
				engineKey = engine.substring(0,engine.indexOf('：'));
				engineValue = engine.substring(engine.indexOf('：') + 1);
				document.append(engineKey, engineValue);
			}
			if(gearbox != null){
				gearboxKey = gearbox.substring(0,engine.indexOf('：'));
				gearboxValue = gearbox.substring(engine.indexOf('：') + 1);
				document.append(gearboxKey, gearboxValue);
			}
			if(emissionStandards != null){
				emissionStandardsKey = emissionStandards.substring(0,engine.indexOf('：'));
				emissionStandardsValue = emissionStandards.substring(engine.indexOf('：'));
				document.append(emissionStandardsKey, emissionStandardsValue);
			}
			if(color != null){
				colorKey = color.substring(0,engine.indexOf('：'));
				colorValue = color.substring(engine.indexOf('：'));
				document.append(colorKey, colorValue);
			}
			if(structure != null){
				structureKey = structure.substring(0,engine.indexOf('：'));
				structureValue = structure.substring(engine.indexOf('：'));
				document.append(structureKey, structureValue);
			}
			if(mileage != null){
				mileageKey = mileage.substring(0,engine.indexOf('：'));
				mileageValue = mileage.substring(engine.indexOf('：'));
				document.append(mileageKey, mileageValue);
			}
			if(date != null){
				dateKey = date.substring(0,engine.indexOf('：'));
				dateValue = date.substring(engine.indexOf('：'));
				document.append(dateKey, dateValue);
			}
			if(properties != null){
				propertiesKey = properties.substring(0,engine.indexOf('：'));
				propertiesValue = properties.substring(engine.indexOf('：'));
				document.append(propertiesKey, propertiesValue);
			}
			if(dueTime != null){
				dueTimeKey = dueTime.substring(0,engine.indexOf('：'));
				dueTimeValue = dueTime.substring(engine.indexOf('：'));
				document.append(dueTimeKey, dueTimeValue);
			}
			if(unscientificTime != null){
				unscientificTimeKey = unscientificTime.substring(0,engine.indexOf('：'));
				unscientificTimeValue = unscientificTime.substring(engine.indexOf('：') + 1);
				document.append(unscientificTimeKey, unscientificTimeValue);
			}
			if(accidents != null){
				accidentsKey = accidents.substring(0,engine.indexOf('：'));
				accidentsValue = accidents.substring(engine.indexOf('：'));
				document.append(accidentsKey, accidentsValue);
			}
			document.append("_id", car.get_id()).append("carName", car.getCarName()).append("age", car.getAge()).append("km",car.getKm()).append("linkName",car.getLinkName()).append("linkPhone", car.getLinkPhone()).append("openDate", car.getOpenDate()).append("price", car.getPrice());
			documents.add(document);  
		}  
		collection.insertMany(documents);  
//		mongoClient.close(); 
	}

}
