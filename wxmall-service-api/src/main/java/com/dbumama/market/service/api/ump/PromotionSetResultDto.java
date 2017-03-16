package com.dbumama.market.service.api.ump;

import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class PromotionSetResultDto extends AbstractResultDto{

	private Long id;
	private Long productId;
	private Long promotinId;
	private String productName;
	private String productImg;
	private String productPrice;
	private Integer type;
	private Float zhekou;
	private Float jianjia;
	private Float promotionValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getPromotinId() {
		return promotinId;
	}
	public void setPromotinId(Long promotinId) {
		this.promotinId = promotinId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Float getZhekou() {
		return zhekou;
	}
	public void setZhekou(Float zhekou) {
		this.zhekou = zhekou;
	}
	public Float getJianjia() {
		return jianjia;
	}
	public void setJianjia(Float jianjia) {
		this.jianjia = jianjia;
	}
	public Float getPromotionValue() {
		return promotionValue;
	}
	public void setPromotionValue(Float promotionValue) {
		this.promotionValue = promotionValue;
	}
	
}
