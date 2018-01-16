package com.dbumama.market.service.api.user;

import com.dbumama.market.model.SellerUser;

public interface SellerUserService {

	SellerUser findByPhone(String phone);
	SellerUser findById(Long sellerId);
}
