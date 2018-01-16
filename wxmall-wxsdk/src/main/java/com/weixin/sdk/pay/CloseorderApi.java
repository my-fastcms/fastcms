/**
 * 文件名:CloseorderApi.java
 * 版本信息:1.0
 * 日期:2015-11-5
 * 广州点步信息科技版权所有
 */
package com.weixin.sdk.pay;

import org.dom4j.Element;

/**
 * @author: wjun_java@163.com
 * @date:2015-11-5
 */
public class CloseorderApi extends PayApi{

	static final String url = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	@Override
	protected String getApiUrl() {
		return url;
	}

	@Override
	protected BaseResData getRespone(Element root) {
		CloseorderResData resData = new CloseorderResData();
		resData.setAppid(root.elementText("appid"));
		resData.setMch_id(root.elementText("mch_id"));
		return resData;
	}

}
