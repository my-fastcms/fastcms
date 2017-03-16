package com.weixin.sdk.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.weixin.sdk.kit.PaymentKit;
import com.weixin.sdk.utils.HttpUtils;

/**
 * 微信支付api
 * @author L.cm
 */
public class PaymentApi {
	
	private PaymentApi() {}
	
	// 文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
	private static String unifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 交易类型枚举
	 * WAP的文档：https://pay.weixin.qq.com/wiki/doc/api/wap.php?chapter=15_1
	 * @author L.cm
	 * email: 596392912@qq.com
	 * site:http://www.dreamlu.net
	 * @date 2015年10月27日 下午9:46:27
	 */
	public static enum TradeType {
		JSAPI, NATIVE, APP, WAP
	}
	
	/**
	 * 统一下单
	 * @return
	 */
	public static String pushOrder(Map<String, String> params) {
		return HttpUtils.post(unifiedOrderUrl, PaymentKit.toXml(params));
	}
	
	/**
	 * 支付相关请求
	 */
	private static Map<String, String> request(String url, Map<String, String> params, String paternerKey) {
		params.put("nonce_str", System.currentTimeMillis() + "");
		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);
		String xmlStr = HttpUtils.post(url, PaymentKit.toXml(params));
		return PaymentKit.xmlToMap(xmlStr);
	}
	
	/*
	 * 文档说明：https://pay.weixin.qq.com/wiki/doc/api/wap.php?chapter=15_4
	 * 
	 * 公众账号ID 		appid		是	String(32)	wx8888888888888888	微信分配的公众账号ID
	 * 随机字符串 		noncestr	是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
	 * 订单详情扩展字符串	package		是	String(32)	WAP	扩展字段，固定填写WAP
	 * 预支付交易会话标识	prepayid	是	String(64)	wx201410272009395522657a690389285100	微信统一下单接口返回的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	 * 签名			     sign		是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
	 * 时间戳			timestamp	是	String(32)	1414561699	当前的时间，其他详见时间戳规则
	 * 
	 */
	public static String getDeepLink(String appId, String prepayId, String paternerKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("noncestr", System.currentTimeMillis() + "");
		params.put("package", "WAP");
		params.put("prepayid", prepayId);
		params.put("timestamp", System.currentTimeMillis() / 1000 + "");
		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);

		String string1 = PaymentKit.packageSign(params, true);

		String string2 = "";
		try { string2 = PaymentKit.urlEncode(string1); } catch (UnsupportedEncodingException e) {}

		return "weixin://wap/pay?" + string2;
	}
	
	// 文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
	private static String orderQueryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	/**
	 * 根据商户订单号查询信息
	 * @param appid 公众账号ID
	 * @param mch_id 商户号
	 * @param paternerKey 商户密钥
	 * @param transaction_id 微信订单号
	 * @return Map<String, String> 回调信息
	 */
	public static Map<String, String> queryByTransactionId(String appid, String mch_id, String paternerKey, String transaction_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		params.put("transaction_id", transaction_id);
		return request(orderQueryUrl, params, paternerKey);
	}
	
	/**
	 * 根据商户订单号查询信息
	 * @param appid 公众账号ID
	 * @param mch_id 商户号
	 * @param paternerKey 商户密钥
	 * @param out_trade_no 商户订单号
	 * @return Map<String, String> 回调信息
	 */
	public static Map<String, String> queryByOutTradeNo(String appid, String mch_id, String paternerKey, String out_trade_no) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		params.put("out_trade_no", out_trade_no);
		return request(orderQueryUrl, params, paternerKey);
	}
	
	// 文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3
	private static String closeOrderUrl = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	/**
	 * 关闭订单
	 * @param appid 公众账号ID
	 * @param mch_id 商户号
	 * @param paternerKey 商户密钥
	 * @param out_trade_no 商户订单号
	 * @return Map<String, String> 回调信息
	 */
	public static Map<String, String> closeOrder(String appid, String mch_id, String paternerKey, String out_trade_no) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		params.put("out_trade_no", out_trade_no);
		return request(closeOrderUrl, params, paternerKey);
	}
	
	// 申请退款文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
	public static String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/**
	 * 申请退款，内部添加了随机字符串nonce_str和签名sign
	 * @param params 参数map，内部添加了随机字符串nonce_str和签名sign
	 * @param paternerKey 商户密钥
	 * @param certPath 证书文件目录
	 * @return Map<String, String> map
	 * @throws Exception 
	 */
	public static Map<String, String> refund(Map<String, String> params, String paternerKey, String certPath) {
		params.put("nonce_str", System.currentTimeMillis() + "");
		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);
		String partner = params.get("mch_id");
		String xmlStr = HttpUtils.postSSL(refundUrl, PaymentKit.toXml(params), certPath, partner);
		return PaymentKit.xmlToMap(xmlStr);
	}
	
	// 查询退款文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
	private static String refundQueryUrl = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	private static Map<String, String> baseRefundQuery(Map<String, String> params, String appid, String mch_id, String paternerKey) {
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		return request(refundQueryUrl, params, paternerKey);
	}
	
	/**
	 * 根据微信订单号查询退款
	 * @param appid 公众账号ID
	 * @param mch_id 商户号
	 * @param paternerKey 商户密钥
	 * @param transaction_id 微信订单号
	 * @return Map<String, String> map
	 */
	public static Map<String, String> refundQueryByTransactionId(String appid, String mch_id, String paternerKey, String transaction_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("transaction_id", transaction_id);
		return baseRefundQuery(params, appid, mch_id, paternerKey);
	}
	
	/**
	 * 根据微信订单号查询退款
	 * @param appid 公众账号ID
	 * @param mch_id 商户号
	 * @param paternerKey 商户密钥
	 * @param out_trade_no 商户订单号
	 * @return Map<String, String> map
	 */
	public static Map<String, String> refundQueryByOutTradeNo(String appid, String mch_id, String paternerKey, String out_trade_no) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("out_trade_no", out_trade_no);
		return baseRefundQuery(params, appid, mch_id, paternerKey);
	}
	
	/**
	 * 根据微信订单号查询退款
	 * @param appid 公众账号ID
	 * @param mch_id 商户号
	 * @param paternerKey 商户密钥
	 * @param out_refund_no 商户退款单号
	 * @return Map<String, String> map
	 */
	public static Map<String, String> refundQueryByOutRefundNo(String appid, String mch_id, String paternerKey, String out_refund_no) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("out_refund_no", out_refund_no);
		return baseRefundQuery(params, appid, mch_id, paternerKey);
	}
	
	/**
	 * 根据微信订单号查询退款
	 * @param appid 公众账号ID
	 * @param mch_id 商户号
	 * @param paternerKey 商户密钥
	 * @param refund_id 微信退款单号
	 * @return Map<String, String> map
	 */
	public static Map<String, String> refundQueryByRefundId(String appid, String mch_id, String paternerKey, String refund_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("refund_id", refund_id);
		return baseRefundQuery(params, appid, mch_id, paternerKey);
	}
	
}
