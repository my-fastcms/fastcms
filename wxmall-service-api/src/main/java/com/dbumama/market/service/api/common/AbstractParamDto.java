package com.dbumama.market.service.api.common;

import java.io.Serializable;

/**
 * 服务调用传输基类
 * wjun_java@163.com
 * 2016年7月6日
 */
@SuppressWarnings("serial")
public abstract class AbstractParamDto implements Serializable{
	
	protected Long sellerId;//当前登陆用户

	/**
	 * @return the sellerId
	 */
	public Long getSellerId() {
		return sellerId;
	}
	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
}
