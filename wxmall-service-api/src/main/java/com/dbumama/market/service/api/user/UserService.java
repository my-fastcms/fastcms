package com.dbumama.market.service.api.user;

import com.dbumama.market.model.BuyerUser;

/**
 * 
 * @author wangjun
 *
 */
public interface UserService {
	
	/**
	 * 小程序登陆微信服务器，获取用户详细信息，包括登陆用户的openid
	 * @param appId
	 * @param code
	 * @return
	 * @throws UserException
	 */
	public WeappLoginResultDto loginWeapp (String appId, String code) throws UserException;
	
	/**
	 * 小程序用户校验与保存
	 * @param userCheckParam
	 * @throws UserException
	 */
	public BuyerUser check(WeappUserCheckParamDto userCheckParam) throws UserException;
	
	BuyerUser findById(Long userId);
	
	BuyerUser findByOpenId(String openId);
	
	/**
	 * 
	 * @param openId
	 * @param appId
	 * @param flag	关注事件标志
	 * @return
	 * @throws UserException
	 */
	BuyerUser saveOrUpdate(Long sellerId, String openId, String userInfoJson, String accessIp) throws UserException;
	
	/**
	 * 用户取消关注公众号
	 * @param openId
	 * @return
	 */
	BuyerUser cancelSubscribe(String openId);
}
