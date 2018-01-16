package com.dbumama.market.service.api.customer;

import java.util.Date;

import com.dbumama.market.service.api.common.AbstractResultDto;

/**
 * 
 * @author wangjun
 *
 */
@SuppressWarnings("serial")
public class MemberResultDto extends AbstractResultDto{

	private Long id;
	private String nickName;
	private String headImg;
	private Integer sex;
	private Date lastLoginTime;
	private Integer active;
	private String province;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
}
