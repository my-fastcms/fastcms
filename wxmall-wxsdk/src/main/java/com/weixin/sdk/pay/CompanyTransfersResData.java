package com.weixin.sdk.pay;

/**
 * wjun_java@163.com
 * 2015年12月18日
 */
public class CompanyTransfersResData extends BaseResData{
/**
 * <xml> 
<return_code><![CDATA[SUCCESS]]></return_code> 
<return_msg><![CDATA[]]></return_msg> 
<mch_appid><![CDATA[wxec38b8ff840bd989]]></mch_appid> 
<mchid><![CDATA[10013274]]></mchid> 
<device_info><![CDATA[]]></device_info> 
<nonce_str><![CDATA[lxuDzMnRjpcXzxLx0q]]></nonce_str> 
<result_code><![CDATA[SUCCESS]]></result_code> 
<partner_trade_no><![CDATA[10013574201505191526582441]]></partner_trade_no> 
<payment_no><![CDATA[1000018301201505190181489473]]></payment_no> 
<payment_time><![CDATA[2015-05-19 15：26：59]]></payment_time> 
</xml>
 */
	private String partner_trade_no;
	private String payment_no;
	private String payment_time;
	public String getPartner_trade_no() {
		return partner_trade_no;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}
	public String getPayment_no() {
		return payment_no;
	}
	public void setPayment_no(String payment_no) {
		this.payment_no = payment_no;
	}
	public String getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}
	
}
