package com.dbumama.market.service.api.specification;

import com.dbumama.market.service.common.AbstractParamDto;

@SuppressWarnings("serial")
public class SpecificationParamDto extends AbstractParamDto{

	private Long productId;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	private Long sellerId;

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
}
