package com.dbumama.market.service.api.activity;

public class ActivityServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ActivityServiceException (String msg){
		super(msg);
	}

	public ActivityServiceException (String code, String msg){
		super(code, new Throwable(msg));
	}
	
}
