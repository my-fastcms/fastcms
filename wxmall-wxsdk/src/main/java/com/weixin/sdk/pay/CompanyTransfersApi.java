package com.weixin.sdk.pay;

import java.util.UUID;

import org.dom4j.Element;

import com.weixin.sdk.kit.WxSdkPropKit;

/**
 * 企业付款业务是基于微信支付商户平台的资金管理能力，为了协助商户方便地实现企业向个人付款，
 * 针对部分有开发能力的商户，提供通过API完成企业付款的功能。 
比如目前的保险行业向客户退保、给付、理赔。
企业付款将使用商户的可用余额，需确保可用余额充足。
查看可用余额、充值、提现请登录商户平台“资金管理”进行操作。https://pay.weixin.qq.com/ 
注意：与商户微信支付收款资金并非同一账户，需要单独充值。 
 * wjun_java@163.com
 * 2015年12月18日
 */
public class CompanyTransfersApi extends PaycertApi{
	
	static final String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

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
		CompanyTransfersResData companyTransfersResData = new CompanyTransfersResData();
		companyTransfersResData.setReturn_code(root.elementText("return_code"));
		companyTransfersResData.setReturn_msg(root.elementText("return_msg"));
		companyTransfersResData.setResult_code(root.elementText("result_code"));
		companyTransfersResData.setErr_code(root.elementText("err_code"));
		companyTransfersResData.setErr_code_des(root.elementText("err_code_des"));
		companyTransfersResData.setPartner_trade_no(root.elementText("partner_trade_no"));
		companyTransfersResData.setPayment_no(root.elementText("payment_no"));
		companyTransfersResData.setPayment_time(root.elementText("payment_time"));
		return companyTransfersResData;
	}
	
	public static void main(String[] args) {
		CompanyTransfersReqData reqData = new CompanyTransfersReqData(
				/*Constants.appID, 
				Constants.mchID,*/
				WxSdkPropKit.get("wx_app_id"),
				WxSdkPropKit.get("wx_mch_id"),
				UUID.randomUUID().toString(), 
				"oQ774wnoZjqJt4UdAXusjT9WBvgI", 
				"NO_CHECK", 
				"王军", 
				"100", 
				"提现", 
				"127.0.0.1");
		CompanyTransfersApi transfersApi = new CompanyTransfersApi();
		try {
			CompanyTransfersResData resData = (CompanyTransfersResData) transfersApi.post(reqData);
			if("SUCCESS".equals(resData.getResult_code())){
				System.out.println(resData.getPayment_no() + "time:" + resData.getPayment_time());
			}else{
				System.out.println(resData.getErr_code_des() + "returnMsg:" + resData.getReturn_msg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
