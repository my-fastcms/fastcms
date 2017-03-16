package com.dbumama.market.service.api.prize;

import com.dbumama.market.service.common.AbstractResultDto;

/**
 * wjun_java@163.com
 * 2016年6月15日
 */
public class PrizeResultDto extends AbstractResultDto {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String prizeName;
	private String prizeImg;
	private String prizePrice;
	private Long prizeTypeId;
	private String prizeType;
	private String typeCode;
	private String status;
	private String publishCount;
	private String hadOutCount;
	private String startDate;
	private String endDate;
	
	public PrizeResultDto(){
		setStartDate("");
		setEndDate("");
		setHadOutCount("0");
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public String getPrizeImg() {
		return prizeImg;
	}
	public void setPrizeImg(String prizeImg) {
		this.prizeImg = prizeImg;
	}
	public String getPrizePrice() {
		return prizePrice;
	}
	public void setPrizePrice(String prizePrice) {
		this.prizePrice = prizePrice;
	}
	public Long getPrizeTypeId() {
		return prizeTypeId;
	}
	public void setPrizeTypeId(Long prizeTypeId) {
		this.prizeTypeId = prizeTypeId;
	}
	public String getPrizeType() {
		return prizeType;
	}
	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPublishCount() {
		return publishCount;
	}
	public void setPublishCount(String publishCount) {
		this.publishCount = publishCount;
	}
	public String getHadOutCount() {
		return hadOutCount;
	}
	public void setHadOutCount(String hadOutCount) {
		this.hadOutCount = hadOutCount;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
