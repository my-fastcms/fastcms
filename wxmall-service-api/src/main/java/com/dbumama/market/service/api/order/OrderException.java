package com.dbumama.market.service.api.order;

@SuppressWarnings("serial")
public class OrderException extends RuntimeException{
	public OrderException (String msg){
		super(msg);
	}

	public OrderException (String code, String msg){
		super(code, new Throwable(msg));
	}
}
