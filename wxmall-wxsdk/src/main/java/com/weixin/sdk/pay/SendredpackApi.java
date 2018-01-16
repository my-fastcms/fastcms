package com.weixin.sdk.pay;

import org.dom4j.Element;

import com.weixin.sdk.kit.WxSdkPropKit;
import com.weixin.sdk.utils.DateTimeUtil;

/**
 * wjun_java@163.com
 * 2015年12月10日
 */
public class SendredpackApi extends PaycertApi{

	private static final String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	
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
		SendredpackResData sendredpackResData = new SendredpackResData();
		sendredpackResData.setReturn_code(root.elementText("return_code"));
		sendredpackResData.setReturn_msg(root.elementText("return_msg"));
		sendredpackResData.setResult_code(root.elementText("result_code"));
		sendredpackResData.setErr_code(root.elementText("err_code"));
		sendredpackResData.setErr_code_des(root.elementText("err_code_des"));
		
		sendredpackResData.setTotal_amount(root.elementText("total_amount"));
		sendredpackResData.setRe_openid(root.elementText("re_openid"));
		sendredpackResData.setSend_listid(root.elementText("send_listid"));
		return sendredpackResData;
	}
	
	public static void main(String[] args) {
		SendredpackReqData sendredpackReqData = new SendredpackReqData(WxSdkPropKit.get("wx_mch_id"),
				WxSdkPropKit.get("wx_mch_id") + 
				DateTimeUtil.getDateTime8String() +
				String.valueOf(System.currentTimeMillis()).substring(3, 13), 
				"", WxSdkPropKit.get("wx_app_id"), "", 
				"点步科技", "oQ774wnoZjqJt4UdAXusjT9WBvgI", "360", "1", "欢迎关注点步商城", 
				"127.0.0.1", "关注点步商城送红包", "广州点步信息科技有限公司", "");
		
		SendredpackApi sendredpackApi = new SendredpackApi();
		try {
			SendredpackResData sendredpackResData = (SendredpackResData) sendredpackApi.post(sendredpackReqData);
			if("SUCCESS".equals(sendredpackResData.getResult_code())){
				System.out.println("totalMoney:" + sendredpackResData.getTotal_amount() + ",listid:" + sendredpackResData.getSend_listid());
			}else{
				System.out.println("error:" + sendredpackResData.getErr_code_des());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
