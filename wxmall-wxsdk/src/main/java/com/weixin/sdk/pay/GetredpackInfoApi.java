package com.weixin.sdk.pay;

import org.dom4j.Element;

/**
 * wjun_java@163.com
 * 2015年12月11日
 */
public class GetredpackInfoApi extends PaycertApi{
	
	private static final String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";

	/* (non-Javadoc)
	 * @see com.weixin.sdk.pay.PayApi#getApiUrl()
	 */
	@Override
	protected String getApiUrl() {
		return url;
	}

	/* (non-Javadoc)
	 * @see com.weixin.sdk.pay.PayApi#getRespone(org.dom4j.Element)
	 */
	@Override
	protected BaseResData getRespone(Element root) {
		GetredpackInfoResData getredpackInfoResData = new GetredpackInfoResData();
		return getredpackInfoResData;
	}

}
