package com.dbumama.market.web.mobile.pay.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dbumama.market.model.Area;
import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.BuyerReceiver;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.service.api.authuser.AuthUserConfigService;
import com.dbumama.market.service.api.customer.MemberRankService;
import com.dbumama.market.service.api.order.OrderException;
import com.dbumama.market.service.api.order.OrderPayResultDto;
import com.dbumama.market.service.api.order.OrderResultDto;
import com.dbumama.market.service.api.order.OrderService;
import com.dbumama.market.service.api.pay.PayException;
import com.dbumama.market.service.api.pay.PayService;
import com.dbumama.market.service.api.receiver.ReceiverService;
import com.dbumama.market.service.api.user.UserService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.dbumama.market.web.core.utils.IpKit;
import com.jfinal.kit.StrKit;
import com.weixin.sdk.utils.SignKit;

@RouteBind(path="pay")
public class PayController extends BaseMobileController{

	//返回成功的xml给微信
	static final String TO_RES_WEIXIN = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg></xml>";
	
	//正在处理的用户订单
	static Set<String> userTradeSet = new HashSet<String>();
	
	@BY_NAME
	private PayService payService;
	@BY_NAME
	private OrderService orderService;
	@BY_NAME
	private AreaService areaService;
	@BY_NAME
	private AuthUserConfigService authUserConfigService;
	@BY_NAME
	private ReceiverService receiverService;
	@BY_NAME
	private UserService userService;
	@BY_NAME
	private MemberRankService memberRankService;
	
	/**
	 * h5 发起支付
	 */
	public void index(){
		String type = getPara("type");
		TreeMap<String, Object> params = null;
		try {
			if(StrKit.notBlank(type)){
				//充值抽奖
				lottery();
			}else{
				OrderPayResultDto order = orderService.getOrderPayInfo(getParaToLong("orderId"), getBuyerId());
				params = payService.prepareToPay(getOpenId(), order.getTradeNo(), order.getPayFee(), order.getOrderSn(), IpKit.getRealIp(getRequest()));
			}
			rendSuccessJson(params);
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
		
	}
	
	/**
	 * 订单结算
	 */
	public void balance (){
		final String items = getPara("items");
		setAttr("items", items);
		//查询默认收货地址
		BuyerReceiver buyerReceiver = receiverService.getDefaultReceiver(getBuyerId());
		if(buyerReceiver != null) {
			Area area=areaService.findById(buyerReceiver.getAreaId());
			setAttr("fullName",area.getFullName());
			setAttr("receiver", buyerReceiver);
		}
		try {
			OrderResultDto orderDto = orderService.balance(getBuyerId(), null, items);
			setAttr("order", orderDto);
			render("/pay/jiesuan.html");
		} catch (OrderException e) {
			setAttr("error", e.getMessage());
			render("/pay/jiesuan_error.html");
		}
	}
	
	void lottery () {
		String type = getPara("type");
		int totalMoney = 0;
		if("x11".equals(type)){
			totalMoney = 2;
		}else if("x12".equals(type)){
			totalMoney = 5;
		}else if("x13".equals(type)){
			totalMoney = 10;
		}else{
			totalMoney = 0;
		}
		if(totalMoney<=0){
			rendFailedJson("提交的金额为0，请重新确认");
			return;
		}
		//生成本次交易号tradeNo，同步tradeNo 保证唯一
		final String tradeNo = "l-" + getUUIDStr().substring(2);
		try {
			TreeMap<String, Object> params = payService.prepareToPay(getOpenId(), tradeNo, new BigDecimal(totalMoney), "疯狂抽奖", IpKit.getRealIp(getRequest()));
	        rendSuccessJson(params);
		} catch (PayException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	/**
	 * 【接收到的notify通知】:
		<xml><appid><![CDATA[wx0dd16298bc16ed63]]></appid>
			<bank_type><![CDATA[CFT]]></bank_type>
			<cash_fee><![CDATA[5]]></cash_fee>
			<device_info><![CDATA[WEB]]></device_info>
			<fee_type><![CDATA[CNY]]></fee_type>
			<is_subscribe><![CDATA[Y]]></is_subscribe>
			<mch_id><![CDATA[1281049301]]></mch_id>
			<nonce_str><![CDATA[92hce70z47sbpgv92kgrf6b2sznkn8y4]]></nonce_str>
			<openid><![CDATA[oQ774wnoZjqJt4UdAXusjT9WBvgI]]></openid>
			<out_trade_no><![CDATA[v2bbu7gb2j68r4c6978hrmpp0mls7wvx]]></out_trade_no>
			<result_code><![CDATA[SUCCESS]]></result_code>
			<return_code><![CDATA[SUCCESS]]></return_code>
			<sign><![CDATA[0134E6A1C41E714D03193003EC51552D]]></sign>
			<time_end><![CDATA[20151105183006]]></time_end>
			<total_fee>5</total_fee>
			<trade_type><![CDATA[JSAPI]]></trade_type>
			<transaction_id><![CDATA[1002690406201511051467804891]]></transaction_id>
		</xml>
	 * 支付成功后，微信回调地址
	 */
	/**
	 * 1.同步此通知回调方法
	 * 2.检查业务数据状态
	 * 通过以上两步：防止微信重复通知，造成数据混乱
	 */
	public void result(){
		log.debug("===================支付成功，成功回调");
		String resultXml = null;
		try {
			resultXml = inputStream2String(getRequest().getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			renderNull();
			return;
		}
		if(StrKit.isBlank(resultXml)){
			renderNull();
			return;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(resultXml);
		} catch (DocumentException e) {
			e.printStackTrace();
			renderNull();
			return;
		}
		Element root = doc.getRootElement();
		//校验签名
		TreeMap<String, Object> params = new TreeMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<Element> elements = root.elements();
		String authAppId = ""; 
		for(Element e : elements){
			params.put(e.getName(), e.getTextTrim());
			if("appid".equals(e.getName())){
				authAppId = e.getTextTrim();
			}
		}
		
		AuthUserConfig authUser = authUserConfigService.getAuthConfig();
		if(authUser == null){
			log.error("[" + authAppId + "]授权公众号不存在");
			renderText(TO_RES_WEIXIN, "text/xml");
			return;
		}
		String sign = SignKit.sign(params, authUser.getPaySecretKey());
		String tenpaySign = root.elementText("sign").toUpperCase();
		if(StrKit.isBlank(sign) 
				|| StrKit.isBlank(tenpaySign) 
				|| !sign.equals(tenpaySign)){
			log.error("微信支付异步通知签名错误");
			renderText(TO_RES_WEIXIN, "text/xml");
			return;
		}
		
		log.debug("成功回调，签名正确");
		
		//根据out_trade_no 判断订单支付来源
		String tradeNo = (String) params.get("out_trade_no");
		log.debug("tradeNo:" + tradeNo);
		String openid = (String) params.get("openid");
		BuyerUser user = userService.findByOpenId(openid);
		if(user==null){
			log.error("["+ openid + "] user is null");
			renderText(TO_RES_WEIXIN, "text/xml");
			return;
		}
		
		synchronized (userTradeSet) {
			if(userTradeSet.contains(tradeNo+user.getId())){
				//正在处理的订单数据，本次不处理
				renderText(TO_RES_WEIXIN, "text/xml");
				return;
			}
			userTradeSet.add(tradeNo+user.getId());
		}
		try {
			//==========================业务处理
			if(StrKit.notBlank(tradeNo) && tradeNo.startsWith("l-")){
				//说明是从抽奖充值支付过来的
				payService.resultLotteryCallback(user, params);
			}else if(StrKit.notBlank(tradeNo) && tradeNo.startsWith("v-")){
				//payService.resultVipCallback(user, params);
			}else{
				//回调更新订单状态
				payService.resultOrderCallback(user, params);
			}
		} catch (PayException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			//处理成功后，移除
			userTradeSet.remove(tradeNo+user.getId());
		}
		
		renderText(TO_RES_WEIXIN, "text/xml");
	}
	
}
