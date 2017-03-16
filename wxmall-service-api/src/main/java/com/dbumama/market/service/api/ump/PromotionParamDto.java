package com.dbumama.market.service.api.ump;

import com.dbumama.market.service.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class PromotionParamDto extends AbstractPageParamDto{

	public PromotionParamDto(Long sellerId, Integer pageNo, Integer pageSize) {
		super(sellerId, pageNo, pageSize);
	}

}
