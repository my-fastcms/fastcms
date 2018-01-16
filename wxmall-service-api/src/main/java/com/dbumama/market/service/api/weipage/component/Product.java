package com.dbumama.market.service.api.weipage.component;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable {

	private String name;
	private String img;
	private String price;
	private String introduction;
	private String zekou;// 折扣
	private String zekouPrice;// 折扣价
	private Long expiresIn; // 倒计时
	// 实际动作
	private String detailLink;
	private String buyLink;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDetailLink() {
		return detailLink;
	}

	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}

	public String getBuyLink() {
		return buyLink;
	}

	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getZekou() {
		return zekou;
	}

	public void setZekou(String zekou) {
		this.zekou = zekou;
	}

	public String getZekouPrice() {
		return zekouPrice;
	}

	public void setZekouPrice(String zekouPrice) {
		this.zekouPrice = zekouPrice;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

}
