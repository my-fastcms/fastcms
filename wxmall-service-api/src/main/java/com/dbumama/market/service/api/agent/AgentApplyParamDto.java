package com.dbumama.market.service.api.agent;

import com.dbumama.market.service.common.AbstractParamDto;

/**
 * 分销商申请
 * @author wangjun
 *
 */
@SuppressWarnings("serial")
public class AgentApplyParamDto extends AbstractParamDto{

	private Long buyerId;			//申请人
	private Long sellerId;			//谁的代理
	private Long parentId;			//上级代理
	private String agentName;		//代理人真实姓名
	private String agentPhone;		//代理人手机号
	private Long areaId;			//代理人所在区域id
	private String addr; 			//包含省市县 + 街道地址
	private String phoneCode;		//短信验证码
	private String code;			//系统验证码
	private String codeInSession;	//session中code码
	
	public AgentApplyParamDto(Long buyerId, Long sellerId, String agentPhone) {
		super();
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.agentPhone = agentPhone;
	}
	
	public String getCodeInSession() {
		return codeInSession;
	}
	public void setCodeInSession(String codeInSession) {
		this.codeInSession = codeInSession;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
