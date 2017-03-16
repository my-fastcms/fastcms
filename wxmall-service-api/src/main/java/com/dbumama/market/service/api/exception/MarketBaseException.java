package com.dbumama.market.service.api.exception;

import com.jfinal.plugin.activerecord.ActiveRecordException;

/**
 * wjun_java@163.com
 * 2016年7月4日
 */
@SuppressWarnings("serial")
public class MarketBaseException extends ActiveRecordException{

	/**
	 * @param message
	 */
	public MarketBaseException(String message) {
		super(message);
	}
	
}
