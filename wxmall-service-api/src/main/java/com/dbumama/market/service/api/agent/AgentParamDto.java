package com.dbumama.market.service.api.agent;

import com.dbumama.market.service.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class AgentParamDto extends AbstractPageParamDto{

	private Long parentId;		//上级id 为空表示查询总代
	private String agentName;
	private String agentPhone;
	private String wxNick;
	
	public AgentParamDto(Long sellerId, Integer pageNo) {
		super(sellerId, pageNo);
	}

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentPhone() {
		return agentPhone;
	}
	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}
	public String getWxNick() {
		return wxNick;
	}
	public void setWxNick(String wxNick) {
		this.wxNick = wxNick;
	}
}
