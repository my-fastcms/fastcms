package com.dbumama.market.service.api.agent;

import java.util.Date;
import java.util.List;

import com.dbumama.market.model.AgentAduitLog;
import com.dbumama.market.service.common.AbstractResultDto;

/**
 * 待审核分销商总代
 * @author wangjun
 *
 */
@SuppressWarnings("serial")
public class AgentAduitResultDto extends AbstractResultDto {

	private Long agentId;
	private String wxNick;		//微信昵称
	private String wxHeadimg;	//微信头像
	private String agentName;
	private String agentPhone;
	private String agentAddr;
	private String totalMoney;	//该用户总消费金额
	private Date applyDate;		//申请时间
	private Integer status;		//状态
	private String statusCn;		//状态中文
	private List<AgentAduitLog> aduitLogs;
	
	public AgentAduitResultDto(){
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
	public String getAgentAddr() {
		return agentAddr;
	}
	public void setAgentAddr(String agentAddr) {
		this.agentAddr = agentAddr;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusCn() {
		return statusCn;
	}
	public void setStatusCn(String statusCn) {
		this.statusCn = statusCn;
	}
	public List<AgentAduitLog> getAduitLogs() {
		return aduitLogs;
	}
	public void setAduitLogs(List<AgentAduitLog> aduitLogs) {
		this.aduitLogs = aduitLogs;
	}
}
