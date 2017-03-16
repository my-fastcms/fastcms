package com.dbumama.market.service.api.agent;

import com.dbumama.market.service.api.exception.MarketBaseException;

@SuppressWarnings("serial")
public class AgentException extends MarketBaseException{

	public AgentException(String message) {
		super(message);
	}

}
