package com.dbumama.market.service.api.product;

import java.math.BigDecimal;

import com.dbumama.market.service.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class ProductMobileParamDto extends AbstractPageParamDto{

	public ProductMobileParamDto(Long sellerId, int pageNo) {
		super(pageNo);
		this.sellerId = sellerId;
	}
	
	private Long categId;
	private BigDecimal startPrice;
	private BigDecimal endPrice;
	
	public Long getCategId() {
		return categId;
	}
	public void setCategId(Long categId) {
		this.categId = categId;
	}
	public BigDecimal getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}
	public BigDecimal getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(BigDecimal endPrice) {
		this.endPrice = endPrice;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
}
