package com.weixin.sdk.pay;

import com.weixin.sdk.utils.SignKit;

/**
 * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，
 * 微信支付将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
注意：
1、交易时间超过一年的订单无法提交退款；
2、微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额。
 * wjun_java@163.com
 * 2015年12月9日
 */
public class RefundReqData extends BaseReqData{

	private static final long serialVersionUID = 1L;
	//请求需要双向证书。 详见证书使用
	/**
	 * <xml>
		   <appid>wx2421b1c4370ec43b</appid>
		   <mch_id>10000100</mch_id>
		   <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
		   <op_user_id>10000100</op_user_id>
		   <out_refund_no>1415701182</out_refund_no>
		   <out_trade_no>1415757673</out_trade_no>
		   <refund_fee>1</refund_fee>
		   <total_fee>1</total_fee>
		   <transaction_id></transaction_id>
		   <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
		</xml>
	 */
	
	private String op_user_id;
	private String out_refund_no;
	private String out_trade_no;
	private String refund_fee;
	private String total_fee;
	private String transaction_id;
	
	/**
	 * @param out_refund_no
	 * @param out_trade_no
	 * @param refund_fee
	 * @param total_fee
	 * @param transaction_id
	 */
	public RefundReqData(String out_refund_no, String out_trade_no, String refund_fee,
			String total_fee, String transaction_id) {
		super();
		this.op_user_id = getMch_id();
		this.out_refund_no = out_refund_no;
		this.out_trade_no = out_trade_no;
		this.refund_fee = refund_fee;
		this.total_fee = total_fee;
		this.transaction_id = transaction_id;
		setSign(SignKit.sign(toMap(), this.getMch_sec_key()));
	}
	
	public String getOp_user_id() {
		return op_user_id;
	}
	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}
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
	public String getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
	
}
