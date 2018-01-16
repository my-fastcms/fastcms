/**
 * 文件名:OrderqueryReqData.java
 * 版本信息:1.0
 * 日期:2015-11-5
 * 广州点步信息科技版权所有
 */
package com.weixin.sdk.pay;

import com.weixin.sdk.utils.SignKit;

/**
 * @author: wjun_java@163.com
 * @date:2015-11-5
 */
public class OrderqueryReqData extends BaseReqData{

	private static final long serialVersionUID = 1L;
	/**
	 * <xml>
		   <appid>wx2421b1c4370ec43b</appid>
		   <mch_id>10000100</mch_id>
		   <nonce_str>ec2316275641faa3aacf3cc599e8730f</nonce_str>
		   <transaction_id>1008450740201411110005820873</transaction_id>
		   <sign>FDD167FAA73459FD921B144BAF4F4CA2</sign>
		</xml>
	 */
	
	private String transaction_id;
	private String out_trade_no;
	
	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @param nonce_str
	 * @param transaction_id
	 */
	public OrderqueryReqData(String nonce_str, String transaction_id, String out_trade_no) {
		this.transaction_id = transaction_id;
		this.out_trade_no = out_trade_no;
		setSign(SignKit.sign(toMap()));
	}
	
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
}
