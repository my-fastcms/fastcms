package com.dbumama.market.service.api.product;

import java.math.BigDecimal;

/**
 * 商品限时满减送
 * @author drs
 *
 */
public class ProductFullCutResultDto {
	private String fullCutTime; //时间
	private String fullCutInfo; //满送现金,包邮
	private BigDecimal meet;	//满多少
	private BigDecimal cash;	//减价多少
	private Integer postage;	//是否免邮
	public Integer getPostage() {
		return postage;
	}
	public void setPostage(Integer postage) {
		this.postage = postage;
	}
	public BigDecimal getMeet() {
		return meet;
	}
	public void setMeet(BigDecimal meet) {
		this.meet = meet;
	}
	public BigDecimal getCash() {
		return cash;
	}
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	public String getFullCutTime() {
		return fullCutTime;
	}
	public void setFullCutTime(String fullCutTime) {
		this.fullCutTime = fullCutTime;
	}
	public String getFullCutInfo() {
		return fullCutInfo;
	}
	public void setFullCutInfo(String fullCutInfo) {
		this.fullCutInfo = fullCutInfo;
	}
}
