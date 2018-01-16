package com.dbumama.market.service.api.customer;

import com.dbumama.market.service.api.exception.MarketBaseException;

@SuppressWarnings("serial")
public class CustomerException extends MarketBaseException{

	public CustomerException(String message) {
		super(message);
	}

}
