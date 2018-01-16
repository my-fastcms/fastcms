package com.weixin.sdk.pay;

import org.dom4j.Element;

/**
 * wjun_java@163.com
 * 2015年12月9日
 */
public class DownloadbillApi extends PayApi{

	private static final String url = "https://api.mch.weixin.qq.com/pay/downloadbill";
	
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
		DownloadbillResData downloadbillResData = new DownloadbillResData();
		return downloadbillResData;
	}

}
