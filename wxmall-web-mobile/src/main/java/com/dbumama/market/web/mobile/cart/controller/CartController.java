package com.dbumama.market.web.mobile.cart.controller;

import java.util.List;

import com.dbumama.market.model.Cart;
import com.dbumama.market.service.api.cart.CartItemResultDto;
import com.dbumama.market.service.api.cart.CartService;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.ump.ProdFullCutResultDto;
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
		List<ProdFullCutResultDto> fullCutDtos = cartService.getCartFullCat(cartItems);
    	setAttr("items", cartItems);
    	setAttr("fullCuts", fullCutDtos);
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
        String cartIds = getPara("ids");
        if(StrKit.isBlank(cartIds)){
        	rendFailedJson("请选择要删除的项");
        	return;
        }
        for(String id : cartIds.split("#")){
        	Cart citem = cartService.findById(Long.valueOf(id));
        	if(citem != null){
        		citem.delete();
        	}
        }
        rendSuccessJson();
    }
	
}
