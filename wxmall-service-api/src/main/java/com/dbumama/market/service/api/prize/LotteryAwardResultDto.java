/**
 * 文件名:LotteryAwardVo.java
 * 版本信息:1.0
 * 日期:2015-6-13
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.service.api.prize;

import com.dbumama.market.service.common.AbstractResultDto;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-6-13
 */
public class LotteryAwardResultDto extends AbstractResultDto{

	/**
	 * @author: wjun.java@gmail.com
	 * @date: 2015-6-13
	 */
	private static final long serialVersionUID = 1L;

	private String awardName;
	private String awardType;
	private String awardOdds;//中奖率
	private int awardCount;//奖品数量
	private int awardOutCount;//已中奖数量
	
	public String getAwardOdds() {
		return awardOdds;
	}
	public void setAwardOdds(String awardOdds) {
		this.awardOdds = awardOdds;
	}
	public int getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(int awardCount) {
		this.awardCount = awardCount;
	}
	public int getAwardOutCount() {
		return awardOutCount;
	}
	public void setAwardOutCount(int awardOutCount) {
		this.awardOutCount = awardOutCount;
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
	
}
