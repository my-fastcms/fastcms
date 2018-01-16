package com.dbumama.market.service.api.ump;

import com.dbumama.market.service.api.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class PromotionParamDto extends AbstractPageParamDto{

	/**
	 * @param pageNo
	 */
	public PromotionParamDto(Long sellerId, Integer pageNo) {
		super(sellerId, pageNo);
	}

}
