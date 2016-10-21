package org.platform.crawler.webmagic.modules.abstr.pipeline;

import java.io.Serializable;
import java.util.List;

import org.platform.crawler.webmagic.modules.abstr.mapper.GenericMapper;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component("dbPipeline")
public abstract class DBPipeline<Entity extends Serializable, PK extends Serializable> 
	implements Pipeline, PageModelPipeline<Entity> {
	
	public abstract GenericMapper<Entity, PK> obtainMapperInstance();

	@Override
	public void process(Entity entity, Task task) {
		obtainMapperInstance().insert(entity);
	}
	
	public void process(List<Entity> entities, Task task) {
		obtainMapperInstance().insert(entities);
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		
	}

}
