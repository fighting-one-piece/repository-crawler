package org.platform.crawler.webmagic.modules.secondhand.entity;

import java.util.List;

import org.platform.crawler.webmagic.modules.abstr.entity.PKAutoEntity;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://cd.58.com/ershouche/pn\\w+")
@HelpUrl("http://cd.58.com/ershouche/pn\\w+")
public class SecondCar extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	@ExtractBy(value = "//*[@id='infolist']/table/tbody/tr/td[2]/a[1]/text()", notNull = true)
	private List<String> names = null;
	
	@ExtractBy(value = "//*[@id='infolist']/table/tbody/tr/td[3]/text()[2]", notNull = true)
	private List<String> prices = null;
	
	@ExtractBy(value = "//*[@id='infolist']/table/tbody/tr/td[2]/p/text()", notNull = true)
	private List<String> description = null;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> getPrices() {
		return prices;
	}

	public void setPrices(List<String> prices) {
		this.prices = prices;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}
	
	
	
}
