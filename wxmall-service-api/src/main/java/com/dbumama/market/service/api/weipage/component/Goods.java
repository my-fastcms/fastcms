package com.dbumama.market.service.api.weipage.component;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Goods implements Serializable {
	private String listStyle;
	private String itemStyle;
	private boolean showName = true;
	private boolean showBuybtn = true;
	private boolean showIntroduction = false;
	private boolean showPrice = true;
	private String activityId;
	private List<Long> goodsList;
	private List<Product> productList;

	public String getListStyle() {
		return listStyle;
	}

	public void setListStyle(String listStyle) {
		this.listStyle = listStyle;
	}

	public String getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(String itemStyle) {
		this.itemStyle = itemStyle;
	}

	public boolean isShowBuybtn() {
		return showBuybtn;
	}

	public void setShowBuybtn(boolean showBuybtn) {
		this.showBuybtn = showBuybtn;
	}

	public boolean isShowIntroduction() {
		return showIntroduction;
	}

	public void setShowIntroduction(boolean showIntroduction) {
		this.showIntroduction = showIntroduction;
	}

	public List<Long> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Long> goodsList) {
		this.goodsList = goodsList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public boolean isShowName() {
		return showName;
	}

	public void setShowName(boolean showName) {
		this.showName = showName;
	}

	public boolean isShowPrice() {
		return showPrice;
	}

	public void setShowPrice(boolean showPrice) {
		this.showPrice = showPrice;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
