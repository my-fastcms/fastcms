package com.dbumama.market.service.api.qiandao;

import java.util.Date;

import com.dbumama.market.service.api.common.AbstractResultDto;

/**
 * wjun_java@163.com
 * 2016年7月21日
 */
@SuppressWarnings("serial")
public class QiandaoResultDto extends AbstractResultDto{

	private Long id;
	private String qiandaoName;
	private String qrCode;
	private String wirlessUrl;
	private String status;
	private Date startDate;
	private Date endDate;
	
	public QiandaoResultDto(){
		setWirlessUrl("");
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQiandaoName() {
		return qiandaoName;
	}
	public void setQiandaoName(String qiandaoName) {
		this.qiandaoName = qiandaoName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
}
