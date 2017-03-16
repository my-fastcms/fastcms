package com.dbumama.market.service.api.agent;

import java.math.BigDecimal;

import com.dbumama.market.service.common.AbstractResultDto;
/**
 * 分销商等级
 * 
 *
 */
@SuppressWarnings("serial")
public class AgentRankResultDto extends AbstractResultDto{
       private Long id;
       private String rankName;
       private Integer rankWeight;
       private String firstRate;
       private String secondRate;
       private String thirdRate;
       private Integer rewardValue;
       private Integer getCashTime;
       private Integer getCashLimit;
       private Integer childrenCount;
       private BigDecimal totalCommission;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	public Integer getRankWeight() {
		return rankWeight;
	}
	public void setRankWeight(Integer rankWeight) {
		this.rankWeight = rankWeight;
	}
	public String getFirstRate() {
		return firstRate;
	}
	public void setFirstRate(String firstRate) {
		this.firstRate = firstRate;
	}
	public String getSecondRate() {
		return secondRate;
	}
	public void setSecondRate(String secondRate) {
		this.secondRate = secondRate;
	}
	public String getThirdRate() {
		return thirdRate;
	}
	public void setThirdRate(String thirdRate) {
		this.thirdRate = thirdRate;
	}
	public Integer getRewardValue() {
		return rewardValue;
	}
	public void setRewardValue(Integer rewardValue) {
		this.rewardValue = rewardValue;
	}
	public Integer getGetCashTime() {
		return getCashTime;
	}
	public void setGetCashTime(Integer getCashTime) {
		this.getCashTime = getCashTime;
	}
	public Integer getGetCashLimit() {
		return getCashLimit;
	}
	public void setGetCashLimit(Integer getCashLimit) {
		this.getCashLimit = getCashLimit;
	}
	public Integer getChildrenCount() {
		return childrenCount;
	}
	public void setChildrenCount(Integer childrenCount) {
		this.childrenCount = childrenCount;
	}
	public BigDecimal getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}
       
       
}
