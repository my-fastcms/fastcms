package com.weixin.sdk.pay;

/**
 * wjun_java@163.com
 * 2015年12月10日
 */
public class SendredpackResData extends BaseResData{
/**
 * <xml>
<return_code><![CDATA[SUCCESS]]></return_code>
<return_msg><![CDATA[发放成功.]]></return_msg>
<result_code><![CDATA[SUCCESS]]></result_code>
<err_code><![CDATA[0]]></err_code>
<err_code_des><![CDATA[发放成功.]]></err_code_des>
<mch_billno><![CDATA[0010010404201411170000046545]]></mch_billno>
<mch_id>10010404</mch_id>
<wxappid><![CDATA[wx6fa7e3bab7e15415]]></wxappid>
<re_openid><![CDATA[onqOjjmM1tad-3ROpncN-yUfa6uI]]></re_openid>
<total_amount>1</total_amount>
<send_listid>100000000020150520314766074200</send_listid>
<send_time>20150520102602</send_time>
</xml>
 */
	
	private String mch_billno;
	private String mch_id;
	private String wxappid;
	private String re_openid;
	private String total_amount;
	private String send_listid;
	private String send_time;
	
	public String getMch_billno() {
		return mch_billno;
	}
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getWxappid() {
		return wxappid;
	}
	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}
	public String getRe_openid() {
		return re_openid;
	}
	public void setRe_openid(String re_openid) {
		this.re_openid = re_openid;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getSend_listid() {
		return send_listid;
	}
	public void setSend_listid(String send_listid) {
		this.send_listid = send_listid;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	
}
