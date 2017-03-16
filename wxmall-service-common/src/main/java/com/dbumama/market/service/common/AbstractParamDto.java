package com.dbumama.market.service.common;

import java.io.Serializable;

/**
 * 服务调用传输基类
 * wjun_java@163.com
 * 2016年7月6日
 */
@SuppressWarnings("serial")
public abstract class AbstractParamDto implements Serializable{
	protected Long sellerId;
	protected String appId;		//微信公众账号id

	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
}
