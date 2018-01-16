package com.dbumama.market.service.api.ump;

import java.util.List;

import com.dbumama.market.service.api.common.AbstractResultDto;

/**
 * 正在进行中的拼团活动。。。
 * @author wangjun
 *
 */
@SuppressWarnings("serial")
public class GroupingResultDto extends AbstractResultDto{

	private Long groupHeaderId;				//团长id
	private String groupHeader;				//团长nick
	private String groupHeaderImg;			//团长头像
	private Integer diffUserCount;			//还需要多少人成团		
	private Long expiresIn;					//倒计时
	private Boolean isExpires;				//是否过期
	List<GrouponUserResultDto> groupUsers;
	private Boolean isGrouped;				//当前购买用户是否已拼过团
	
	public GroupingResultDto (){
		setIsExpires(false);
		setIsGrouped(false);
	}
	
	public Long getGroupHeaderId() {
		return groupHeaderId;
	}
	public void setGroupHeaderId(Long groupHeaderId) {
		this.groupHeaderId = groupHeaderId;
	}
	public String getGroupHeader() {
		return groupHeader;
	}
	public void setGroupHeader(String groupHeader) {
		this.groupHeader = groupHeader;
	}
	public String getGroupHeaderImg() {
		return groupHeaderImg;
	}
	public void setGroupHeaderImg(String groupHeaderImg) {
		this.groupHeaderImg = groupHeaderImg;
	}
	public Integer getDiffUserCount() {
		return diffUserCount;
	}
	public void setDiffUserCount(Integer diffUserCount) {
		this.diffUserCount = diffUserCount;
	}
	public Boolean getIsGrouped() {
		return isGrouped;
	}
	public void setIsGrouped(Boolean isGrouped) {
		this.isGrouped = isGrouped;
	}
	public Long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public List<GrouponUserResultDto> getGroupUsers() {
		return groupUsers;
	}
	public void setGroupUsers(List<GrouponUserResultDto> groupUsers) {
		this.groupUsers = groupUsers;
	}
	public Boolean getIsExpires() {
		return isExpires;
	}
	public void setIsExpires(Boolean isExpires) {
		this.isExpires = isExpires;
	}
}
