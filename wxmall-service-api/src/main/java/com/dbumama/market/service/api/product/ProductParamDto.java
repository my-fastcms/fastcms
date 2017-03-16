package com.dbumama.market.service.api.product;

import com.dbumama.market.service.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class ProductParamDto extends AbstractPageParamDto{
	private Long sellerId;
    private Long productId;
    
    protected ProductParamDto(Integer pageNo) {
		super(pageNo);
	}
    
    public ProductParamDto(Long sellerId){
    	super(0);
    	this.sellerId = sellerId;
    }
    
    public ProductParamDto(Long sellerId, Long productId){
    	super(0);
    	this.sellerId = sellerId;
    	this.productId = productId;
    }
    
    public ProductParamDto(Long sellerId, Integer pageNo){
    	super(pageNo);
    	this.sellerId = sellerId;
    }
    
	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
}
