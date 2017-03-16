package com.dbumama.market.service.api.authuser;

import com.dbumama.market.model.AuthUser;
import com.dbumama.market.service.api.exception.MarketBaseException;

/**
 * 授权公众号服务类
 * @author wangjun
 *
 */
public interface AuthUserService {

	/**
	 * 获取用户当前正在使用的公众账号
	 * @param sellerId
	 * @return
	 * @throws MarketBaseException
	 */
	AuthUser getUsedAuthUser(Long sellerId) throws MarketBaseException; 
	
}
