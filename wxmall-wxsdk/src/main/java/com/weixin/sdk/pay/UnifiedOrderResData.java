/**
 * 文件名:UnifiedOrderResData.java
 * 版本信息:1.0
 * 日期:2015-11-4
 * 广州点步信息科技版权所有
 */
package com.weixin.sdk.pay;

/**
 * <xml><return_code><![CDATA[SUCCESS]]></return_code>
	<return_msg><![CDATA[OK]]></return_msg>
	<appid><![CDATA[wx0dd16298bc16ed63]]></appid>
	<mch_id><![CDATA[1281049301]]></mch_id>
	<device_info><![CDATA[web]]></device_info>
	<nonce_str><![CDATA[as4HXeDME5uLD6OU]]></nonce_str>
	<sign><![CDATA[B274911E8BA107FC224AA4F25E1563DB]]></sign>
	<result_code><![CDATA[SUCCESS]]></result_code>
	<prepay_id><![CDATA[wx2015110421261732289d15e30625046746]]></prepay_id>
	<trade_type><![CDATA[JSAPI]]></trade_type>
	</xml>
 * @author: wjun_java@163.com
 * @date:2015-11-4
 */
public class UnifiedOrderResData extends BaseResData{

	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String result_code;
	private String prepay_id;
	private String trade_type;
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
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
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
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	
}
