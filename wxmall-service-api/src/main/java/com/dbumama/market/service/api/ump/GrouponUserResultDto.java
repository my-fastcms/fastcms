package com.dbumama.market.service.api.ump;

import com.dbumama.market.service.api.common.AbstractResultDto;

/**
 * 拼团用户
 * @author wangjun
 *
 */
@SuppressWarnings("serial")
public class GrouponUserResultDto extends AbstractResultDto{

	private Long id;
	private String nickName;
	private String headimg;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	
}
