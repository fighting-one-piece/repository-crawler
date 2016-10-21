package org.platform.crawler.webmagic.modules.secondhand.tongcheng.house;

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

@Component("HousePippine")
public class HousePippine implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {
		String mpmjp1 = null;
		String fzjs = resultItems.get("房子介绍");
		String fj = resultItems.get("房价") + "万";
		String fbsj = resultItems.get("发布时间");
		String mpmjg = resultItems.get("每平米价格");
		if (mpmjg != null && mpmjg != "") {
			mpmjp1 = mpmjg
					.substring(mpmjg.indexOf("（") + 1, mpmjg.indexOf("）"));
		}
		String sfjg = resultItems.get("首付价格");
		String ygjg = resultItems.get("月供价格");
		List<String> fytp = resultItems.get("房源图片");
		String wz = resultItems.get("位置");
		String hx = resultItems.get("户型");
		String dz = resultItems.get("地址");
		String lxr = resultItems.get("联系人");
		String lxrdh = resultItems.get("联系电话");
		String zzlx = resultItems.get("住宅类型");
		String fwlx = resultItems.get("房屋类型");
		String jznd = resultItems.get("建造年代");
		String cq = resultItems.get("产权");
		String zxcd = resultItems.get("装修程度");
		String fwlc = resultItems.get("房屋楼成");
		String jzjg = resultItems.get("建造结构");
		String fwcx = resultItems.get("朝向");
		String fzlywz = resultItems.get("房子来源网站");
		if (fzjs != null) {
			System.out.println(fzjs + "      " + jzjg);
			MongoClient mongoclient = MongoDBUtils.getInstance().getClient();
			MongoDatabase mongoDatabase = mongoclient.getDatabase("secondhand");
			MongoCollection<Document> collection = mongoDatabase
					.getCollection("tongcheng_house");
			Document document1 = new Document();
			document1.append("_id", fzlywz).append("房子介绍", fzjs)
					.append("房价", fj).append("每平米价格", mpmjp1)
					.append("首付价格", sfjg).append("月供价格", ygjg)
					.append("房源图片", fytp).append("位置", wz).append("户型", hx)
					.append("地址", dz).append("联系人", lxr).append("联系电话", lxrdh)
					.append("发布时间", fbsj).append("住宅类型", zzlx)
					.append("房屋类型", fwlx).append("建造年代", jznd).append("产权", cq)
					.append("装修程度", zxcd).append("建造结构", jzjg)
					.append("朝向", fwcx).append("房屋楼层", fwlc);
			collection.insertOne(document1);

		}

	}

}
