package com.dbumama.market.service.api.agent;

import java.util.Date;

import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class AgentResultDto extends AbstractResultDto{
	private Long agentId;
	private String wxNick;		//微信昵称
	private String wxHeadimg;	//微信头像
	private String agentName;
	private String agentPhone;
	private String totalMoney;	//在公众号总消费金额
	private String memberCount;	//下级会员数
	private Date created;		//申请时间
	private Date aduitDate;		//最终审核通过时间
	
	public AgentResultDto(){
		setMemberCount("0");
		setTotalMoney("0.00");
	}
	
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public String getWxNick() {
		return wxNick;
	}
	public void setWxNick(String wxNick) {
		this.wxNick = wxNick;
	}
	public String getWxHeadimg() {
		return wxHeadimg;
	}
	public void setWxHeadimg(String wxHeadimg) {
		this.wxHeadimg = wxHeadimg;
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
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getAduitDate() {
		return aduitDate;
	}
	public void setAduitDate(Date aduitDate) {
		this.aduitDate = aduitDate;
	}
}
