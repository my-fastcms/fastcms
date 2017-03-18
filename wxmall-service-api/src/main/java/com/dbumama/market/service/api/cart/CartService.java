package com.dbumama.market.service.api.cart;

import java.util.List;

import com.dbumama.market.service.api.exception.MarketBaseException;

/**
 * wjun_java@163.com
 * 2016年5月15日
 */
public interface CartService {

	public void add(Long buyerId, Long productId, int quantity, String speci) throws MarketBaseException;
	
	public List<CartItemResultDto> getCartsByBuyer(Long buyerId) throws MarketBaseException;

	public Long getCartItemCountByBuyer(Long buyerId) throws MarketBaseException;
	
}
