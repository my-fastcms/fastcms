/**
 * 文件名:Constants.java
 * 版本信息:1.0
 * 日期:2015-5-24
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.weixin.sdk;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-24
 */
public interface Constants {
	
	String DEFAULT_ENCODING = "UTF-8";
	
	public static final String SUCCESS = "SUCCESS";
	
	public static final String FAIL = "FAIL";
	
/*	//微信分配的公众号ID（开通公众号之后可以获取到）
	public static final String appID = "wx0dd16298bc16ed63";
	public static final String appSecret = "043397fb77eca2508d3f53a123076cc0";

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	public static final String mchID = "1281049301";*/
	
	/**
	 * 这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	 * 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，
	 * API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	 * 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
	 */
//	public static final String API_SECRET_KEY = "guangzhoudbumamakeji2015guangzhou";

	//受理模式下给子商户分配的子商户号
	public static final String subMchID = "";

	//HTTPS证书的本地路径
	//public static final String certLocalPath = "cert/apiclient_cert.p12";

	//HTTPS证书密码，默认密码等于商户号MCHID
	public static final String certPassword = "";

	//以下是几个API的路径：
	//1）被扫支付API
	public static final String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

	//2）被扫支付查询API
	public static final String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

	//3）退款API
	public static final String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	//4）退款查询API
	public static final String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

	//5）撤销API
	public static final String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

	//6）下载对账单API
	public static final String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

	//7) 统计上报API
	public static final String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";
	
}
