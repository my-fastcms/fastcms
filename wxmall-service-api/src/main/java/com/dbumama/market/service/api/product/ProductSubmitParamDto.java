package com.dbumama.market.service.api.product;

import java.math.BigDecimal;

import com.dbumama.market.service.api.common.AbstractParamDto;

@SuppressWarnings("serial")
public class ProductSubmitParamDto extends AbstractParamDto{

	private Boolean isUnifiedSpec;
	private String [] images;
	private Long productCategoryId;
	private Long[] specificationIds;
	private String[] specificationValues;
	private Boolean isMarketable;
	private String price;
	private String marketPrice;
	private String stock;
	private String name;
	private String mainImage;
	private String introduction;
	private String priceAndStock;			//商品规格对应的价格跟库存
	private Integer deliveryType;
	private BigDecimal deliveryFees;
	private Long deliveryTemplateId;
	private BigDecimal deliveryWeight;
	public ProductSubmitParamDto(Long sellerId, String[] images, Long productCategoryId, String name, String mainImage,
			String introduction) {
		this.sellerId = sellerId;
		this.images = images;
		this.productCategoryId = productCategoryId;
		this.name = name;
		this.mainImage = mainImage;
		this.introduction = introduction;
	}
	
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Boolean getIsUnifiedSpec() {
		return isUnifiedSpec;
	}
	public void setIsUnifiedSpec(Boolean isUnifiedSpec) {
		this.isUnifiedSpec = isUnifiedSpec;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMainImage() {
		return mainImage;
	}
	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public Long[] getSpecificationIds() {
		return specificationIds;
	}
	public void setSpecificationIds(Long[] specificationIds) {
		this.specificationIds = specificationIds;
	}
	public String[] getSpecificationValues() {
		return specificationValues;
	}
	public void setSpecificationValues(String[] specificationValues) {
		this.specificationValues = specificationValues;
	}
	public Boolean getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getPriceAndStock() {
		return priceAndStock;
	}
	public void setPriceAndStock(String priceAndStock) {
		this.priceAndStock = priceAndStock;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public BigDecimal getDeliveryFees() {
		return deliveryFees;
	}
	public void setDeliveryFees(BigDecimal deliveryFees) {
		this.deliveryFees = deliveryFees;
	}
	public Long getDeliveryTemplateId() {
		return deliveryTemplateId;
	}
	public void setDeliveryTemplateId(Long deliveryTemplateId) {
		this.deliveryTemplateId = deliveryTemplateId;
	}
	public BigDecimal getDeliveryWeight() {
		return deliveryWeight;
	}
	public void setDeliveryWeight(BigDecimal deliveryWeight) {
		this.deliveryWeight = deliveryWeight;
	}
	
}
