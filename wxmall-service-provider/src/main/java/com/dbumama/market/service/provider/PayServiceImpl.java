package com.dbumama.market.service.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.BuyerRecharge;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Order;
import com.dbumama.market.model.OrderItem;
import com.dbumama.market.model.Product;
import com.dbumama.market.service.api.pay.PayException;
import com.dbumama.market.service.api.pay.PayService;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.enmu.PaymentStatus;
import com.jfinal.kit.StrKit;
import com.weixin.sdk.api.TemplateMsgApi;
import com.weixin.sdk.kit.WxSdkPropKit;
import com.weixin.sdk.pay.UnifiedOrderApi;
import com.weixin.sdk.pay.UnifiedOrderReqData;
import com.weixin.sdk.pay.UnifiedOrderResData;
import com.weixin.sdk.utils.SignKit;

@Service("payService")
public class PayServiceImpl extends AbstractServiceImpl implements PayService{
	
	private static final Order orderdao = new Order().dao();
	private static final OrderItem orderItemdao = new OrderItem().dao();
	private static final Product productDao = new Product().dao();
	private static final AuthUserConfig authConfigDao = new AuthUserConfig().dao();
	
	@Override
	@Transactional(rollbackFor = PayException.class)
	public void resultLotteryCallback(BuyerUser user, TreeMap<String, Object> params) throws PayException {
		//更新用户抽奖次数
		String totalFee = (String) params.get("total_fee");
		String tradeNo = (String) params.get("out_trade_no");
		//tradeNo = tradeNo.replaceAll("'", "");
		String transactionId = (String) params.get("transaction_id");
		if("200".equals(totalFee)){
			user.setScore(user.getScore() + 20);
		}else if("500".equals(totalFee)){
			user.setScore(user.getScore() + 60);
		}else if("1000".equals(totalFee)){
			user.setScore(user.getScore() + 120);
		}else{
			throw new PayException("支付金额错误");
		}
		
		user.setUpdated(new Date());
		if(!user.update()) throw new PayException("更新公众号["+WxSdkPropKit.get("wx_app_id")+"]用户["+user.getId()+"]信息失败");
		//记录用户充值记录
		BuyerRecharge recharge = new BuyerRecharge();
		recharge.setBuyerId(user.getId());
		recharge.setRecharge(new BigDecimal(totalFee));
		recharge.setOutTradeId(tradeNo);
		recharge.setTransactionId(transactionId);
		recharge.setActive(true);
		recharge.setCreated(new Date());
		recharge.setUpdated(new Date());
		try {
			recharge.save();			
		} catch (Exception e) {
			throw new PayException(e.getMessage());
		}
	}

	@Override
	public TreeMap<String, Object> prepareToPay(String openId, String tradeNo, BigDecimal payFee, String desc, String ip)
			throws PayException {
		AuthUserConfig authConfig = authConfigDao.findFirst("select * from " + AuthUserConfig.table);
		
		String payFee_ = String.valueOf(payFee.multiply(new BigDecimal(100)).intValue());
		
		UnifiedOrderResData unifiedOrderResData = null;
		UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(openId, desc, tradeNo, payFee_,  ip, "JSAPI");
		
		try {
			UnifiedOrderApi unifiedOrderApi = new UnifiedOrderApi();
			unifiedOrderResData = (UnifiedOrderResData) unifiedOrderApi.post(unifiedOrderReqData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PayException(e.getMessage());
		}
		
		if(unifiedOrderResData==null || !"OK".equals(unifiedOrderResData.getReturn_msg())){
			if(unifiedOrderResData != null){
				System.out.println(unifiedOrderResData.getReturn_msg() + "appId:" + authConfig.getAppId() + ",secretKey:" + authConfig.getPaySecretKey() + ",mchid:" + authConfig.getPayMchId());
			}
			throw new PayException("调用微信统一下单接口失败");
		}
		
		//准备调用支付js接口的参数
		TreeMap<String, Object> params = new TreeMap<String, Object>();
    	params.put("appId", authConfig.getAppId());
        params.put("timeStamp", Long.toString(new Date().getTime()));
        params.put("nonceStr", SignKit.genRandomString32());
        params.put("package", "prepay_id="+unifiedOrderResData.getPrepay_id());
        params.put("signType", "MD5");
        
        String paySign = SignKit.sign(params, authConfig.getPaySecretKey());
        
        params.put("paySign", paySign);
        params.put("packageValue", "prepay_id="+unifiedOrderResData.getPrepay_id());
        params.put("returnMsg", unifiedOrderResData.getReturn_msg());
        params.put("sendUrl", authConfig.getWxDomain() + "/pay/result/");
        params.put("tradeno", tradeNo);
		return params;
	}
	
	/**
	 * 微信小程序
	 */
	@Override
	public TreeMap<String, Object> wxAppPrepareToPay(String openId, String tradeNo, BigDecimal payFee,
			String desc, String ip) throws PayException {
		String payFee_ = String.valueOf(payFee.multiply(new BigDecimal(100)).intValue());
		
		UnifiedOrderResData unifiedOrderResData = null;
		UnifiedOrderReqData unifiedOrderReqData = null;
		
			String appId=WxSdkPropKit.get("weapp_id");
			String mchId=WxSdkPropKit.get("wx_mch_id");
			String payScretKey=WxSdkPropKit.get("wx_secret_key"); 
			String notify_url=WxSdkPropKit.get("wx_notify_url"); 
			unifiedOrderReqData = new UnifiedOrderReqData(appId,mchId,payScretKey,notify_url,
					openId, desc, tradeNo, payFee_,  ip, "JSAPI");
		
		
		try {
			UnifiedOrderApi unifiedOrderApi = new UnifiedOrderApi();
			unifiedOrderResData = (UnifiedOrderResData) unifiedOrderApi.post(unifiedOrderReqData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PayException(e.getMessage());
		}
		
		if(unifiedOrderResData==null || !"OK".equals(unifiedOrderResData.getReturn_msg())){
			if(unifiedOrderResData != null){
				System.out.println(unifiedOrderResData.getReturn_msg());
			}
			throw new PayException("调用微信统一下单接口失败");
		}
		
		//准备调用支付js接口的参数
		TreeMap<String, Object> params = new TreeMap<String, Object>();
    	params.put("appId",unifiedOrderReqData.getAppid() );
    	
    	String now=Long.toString(new Date().getTime());
    	if(now.length()>10){
			now=now.substring(0, 10);
		}
        params.put("timeStamp",now);
        
        params.put("nonceStr", SignKit.genRandomString32());
        params.put("package", "prepay_id="+unifiedOrderResData.getPrepay_id());
        params.put("signType", "MD5");
        
        String paySign = SignKit.sign(params,  payScretKey );
        
        params.put("paySign", paySign);
        params.put("packageValue", "prepay_id="+unifiedOrderResData.getPrepay_id());
        params.put("returnMsg", unifiedOrderResData.getReturn_msg());
      
        params.put("tradeno", tradeNo);
		return params;
	}

	@Override
	public void resultOrderCallback(BuyerUser user, TreeMap<String, Object> params) throws PayException {
		String tradeNo = (String) params.get("out_trade_no");
		String transactionId = (String) params.get("transaction_id");
		
		List<Order> orders = orderdao.find("select * from " + Order.table +" where trade_no = ?", tradeNo);
		for(Order order : orders){
			order.setTransactionId(transactionId);
			dealOrder(order, user);
		}
	}
	
	/**
	 * 针对普通订单支付后回调处理
	 * @param order
	 * @param user
	 */
	private void dealOrder(Order order, BuyerUser user){
		//更新商品状态为已支付
		order.setPaymentStatus(PaymentStatus.paid.ordinal());
		order.setUpdated(new Date());
		order.update();
		
		//更新商品库存
		List<OrderItem> orderItems = orderItemdao.find("select * from " + OrderItem.table + " where order_id=? ", order.getId());
		//存储该笔订单中的商品，避免重复发sql查询多次
		List<Product> products = new ArrayList<Product>();
		for(OrderItem orderItem : orderItems){
			Product product = productDao.findById(orderItem.getProductId());
			products.add(product);
			product.setStock(product.getStock() - orderItem.getQuantity() > 0 ? product.getStock() - orderItem.getQuantity() : 0);
			product.setSales(product.getSales() + orderItem.getQuantity());
			product.update();
		}
		
		//推送消息，提示用户支付成功，仓库正在准备发货
		new SendTemplateMsgThread (user, order).start();
	}
	
	class SendTemplateMsgThread extends Thread{
		private BuyerUser buyerUser;   //买家
		private Order order;
		public SendTemplateMsgThread(BuyerUser buyerUser, Order order){
			this.buyerUser = buyerUser;
			this.order = order;
		}
		
		@Override
		public void run() {
			super.run();
			if (buyerUser == null) return;
			//推送一条消息给公众号消息接收者，提醒有人下单了
			JSONObject json = new JSONObject();
			json.put("template_id_short", "TM00015");
			String templateId = TemplateMsgApi.getTemplateId("TM00015", json.toString());
			if(StrKit.isBlank(templateId)) return;
			
			StringBuffer sbuffer = new StringBuffer();
			List<OrderItem> orderItems = orderItemdao.find("select * from " + OrderItem.table + " where order_id=? ", order.getId());
			for(OrderItem orderItem : orderItems){
				sbuffer.append(orderItem.getName() + "X" + orderItem.getQuantity()).append("<br>");
			}
			//1查找消息接收者
			/*List<BuyerUser> receivers = BuyerUser.dao.find(
					"select * from " + BuyerUser.table + " where seller_id=? and auth_app_id=? and subscribe=1 and is_receiver=1", 
					buyerUser.getSellerId(), buyerUser.getAuthAppId());
			//给公众号管理者发送消息，有人支付订单
			for(BuyerUser receiver : receivers){
				TemplateData templateData = TemplateData.New().setTemplate_id(templateId)
						.setTouser(receiver.getOpenId())
						.setUrl("http://"+buyerUser.getAuthAppId()+".dbumama.com/order/detail/"+order.getId())
						.add("first",  "客户[" + buyerUser.getNickname() + "]已成功支付货款，请准备发货", "#173177")
						.add("orderProductName", sbuffer.toString(), "#173177")
						.add("orderMoneySum", order.getTotalPrice() + "，邮费：" + order.getPostFee(), "#173177")
						.add("backupFieldName", "", "#173177")
						.add("backupFieldData", "", "#173177")
						.add("remark", "订单编号："+order.getOrderSn()+", 如对订单有疑问，请联系公众号客服！", "#173177");
					
					ApiResult apiResult = CompTemplateMsgApi.send(accessToken, templateData.build());
					
					if(!apiResult.isSucceed()){
						logger.error("error_code:" + apiResult.getErrorCode() + ",error_msg" + apiResult.getErrorMsg());
					}
			}*/
			
		}
		
	}
	
}
