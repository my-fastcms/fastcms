/**
 * 文件名:CloseorderReqData.java
 * 版本信息:1.0
 * 日期:2015-11-5
 * 广州点步信息科技版权所有
 */
package com.weixin.sdk.pay;

/**
 * @author: wjun_java@163.com
 * @date:2015-11-5
 */
public class CloseorderReqData extends BaseReqData{

	private static final long serialVersionUID = 1L;
	/**
	 * <xml>
		   <appid>wx2421b1c4370ec43b</appid>
		   <mch_id>10000100</mch_id>
		   <nonce_str>4ca93f17ddf3443ceabf72f26d64fe0e</nonce_str>
		   <out_trade_no>1415983244</out_trade_no>
		   <sign>59FF1DF214B2D279A0EA7077C54DD95D</sign>
		</xml>
	 */
	
	private String out_trade_no;
	
	/**
	 * @param out_trade_no
	 */
	public CloseorderReqData(String out_trade_no) {
		super();
		this.out_trade_no = out_trade_no;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
}
