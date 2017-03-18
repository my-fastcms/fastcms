package com.dbumama.market.web.mobile.cart.controller;

import java.util.List;

import com.dbumama.market.model.Cart;
import com.dbumama.market.service.api.cart.CartItemResultDto;
import com.dbumama.market.service.api.cart.CartService;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;

@RouteBind(path = "cart")
public class CartController extends BaseMobileController{

	@BY_NAME
	CartService cartService;
	
	public void index() {
		List<CartItemResultDto> cartItems = cartService.getCartsByBuyer(getBuyerId());
    	setAttr("items", cartItems);
        render("/cart/index.html");
    }
	
	public void add(){
		Long productId = getParaToLong("productId");
        int quantity = getParaToInt("quantity", 1);
        String speci = getPara("speci");//规格值
        
        try {
			cartService.add(getBuyerId(), productId, quantity, speci);
			rendSuccessJson();
		} catch (MarketBaseException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void delete() {
        String productIds = getPara("ids");
        if(StrKit.isBlank(productIds)){
        	rendFailedJson("请选择要删除的项");
        	return;
        }
        for(String id : productIds.split("#")){
        	Cart citem = Cart.dao.findById(id);
        	if(citem != null){
        		citem.delete();
        	}
        }
        rendSuccessJson();
    }
	
}
