package com.dbumama.market.service.constants;

import com.jfinal.kit.PropKit;

public final class Constants {

	/**
	 * 正式环境
	 */
	public static final String WUXIAN_DOMIAN = PropKit.get("wirless_domain");
	
	/** 接口域名key **/
	public static final String DOMAIN_KEY = "domain_url";
	
	//把BuyerUserVo的数据放到session中
	public static final String BUYER_USER_IN_SESSION = "_buyerUser";
	
	/** 微信公众号信息存放到session中 **/
	public static final String APPUSER_IN_SESSION = "appUser";
	
	public final static String NET_ERROR_MSG = "请求接口失败，请检查网络，或者刷新重连";
	
}
