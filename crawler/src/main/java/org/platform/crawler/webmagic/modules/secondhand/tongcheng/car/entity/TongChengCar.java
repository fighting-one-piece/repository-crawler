package org.platform.crawler.webmagic.modules.secondhand.tongcheng.car.entity;

import java.util.List;

public class TongChengCar {
	//二手车名称
	private String carName = null;
	//二手车价格
	private String carPrice = null;
	//行驶里程
	private String mileage = null;
	//首次上牌时间
	private String firstDate = null;
	//排量 / 变速箱
	private String displacement = null;
	//卖主名 
	private String sellerName = null;
	//看车地址
	private String address = null;
	//车辆颜色
	private String color = null;
	//保养过程
	private String maintain = null;
	//排放标准
	private String emissions = null;
	//车辆描述
	private String carDescribe = null;
	//车辆图片
	private List<String> images = null;
	//链接地址
	private String href = null;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getCarPrice() {
		return carPrice;
	}
	public void setCarPrice(String carPrice) {
		this.carPrice = carPrice;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}
	public String getDisplacement() {
		return displacement;
	}
	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMaintain() {
		return maintain;
	}
	public void setMaintain(String maintain) {
		this.maintain = maintain;
	}
	public String getEmissions() {
		return emissions;
	}
	public void setEmissions(String emissions) {
		this.emissions = emissions;
	}
	public String getCarDescribe() {
		return carDescribe;
	}
	public void setCarDescribe(String carDescribe) {
		this.carDescribe = carDescribe;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	
	public TongChengCar(String carName, String carPrice, String mileage, String firstDate, String displacement,
			String sellerName, String address, String color, String maintain, String emissions, String carDescribe,
			List<String> images, String href) {
		super();
		this.carName = carName;
		this.carPrice = carPrice;
		this.mileage = mileage;
		this.firstDate = firstDate;
		this.displacement = displacement;
		this.sellerName = sellerName;
		this.address = address;
		this.color = color;
		this.maintain = maintain;
		this.emissions = emissions;
		this.carDescribe = carDescribe;
		this.images = images;
		this.href = href;
	}
	public TongChengCar() {
		super();
	}
	
	
}
