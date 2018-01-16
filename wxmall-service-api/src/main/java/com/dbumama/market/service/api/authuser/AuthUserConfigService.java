package com.dbumama.market.service.api.authuser;

import java.io.File;

import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.service.api.exception.MarketBaseException;

/**
 * 授权公众号服务类
 * @author wangjun
 *
 */
public interface AuthUserConfigService {

	public void save(AuthUserConfig userParam, File file, Long sellerId) throws MarketBaseException;
	
	public AuthUserConfig getAuthConfig();
}
