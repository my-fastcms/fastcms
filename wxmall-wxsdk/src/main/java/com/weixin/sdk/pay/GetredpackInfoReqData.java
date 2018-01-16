package com.weixin.sdk.pay;

/**
 * 用于商户对已发放的红包进行查询红包的具体信息，可支持普通红包和裂变包。
 * wjun_java@163.com
 * 2015年12月11日
 */
public class GetredpackInfoReqData extends BaseReqData{

	private static final long serialVersionUID = 1L;

	/**
	 * <xml>
<sign><![CDATA[E1EE61A91C8E90F299DE6AE075D60A2D]]></sign>
<mch_billno><![CDATA[0010010404201411170000046545]]></mch_billno>
<mch_id><![CDATA[10000097]]></mch_id>
<appid><![CDATA[wxe062425f740c30d8]]></appid>
<bill_type><![CDATA[MCHT]]></ bill_type> 
<nonce_str><![CDATA[50780e0cca98c8c8e814883e5caa672e]]></nonce_str>
</xml>
	 */
	
	private String mch_billno;
	private String bill_type;
	public String getMch_billno() {
		return mch_billno;
	}
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}
	public String getBill_type() {
		return bill_type;
	}
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	
}
