package com.weixin.sdk.pay;

import org.dom4j.Element;

/**
 * 提交退款申请后，通过调用该接口查询退款状态。
 * 退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
 * wjun_java@163.com
 * 2015年12月9日
 */
public class RefundqueryApi extends PayApi{
	
	private static final String url = "https://api.mch.weixin.qq.com/pay/refundquery";

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
		RefundqueryResData refundqueryResData = new RefundqueryResData();
		return refundqueryResData;
	}

}
