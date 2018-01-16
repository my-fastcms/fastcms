package com.dbumama.market.service.base;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.weixin.sdk.kit.WxSdkPropKit;

/**
 * 公共服务类
 * @author wangjun
 *
 */
public abstract class AbstractServiceImpl {
	
	protected Logger logger = Logger.getLogger(getClass());

	protected synchronized String getTradeNo(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	protected String getImageDomain(){
		return WxSdkPropKit.get("img_domain");
	}
	
}
