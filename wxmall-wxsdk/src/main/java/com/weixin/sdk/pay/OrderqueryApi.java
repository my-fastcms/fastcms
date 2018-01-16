/**
 * 文件名:OrderqueryApi.java
 * 版本信息:1.0
 * 日期:2015-11-5
 * 广州点步信息科技版权所有
 */
package com.weixin.sdk.pay;

import org.dom4j.Element;

import com.weixin.sdk.utils.SignKit;

/**
 * @author: wjun_java@163.com
 * @date:2015-11-5
 */
public class OrderqueryApi extends PayApi{

	static final String url = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	@Override
	protected String getApiUrl() {
		return url;
	}

	@Override
	protected BaseResData getRespone(Element root) {
		OrderqueryResData orderqueryResData = new OrderqueryResData();
		orderqueryResData.setReturn_code(root.elementText("return_code"));
		orderqueryResData.setReturn_msg(root.elementText("return_msg"));
		orderqueryResData.setResult_code(root.elementText("result_code"));
		orderqueryResData.setErr_code(root.elementText("err_code"));
		orderqueryResData.setErr_code_des(root.elementText("err_code_des"));
		orderqueryResData.setAppid(root.elementText("appid"));
		orderqueryResData.setTotal_fee(root.elementText("total_fee"));
		return orderqueryResData;
	}
	
	public static void main(String[] args) {
		OrderqueryReqData reqData = new OrderqueryReqData(SignKit.genRandomString32(), 
				"1002690406201511041446125869", "");
		
		OrderqueryApi orderqueryApi = new OrderqueryApi();
		try {
			OrderqueryResData resData = (OrderqueryResData) orderqueryApi.post(reqData);
			if("SUCCESS".equals(resData.getResult_code())){
				System.out.println(resData.getTotal_fee());
			}else{
				System.out.println("error:" + resData.getErr_code_des() + ",errormsg:" + resData.getReturn_msg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
