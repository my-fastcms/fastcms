package com.dbumama.market.service.api.cart;

import java.util.List;

import com.dbumama.market.model.Cart;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.ump.ProdFullCutResultDto;

/**
 * wjun_java@163.com
 * 2016年5月15日
 */
public interface CartService {

	public void add(Long buyerId, Long productId, int quantity, String speci) throws MarketBaseException;
	
	public List<CartItemResultDto> getCartsByBuyer(Long buyerId) throws MarketBaseException;
	
	public Long getCartItemCountByBuyer(Long buyerId) throws MarketBaseException;
	
	/**
	 * 获取购物车中的商品的满减数据，并按从小到大的顺序进行排序
	 * @param buyerId
	 * @return
	 * @throws MarketBaseException
	 */
	public List<ProdFullCutResultDto> getCartFullCat(List<CartItemResultDto> cartItemParamDto);
	
	Cart findById(Long cartId);
	
}
