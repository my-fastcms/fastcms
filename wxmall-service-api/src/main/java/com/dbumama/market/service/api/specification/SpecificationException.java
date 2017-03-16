package com.dbumama.market.service.api.specification;

import com.dbumama.market.service.api.exception.MarketBaseException;

@SuppressWarnings("serial")
public class SpecificationException extends MarketBaseException{

	public SpecificationException(String message) {
		super(message);
	}

}
