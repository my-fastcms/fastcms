package com.dbumama.market.service.api.product;

import com.dbumama.market.service.api.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class ProductParamDto extends AbstractPageParamDto{
    private Long productId;
    private Integer isMarketable;
    private Long promotionId;
    private String productIds; //多个商品id
    
	public ProductParamDto(Long sellerId, Integer pageNo) {
		super(sellerId, pageNo);
	}
    
	public ProductParamDto(Long sellerId) {
		super(0);
		this.sellerId = sellerId;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(Integer isMarketable) {
		this.isMarketable = isMarketable;
	}
	public Long getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	
}
