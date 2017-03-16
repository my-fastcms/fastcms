package com.dbumama.market.service.provider;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Prize;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.prize.ExchangeService;
import com.dbumama.market.service.api.prize.ExchangeServiceException;

@Service("flowcashService")
public class FlawcashServiceImpl implements ExchangeService{

	@Override
	public void doExchange(final Prize prize, final BuyerUser buyer, final SellerUser sellerUser, final Map<String, String> params) throws ExchangeServiceException {
		
	}

}
