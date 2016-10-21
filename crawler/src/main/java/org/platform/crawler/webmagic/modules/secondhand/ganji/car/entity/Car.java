package org.platform.crawler.webmagic.modules.secondhand.ganji.car.entity;

import java.io.Serializable;

public class Car implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _id;		//mongodb的_id设置为爬过的页面链接
	private String carName;  //二手车名
	private String openDate; 	//发布时间
	private String price;	//价格
	private String age;	//车龄
	private String km;	//总路程
	private String linkPhone;	//联系人电话
	private String linkName;	//联系人
	private String engine;	//排气量
	private String gearbox;	//变速箱
	private String emissionStandards;	//排放标准
	private String color;		//颜色
	private String structure;	//车身结构
	private String mileage;	//行驶里程
	private String date;		//上牌时间
	private String properties;	//使用性质
	private String dueTime;	//年检到期
	private String unscientificTime;	//交强险到期
	private String accidents;	//事故情况
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getKm() {
		return km;
	}
	public void setKm(String km) {
		this.km = km;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getGearbox() {
		return gearbox;
	}
	public void setGearbox(String gearbox) {
		this.gearbox = gearbox;
	}
	public String getEmissionStandards() {
		return emissionStandards;
	}
	public void setEmissionStandards(String emissionStandards) {
		this.emissionStandards = emissionStandards;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public String getUnscientificTime() {
		return unscientificTime;
	}
	public void setUnscientificTime(String unscientificTime) {
		this.unscientificTime = unscientificTime;
	}
	public String getAccidents() {
		return accidents;
	}
	public void setAccidents(String accidents) {
		this.accidents = accidents;
	}
	
	
	
}
