package com.dbumama.market.service.api.ump;

import com.dbumama.market.service.api.common.AbstractResultDto;
@SuppressWarnings("serial")
public class ProdGroupResultDto extends AbstractResultDto {
	private Long groupId;     //拼团活动id 
    private String groupNum;  //几人团
    private String collagePrice; //拼团价格
    private Integer quota;		//限购件数
	private String groupTime;
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}
	public String getCollagePrice() {
		return collagePrice;
	}
	public void setCollagePrice(String collagePrice) {
		this.collagePrice = collagePrice;
	}
	public Integer getQuota() {
		return quota;
	}
	public void setQuota(Integer quota) {
		this.quota = quota;
	}
	public String getGroupTime() {
		return groupTime;
	}
	public void setGroupTime(String groupTime) {
		this.groupTime = groupTime;
	}
     
}
