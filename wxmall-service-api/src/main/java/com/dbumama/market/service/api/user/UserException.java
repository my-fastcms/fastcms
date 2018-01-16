package com.dbumama.market.service.api.user;

import com.dbumama.market.service.api.exception.MarketBaseException;

/**
 * wjun_java@163.com
 * 2016年7月8日
 */
@SuppressWarnings("serial")
public class UserException extends MarketBaseException{

	/**
	 * @param message
	 */
	public UserException(String message) {
		super(message);
	}
	
}
