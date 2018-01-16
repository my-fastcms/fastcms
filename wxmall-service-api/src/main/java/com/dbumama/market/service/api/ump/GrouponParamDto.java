package com.dbumama.market.service.api.ump;

import com.dbumama.market.service.api.common.AbstractPageParamDto;
@SuppressWarnings("serial")
public class GrouponParamDto extends AbstractPageParamDto {

	/**
	 * @param pageNo
	 */
	public GrouponParamDto(Long sellerId, Integer pageNo) {
		super(sellerId, pageNo);
	}

}
