/**
 * 文件名:CloseorderResData.java
 * 版本信息:1.0
 * 日期:2015-11-5
 * 广州点步信息科技版权所有
 */
package com.weixin.sdk.pay;

/**
 * @author: wjun_java@163.com
 * @date:2015-11-5
 */
public class CloseorderResData extends BaseResData{

	/**
	 * <xml>
		   <return_code><![CDATA[SUCCESS]]></return_code>
		   <return_msg><![CDATA[OK]]></return_msg>
		   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
		   <mch_id><![CDATA[10000100]]></mch_id>
		   <nonce_str><![CDATA[BFK89FC6rxKCOjLX]]></nonce_str>
		   <sign><![CDATA[72B321D92A7BFA0B2509F3D13C7B1631]]></sign>
		   <result_code><![CDATA[SUCCESS]]></result_code>
		</xml>
	 */
	
	private String appid;
	private String mch_id;
	private String nonce_str;
	private String sign;
	private String result_code;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	
}
