package com.dbumama.market.service.api.prize;

import java.util.Map;

import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Prize;
import com.dbumama.market.model.SellerUser;

public interface ExchangeService {

	public void doExchange(final Prize prize, final BuyerUser buyerUser, final SellerUser sellerUser, final Map<String, String> params) throws ExchangeServiceException;
}
