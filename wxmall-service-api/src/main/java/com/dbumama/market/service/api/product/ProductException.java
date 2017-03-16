package com.dbumama.market.service.api.product;

@SuppressWarnings("serial")
public class ProductException extends RuntimeException{
	public ProductException (String msg){
		super(msg);
	}

	public ProductException (String code, String msg){
		super(code, new Throwable(msg));
	}
}
