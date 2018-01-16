package com.dbumama.market.service.api.product;

import java.math.BigDecimal;

import com.dbumama.market.service.api.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class ProductMobileParamDto extends AbstractPageParamDto{

	public ProductMobileParamDto(Long sellerId, int pageNo) {
		super(sellerId, pageNo);
	}
	
	private Long categId;
	private BigDecimal startPrice;
	private BigDecimal endPrice;
	private String keyword;
	private String orderBy;
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
