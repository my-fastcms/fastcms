package com.dbumama.market.web.mobile.pay.controller;

import com.dbumama.market.model.Area;
import com.dbumama.market.model.BuyerReceiver;
import com.dbumama.market.service.api.order.OrderResultDto;
import com.dbumama.market.service.api.order.OrderService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;

@RouteBind(path="pay")
public class PayController extends BaseMobileController{

	@BY_NAME
	private OrderService orderService;

	/**
	 * h5 发起支付
	 */
	public void index(){
		String type = getPara("type");
		if(StrKit.notBlank(type)){
			//充值抽奖
			lottery();
		}else{
			rendFailedJson("请开通企业版");
		}
	}
	
	/**
	 * 订单结算
	 */
	public void balance (){
		final String items = getPara("items");
		final Long receiverId = getParaToLong("receiverId");
		setAttr("items", items);
		OrderResultDto orderDto = orderService.balance(getBuyerId(), receiverId, items);
		setAttr("order", orderDto);
    	//查询默认收货地址
		final String sql = "SELECT * FROM "+BuyerReceiver.table+" WHERE buyer_id = ? AND is_default = 1 ";
		BuyerReceiver buyerReceiver=BuyerReceiver.dao.findFirst(sql, getBuyerId());
		if(buyerReceiver != null) {
			Area area=Area.dao.findById(buyerReceiver.getAreaId());
			setAttr("fullName",area.getFullName());
			setAttr("receiver", buyerReceiver);
		}
		render("/pay/jiesuan.html");
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
		
		rendFailedJson("请开通企业版");
	}
	
	
}
