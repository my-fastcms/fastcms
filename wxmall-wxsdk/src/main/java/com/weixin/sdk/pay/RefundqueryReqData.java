package com.weixin.sdk.pay;

/**
 * wjun_java@163.com
 * 2015年12月9日
 */
public class RefundqueryReqData extends BaseReqData{

	private static final long serialVersionUID = 1L;
	/**
	 * <xml>
   <appid>wx2421b1c4370ec43b</appid>
   <mch_id>10000100</mch_id>
   <nonce_str>0b9f35f484df17a732e537c37708d1d0</nonce_str>
   <out_refund_no></out_refund_no>
   <out_trade_no>1415757673</out_trade_no>
   <refund_id></refund_id>
   <transaction_id></transaction_id>
   <sign>66FFB727015F450D167EF38CCC549521</sign>
</xml>
	 */
	
	private String out_refund_no;
	private String out_trade_no;
	private String refund_id;
	private String transaction_id;
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
}
