package com.dbumama.market.service.api.specification;

import com.dbumama.market.service.api.common.AbstractParamDto;

@SuppressWarnings("serial")
public class SpecificationParamDto extends AbstractParamDto{

	private Long productId;
	private Long specificationId;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(Long specificationId) {
		this.specificationId = specificationId;
	}
	
}
