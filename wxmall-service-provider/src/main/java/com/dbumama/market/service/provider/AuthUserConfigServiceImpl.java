package com.dbumama.market.service.provider;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbumama.market.message.MessageKit;
import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.authuser.AuthUserConfigService;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.utils.FileUtils;
import com.jfinal.plugin.ehcache.CacheKit;

@Service("authUserConfigService")
public class AuthUserConfigServiceImpl extends AbstractServiceImpl implements AuthUserConfigService{

	private static final String USED_AUTH_CACHENAME = "used_auth_user_cache";
	private static final String USED_AUTH_CACHENAME_KEY = "used_key";
	
	private static final AuthUserConfig authConfigDao = new AuthUserConfig().dao();
	private static final SellerUser sellerUserDao = new SellerUser().dao();
	
	@Override
	@Transactional(rollbackFor = MarketBaseException.class)
	public void save(AuthUserConfig authConfig, File file, Long sellerId) throws MarketBaseException {

//		if(!JFinal.me().getConstants().getDevMode()) throw new MarketBaseException("体验环境不可更改配置");
		
		if(authConfig == null || sellerId == null)
			throw new MarketBaseException("更新公众号信息缺少必要参数");

		SellerUser user = sellerUserDao.findById(sellerId);
		if(user == null || !"admin".equals(user.getPhone())) throw new MarketBaseException("你没有权限执行该操作");
		
		if(file != null){
			try {
				authConfig.setPayCertFile(FileUtils.toByteArray(file));
			} catch (IOException e) {
				throw new MarketBaseException(e.getMessage());
			}
		}
		
		authConfig.setSellerId(sellerId);
		if(authConfig.getId() == null){
			authConfig.save();
		}else{
			authConfig.update();
		}
		
		//删除文件
		if(file != null)
			file.delete();
		
		//写入配置文件
		MessageKit.sendMessage("auth_user_config", authConfig);
		CacheKit.remove(USED_AUTH_CACHENAME, USED_AUTH_CACHENAME_KEY);
	}
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.authuser.AuthUserConfigService#getAuthUser()
	 */
	@Override
	public AuthUserConfig getAuthConfig() {
		return authConfigDao.findFirst("select * from " + AuthUserConfig.table);
	}

}
