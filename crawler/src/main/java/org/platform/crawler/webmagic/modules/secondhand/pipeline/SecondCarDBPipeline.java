package org.platform.crawler.webmagic.modules.secondhand.pipeline;

import org.platform.crawler.webmagic.modules.abstr.mapper.GenericMapper;
import org.platform.crawler.webmagic.modules.abstr.pipeline.DBPipeline;
import org.platform.crawler.webmagic.modules.secondhand.entity.SecondCar;

public class SecondCarDBPipeline extends DBPipeline<SecondCar, Long> {

	@Override
	public GenericMapper<SecondCar, Long> obtainMapperInstance() {
		return null;
	}

}
