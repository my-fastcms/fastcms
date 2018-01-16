package com.weixin.sdk.pay;

import com.weixin.sdk.utils.SignKit;

/**
 * 用于企业向微信用户个人发现金红包
目前支持向指定微信用户的openid发放指定金额红包。
（获取openid参见微信公众平台开发者文档：网页授权获取用户基本信息）
 * wjun_java@163.com
 * 2015年12月10日
 */
public class SendredpackReqData extends BaseReqCommData{
	
	private static final long serialVersionUID = 1L;
	/**
	 * <xml>
	<sign><![CDATA[E1EE61A91C8E90F299DE6AE075D60A2D]]></sign>
	<mch_billno><![CDATA[0010010404201411170000046545]]></mch_billno>
	<mch_id><![CDATA[10000098]]></mch_id>
	<sub_mch_id><![CDATA[10000090]]></sub_mch_id>
	<wxappid><![CDATA[wxcbda96de0b165486]]></wxappid>
	<msgappid><![CDATA[wx28b16568a629bb33]]></msgappid>
	<send_name><![CDATA[send_name]]></send_name>
	<re_openid><![CDATA[onqOjjmM1tad-3ROpncN-yUfa6uI]]></re_openid>
	<total_amount><![CDATA[200]]></total_amount>
	<total_num><![CDATA[1]]></total_num>
	<wishing><![CDATA[恭喜发财]]></wishing>
	<client_ip><![CDATA[127.0.0.1]]></client_ip>
	<act_name><![CDATA[新年红包]]></act_name>
	<remark><![CDATA[新年红包]]></remark>
	<nonce_str><![CDATA[50780e0cca98c8c8e814883e5caa672e]]></nonce_str>
	</xml>
	 */
	
	private String mch_id;
	private String mch_billno;
	private String sub_mch_id;  //微信支付分配的子商户号，服务商模式下必填
	private String wxappid;		//微信分配的公众账号ID（企业号corpid即为此appId）
	private String msgappid;	//服务商模式下触达用户时的appid(可填服务商自己的appid或子商户的appid)，服务商模式下必填，服务商模式下填入的子商户appid必须在微信支付商户平台中先录入，否则会校验不过。
	private String send_name;	//红包发送者名称
	private String re_openid;	//接受红包的用户,用户在wxappid下的openid，服务商模式下可填入msgappid下的openid。
	private String total_amount;//付款金额，单位分
	private String total_num;	//红包发放总人数 total_num=1
	private String wishing;		//红包祝福语
	private String client_ip;	//调用接口的机器Ip地址
	private String act_name;	//活动名称
	private String remark;		//备注信息
	private String consume_mch_id; //常规模式下无效，服务商模式下选填，服务商模式下不填默认扣子商户的钱
	
	/**
	 * @param mch_billno
	 * @param sub_mch_id
	 * @param wxappid
	 * @param msgappid
	 * @param send_name
	 * @param re_openid
	 * @param total_amount
	 * @param total_num
	 * @param wishing
	 * @param client_ip
	 * @param act_name
	 * @param remark
	 * @param consume_mch_id
	 */
	public SendredpackReqData(String mch_id, String mch_billno, String sub_mch_id, String wxappid, String msgappid, String send_name,
			String re_openid, String total_amount, String total_num, String wishing, String client_ip, String act_name,
			String remark, String consume_mch_id) {
		this.mch_id = mch_id;
		this.mch_billno = mch_billno;
		this.sub_mch_id = sub_mch_id;
//		this.wxappid = WxSdkPropKit.get("wx_app_id");
		this.wxappid = wxappid;
		this.msgappid = msgappid;
		this.send_name = send_name;
		this.re_openid = re_openid;
		this.total_amount = total_amount;
		this.total_num = total_num;
		this.wishing = wishing;
		this.client_ip = client_ip;
		this.act_name = act_name;
		this.remark = remark;
		this.consume_mch_id = consume_mch_id;
		setSign(SignKit.sign(toMap(), this.getMch_sec_key()));
	}
	
	/**
	 * @param mchSecKey 	//商户支付密钥
	 * @param mch_billno
	 * @param sub_mch_id
	 * @param wxappid
	 * @param msgappid
	 * @param send_name
	 * @param re_openid
	 * @param total_amount
	 * @param total_num
	 * @param wishing
	 * @param client_ip
	 * @param act_name
	 * @param remark
	 * @param consume_mch_id
	 */
	public SendredpackReqData(String mch_id, String mchSecKey, String mch_billno, String sub_mch_id, String wxappid, 
			String msgappid, String send_name,
			String re_openid, String total_amount, String total_num, String wishing, String client_ip, String act_name,
			String remark, String consume_mch_id) {
		this(mch_id, mch_billno, sub_mch_id, wxappid, msgappid, send_name, 
				re_openid, total_amount, total_num, wishing, client_ip, act_name, remark, consume_mch_id);
		this.mch_sec_key = mchSecKey;
		setSign(SignKit.sign(toMap(), this.getMch_sec_key()));
	}
	
	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getMch_billno() {
		return mch_billno;
	}
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}
	public String getSub_mch_id() {
		return sub_mch_id;
	}
	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}
	public String getWxappid() {
		return wxappid;
	}
	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}
	public String getMsgappid() {
		return msgappid;
	}
	public void setMsgappid(String msgappid) {
		this.msgappid = msgappid;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
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
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	public String getWishing() {
		return wishing;
	}
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getConsume_mch_id() {
		return consume_mch_id;
	}
	public void setConsume_mch_id(String consume_mch_id) {
		this.consume_mch_id = consume_mch_id;
	}
	
}
