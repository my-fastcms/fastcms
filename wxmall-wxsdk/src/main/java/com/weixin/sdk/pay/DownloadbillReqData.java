package com.weixin.sdk.pay;

/**
 * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
注意：
1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
3、对账单中涉及金额的字段单位为“元”。
 * wjun_java@163.com
 * 2015年12月9日
 */
public class DownloadbillReqData extends BaseReqData{

	private static final long serialVersionUID = 1L;
	/**
	 * <xml>
	  <appid>wx2421b1c4370ec43b</appid>
	  <bill_date>20141110</bill_date>
	  <bill_type>ALL</bill_type>
	  <mch_id>10000100</mch_id>
	  <nonce_str>21df7dc9cd8616b56919f20d9f679233</nonce_str>
	  <sign>332F17B766FC787203EBE9D6E40457A1</sign>
	</xml>
	 */
	
	private String bill_date;
	private String bill_type;
	
	public String getBill_date() {
		return bill_date;
	}
	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
	}
	public String getBill_type() {
		return bill_type;
	}
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	
}
