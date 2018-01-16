package com.dbumama.market.service.api.ump;

import java.math.BigDecimal;

import com.dbumama.market.service.api.common.AbstractResultDto;

@SuppressWarnings("serial")
public class ProdCashbackResultDto extends AbstractResultDto {
	//tag支付随机返现，最高 maxCash元，还剩4天结束
	private String cashbackName;	//活动名称
	private String tag;				//显示标签
	private BigDecimal maxCash;		//最高返现
	private String backTime;		//时间
	private Integer limit;			//订单上限
	private BigDecimal percentStart;	//区间最小值
	private BigDecimal percentEnd;		//区间最大值
	private Integer cashbackMethod;		//返现方式

	public String getCashbackName() {
		return cashbackName;
	}
	public void setCashbackName(String cashbackName) {
		this.cashbackName = cashbackName;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public BigDecimal getMaxCash() {
		return maxCash;
	}
	public void setMaxCash(BigDecimal maxCash) {
		this.maxCash = maxCash;
	}
	public String getBackTime() {
		return backTime;
	}
	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public BigDecimal getPercentStart() {
		return percentStart;
	}
	public void setPercentStart(BigDecimal percentStart) {
		this.percentStart = percentStart;
	}
	public BigDecimal getPercentEnd() {
		return percentEnd;
	}
	public void setPercentEnd(BigDecimal percentEnd) {
		this.percentEnd = percentEnd;
	}
	public Integer getCashbackMethod() {
		return cashbackMethod;
	}
	public void setCashbackMethod(Integer cashbackMethod) {
		this.cashbackMethod = cashbackMethod;
	}
}
