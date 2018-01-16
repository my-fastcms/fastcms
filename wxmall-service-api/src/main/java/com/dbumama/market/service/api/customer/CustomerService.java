package com.dbumama.market.service.api.customer;

import com.dbumama.market.model.BuyerUser;
import com.jfinal.plugin.activerecord.Page;

/**
 * 客户，会员
 * @author wangjun
 *
 */
public interface CustomerService {

	/**
	 * 查询获取所有客户，包括会员
	 * @param customerParam
	 * @return
	 * @throws CustomerException
	 */
	public Page<BuyerUser> list(CustomerParamDto customerParam) throws CustomerException;
	
	/**
	 * 只查询出会员数据，领取过微信会员卡的用户列表，并且会员卡未过期
	 * @return
	 * @throws CustomerException
	 */
	public Page<MemberResultDto> listMembers(CustomerParamDto customerParam) throws CustomerException;
	
}
