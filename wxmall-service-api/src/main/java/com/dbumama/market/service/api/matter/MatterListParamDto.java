package com.dbumama.market.service.api.matter;

import com.dbumama.market.service.api.common.AbstractPageParamDto;
@SuppressWarnings("serial")
public class MatterListParamDto extends AbstractPageParamDto{
	public MatterListParamDto(Long sellerId, Integer pageNo) {
		super(sellerId, pageNo);
	}

}
