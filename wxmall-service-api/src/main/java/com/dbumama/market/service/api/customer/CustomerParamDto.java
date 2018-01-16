package com.dbumama.market.service.api.customer;

import com.dbumama.market.service.api.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class CustomerParamDto extends AbstractPageParamDto{

	/**
	 * @param pageNo
	 */
	public CustomerParamDto(Long sellerId, Integer pageNo) {
		super(sellerId, pageNo);
	}

	private String nickName;
	private Integer active;
	private Integer sceneId;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getSceneId() {
		return sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}
	

}
