package com.dbumama.market.service.api.agent;

import com.dbumama.market.service.common.AbstractPageParamDto;
@SuppressWarnings("serial")
public class AgentRankParamDto extends AbstractPageParamDto{
	private String rankName;
	
	public AgentRankParamDto(Long sellerId, Integer pageNo) {
		super(sellerId, pageNo);
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	
	
}
