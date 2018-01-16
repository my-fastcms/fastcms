package com.weixin.sdk.pay;

import org.dom4j.Element;

/**
 * 微信支付  统一下单接口
 * wjun_java@163.com
 * 2015年11月3日
 */
public class UnifiedOrderApi extends PayApi{

	static final String unified_order_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	@Override
	protected String getApiUrl() {
		return unified_order_url;
	}
	
	@Override
	protected BaseResData getRespone(Element root) {
		UnifiedOrderResData unifiedOrderResData = new UnifiedOrderResData();
		unifiedOrderResData.setReturn_code(root.elementText("return_code"));
		unifiedOrderResData.setReturn_msg(root.elementText("return_msg"));
		unifiedOrderResData.setResult_code(root.elementText("result_code"));
		unifiedOrderResData.setErr_code(root.elementText("err_code"));
		unifiedOrderResData.setErr_code_des(root.elementText("err_code_des"));
		
		unifiedOrderResData.setAppid(root.elementText("appid"));
		unifiedOrderResData.setPrepay_id(root.elementText("prepay_id"));
		return unifiedOrderResData;
	}
	
	public static void main(String[] args) {
		/**
		//调用统一下单接口
		UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData("oQ774wnoZjqJt4UdAXusjT9WBvgI", "test", 
				"32232", "5", "127.0.0.1", "http://m.dbumama.com/pay/result", "JSAPI");
		UnifiedOrderApi unifiedOrderApi = new UnifiedOrderApi();
		UnifiedOrderResData unifiedOrderResData = null;;
		try {
			unifiedOrderResData = (UnifiedOrderResData) unifiedOrderApi.post(unifiedOrderReqData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(unifiedOrderResData.getPrepay_id() + "," + unifiedOrderResData.getReturn_code() + "," + unifiedOrderResData.getReturn_msg());
	  **/
		
		//NATIVE 下单接口
		UnifiedOrderAppReqData unifiedOrderAppReqData = new UnifiedOrderAppReqData( "testOrder", 
				"transnNO32232", "5", "127.0.0.1", "APP");
		UnifiedOrderApi unifiedOrderApi = new UnifiedOrderApi();
		UnifiedOrderResData unifiedOrderResData = null;;
		try {
			unifiedOrderResData = (UnifiedOrderResData) unifiedOrderApi.post(unifiedOrderAppReqData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(unifiedOrderResData.getPrepay_id() + "," + unifiedOrderResData.getReturn_code() + "," + unifiedOrderResData.getReturn_msg());

		
	}

}
