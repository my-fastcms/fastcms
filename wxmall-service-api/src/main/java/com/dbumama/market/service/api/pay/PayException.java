/**
 * 文件名:PayException.java
 * 版本信息:1.0
 * 日期:2015-11-2
 * 广州点步信息科技版权所有
 */
package com.dbumama.market.service.api.pay;

/**
 * @author: wjun_java@163.com
 * @date:2015-11-2
 */
public class PayException extends RuntimeException{

	/**
	 * @param message
	 */
	public PayException(String message) {
		super(message);
	}

	/**
	 * @author: wjun_java@163.com
	 * @date: 2015-11-2
	 */
	private static final long serialVersionUID = 1L;

}
