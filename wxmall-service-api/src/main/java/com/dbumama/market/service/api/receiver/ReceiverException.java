package com.dbumama.market.service.api.receiver;

import com.dbumama.market.service.api.exception.MarketBaseException;

/**
 * wjun_java@163.com
 * 2016年7月8日
 */
@SuppressWarnings("serial")
public class ReceiverException extends MarketBaseException{

	/**
	 * @param message
	 */
	public ReceiverException(String message) {
		super(message);
	}
	
}
