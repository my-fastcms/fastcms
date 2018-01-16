package com.dbumama.market.service.api.lottery;

import java.util.Date;

import com.dbumama.market.service.api.common.AbstractResultDto;

/**
 * wjun_java@163.com
 * 2016年7月3日
 */
@SuppressWarnings("serial")
public class LotteryResultDto extends AbstractResultDto {

	private Long id;
	private String lotteryName;
	private Date startDate;			//有效开始时间
	private Date endDate;
	private String qrCode;
	private String wirlessUrl;
	private int hadDay; 			//已开始天数
	private int leftDay;			//剩余天数
	private String status;			//进行中，已开始，未开始
	private String lotteryTypeCH;
	private int lotteryType;
	private String hadAwardStatus;	//是否有设置奖品
	
	public LotteryResultDto(){
		setHadAwardStatus("");
		setLotteryName("");
		setWirlessUrl("");
	} 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public int getHadDay() {
		return hadDay;
	}

	public void setHadDay(int hadDay) {
		this.hadDay = hadDay;
	}

	public int getLeftDay() {
		return leftDay;
	}

	public void setLeftDay(int leftDay) {
		this.leftDay = leftDay;
	}
	public String getLotteryTypeCH() {
		return lotteryTypeCH;
	}

	public void setLotteryTypeCH(String lotteryTypeCH) {
		this.lotteryTypeCH = lotteryTypeCH;
	}

	public int getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}
	
	public String getHadAwardStatus() {
		return hadAwardStatus;
	}
	public void setHadAwardStatus(String hadAwardStatus) {
		this.hadAwardStatus = hadAwardStatus;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getWirlessUrl() {
		return wirlessUrl;
	}

	public void setWirlessUrl(String wirlessUrl) {
		this.wirlessUrl = wirlessUrl;
	}
	
}
