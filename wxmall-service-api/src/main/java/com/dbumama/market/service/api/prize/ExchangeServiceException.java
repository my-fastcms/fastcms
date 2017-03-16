package com.dbumama.market.service.api.prize;

public class ExchangeServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExchangeServiceException (String msg){
		super(msg);
	}

	public ExchangeServiceException (String code, String msg){
		super(code, new Throwable(msg));
	}

}
