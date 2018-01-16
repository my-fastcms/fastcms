package com.dbumama.market.service.api.order;

import com.dbumama.market.model.SellerAddr;

/**
 * 订单发货地址管理
 * @author wangjun
 *
 */
public interface SellerAddrService {

	/**
	 * 获取用户的发货地址
	 * @param sellerId
	 * @return
	 * @throws OrderException
	 */
	SellerAddr getSendAddr(Long sellerId) throws OrderException;
	
	/**
	 * 更新或保存用户的发货地址
	 * @param addrSaveParamDto
	 * @return
	 * @throws OrderException
	 */
	SellerAddr saveOrUpdate(SellerAddrSaveParamDto addrSaveParamDto) throws OrderException;
}
