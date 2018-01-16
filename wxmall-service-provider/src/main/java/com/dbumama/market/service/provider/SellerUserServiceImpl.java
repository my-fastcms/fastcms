package com.dbumama.market.service.provider;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.user.SellerUserService;

@Service("sellerUserService")
public class SellerUserServiceImpl implements SellerUserService{

	private static final SellerUser sellerUserDao = new SellerUser().dao();
	
	@Override
	public SellerUser findByPhone(String phone) {
		return sellerUserDao.findFirst("select * from t_seller_user where phone = ?", phone);
	}

	@Override
	public SellerUser findById(Long sellerId) {
		return sellerUserDao.findById(sellerId);
	}

}
