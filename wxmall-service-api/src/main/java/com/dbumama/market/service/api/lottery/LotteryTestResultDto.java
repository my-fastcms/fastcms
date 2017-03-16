package com.dbumama.market.service.api.lottery;

import com.dbumama.market.service.common.AbstractResultDto;

/**
 * wjun_java@163.com
 * 2016年8月5日
 */
@SuppressWarnings("serial")
public class LotteryTestResultDto extends AbstractResultDto{

	private String awardName;
	private String awardType;
	private String awardRate;
	private Integer prizeCount;
	private Integer luckCount;
	
	public LotteryTestResultDto(){
		setAwardType("");
		setLuckCount(0);
		setPrizeCount(0);
	}
	
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public String getAwardType() {
		return awardType;
	}
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}
	public String getAwardRate() {
		return awardRate;
	}
	public void setAwardRate(String awardRate) {
		this.awardRate = awardRate;
	}
	public Integer getPrizeCount() {
		return prizeCount;
	}
	public void setPrizeCount(Integer prizeCount) {
		this.prizeCount = prizeCount;
	}
	public Integer getLuckCount() {
		return luckCount;
	}
	public void setLuckCount(Integer luckCount) {
		this.luckCount = luckCount;
	}
	
}
