package com.dbumama.market.service.provider;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.Shop;
import com.dbumama.market.service.api.shop.ShopService;
import com.dbumama.market.service.base.BaseServiceImpl;
import com.jfinal.plugin.activerecord.Model;

@Service("shopService")
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService{

	private static final Shop shopdao = new Shop().dao();
	
	@Override
	public Shop findBySeller() {
		return shopdao.findFirst(" select * from " + Shop.table);
	}

	@Override
	public Shop save(Shop shop) {
		if(shop.getId() == null){
			shop.save();	
		}else{
			shop.update();
		}
		return shop;
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.base.BaseServiceImpl#getModel()
	 */
	@Override
	protected Model<Shop> getModel() {
		return shopdao;
	}

}
