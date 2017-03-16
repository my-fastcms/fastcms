/**
 * 文件名:QiandaoResultVo.java
 * 版本信息:1.0
 * 日期:2015-5-25
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.service.api.qiandao;

import com.dbumama.market.service.common.AbstractResultDto;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-25
 */
@SuppressWarnings("serial")
public class QiandaoSubmitResultDto extends AbstractResultDto {
	
	private Boolean hasPrize;		//是否有奖品

	private String msg;

	public QiandaoSubmitResultDto(){
		setHasPrize(false);
	}
	
	public Boolean getHasPrize() {
		return hasPrize;
	}

	public void setHasPrize(Boolean hasPrize) {
		this.hasPrize = hasPrize;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
