package com.dbumama.market.service.api.shop;

import com.dbumama.market.model.Shop;
import com.dbumama.market.service.api.base.BaseService;

public interface ShopService extends BaseService{
	Shop findBySeller();
	Shop save(Shop shop);
}
