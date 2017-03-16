package com.dbumama.market.web.admin.order.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbumama.market.model.Area;
import com.dbumama.market.model.ExpressTemplate;
import com.dbumama.market.model.Order;
import com.dbumama.market.model.Refund;
import com.dbumama.market.model.Shipping;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.service.api.order.OrderException;
import com.dbumama.market.service.api.order.OrderListParamDto;
import com.dbumama.market.service.api.order.OrderResultDto;
import com.dbumama.market.service.api.order.OrderService;
import com.dbumama.market.service.enmu.PaymentStatus;
import com.dbumama.market.service.enmu.ShippingStatus;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.plugin.activerecord.Page;

@RouteBind(path="order")
public class OrderController extends AdminBaseController<Order>{

	@BY_NAME
	private OrderService orderService;
	@BY_NAME
	private AreaService areaService;
	public void index(){
		//查询用户已添加的快递模板
		List<ExpressTemplate> templates = ExpressTemplate.dao.find("select * from " + ExpressTemplate.table + " where seller_id=? and active = 1", getSellerId());
		setAttr("expressList", templates);
		render("/order/o_index.html");
	}
	
	public void list(){
		OrderListParamDto orderParamDto = new OrderListParamDto(getSellerId(), getPageNo());
		orderParamDto.setStartDate(getPara("startDate"));
		orderParamDto.setEndDate(getPara("endDate"));
		orderParamDto.setBuyerNickName(getPara("nickNmae"));
		orderParamDto.setReceiverPhone(getPara("receiverPhone"));
		orderParamDto.setReceiverName(getPara("receiverName"));
		orderParamDto.setOrderStatus(getPara("orderStatus"));
		orderParamDto.setPaymentStatus(getPara("paymentStatus"));
		orderParamDto.setShippingStatus(getPara("shippingStatus"));
		try {
			//合并订单  买家收货地址跟收货人做map key 对应的交易数据用List装
			Page<OrderResultDto> orders = orderService.list(orderParamDto);
			rendSuccessJson(orders);
		} catch (OrderException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	/**
	 * 
	 * 退款
	 */
	public void refund(){
		String orderIds=getPara("orderIds");
		List<OrderResultDto> orderDetailResultDto=orderService.getOrders(orderIds);
		setAttr("orderDetailResultDto", orderDetailResultDto);
		setAttr("orderIds", orderIds);
		render("/order/refund.html");
	}
	
	public void sendMoregoods(){
		String orderIds=getPara("orderIds");
		try {
			Map<String, String> statusMap = new HashMap<String, String>();
			statusMap.put("p", "paid");//查询出已支付，未发货订单
			List<OrderResultDto> orderDetailResultDto=orderService.getOrders(orderIds, statusMap);
			rendSuccessJson(orderDetailResultDto);
		} catch (OrderException e) {
			rendFailedJson(e.getMessage());
		}
	}
	/**
	 * 批量保存发货信息
	 */
	public void saveSendMoreGoods(){
		final String items = getPara("items");
		try {
			orderService.saveShipping(items);
			rendSuccessJson("发货成功");
		} catch (Exception e) {
			rendFailedJson("发货失败请重新发货！");
		}
		

	}
	/**
	 * 保存发货信息
	 */
	
	public void saveSendGoods(){
		final Long orderIds = getParaToLong("orderIds");
		final String exp_key = getPara("exp_key");
		final String bill_number = getPara("bill_number");
		Shipping shop=new Shipping();
		shop.setOrderId(orderIds);
		shop.setExpKey(exp_key);
		shop.setBillNumber(bill_number);
		shop.setActive(1);
		shop.setCreated(new Date());
		shop.setUpdated(new Date());
		try {
			shop.save();
			Order order=Order.dao.findById(orderIds);
			order.setShippingStatus(ShippingStatus.shipped.ordinal());
			order.update();
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson("发货失败请重新发货！");
		}
	}
	
	/**
	 * 保存退款信息
	 */
	public void saveRefund(){
		final Long orderId = getParaToLong("orderIds");
		final String transactionId = getPara("transactionId");
		final  BigDecimal cash=new BigDecimal(getPara("cash"));
		Refund refund=new Refund();
		refund.setOrderId(orderId);
		refund.setTransactionId(transactionId);
		refund.setCash(cash);
		refund.setCreated(new Date());
		refund.setUpdated(new Date());
		refund.setActive(true);
		try {
			refund.save();
			Order order=Order.dao.findById(orderId);
			order.setPaymentStatus(PaymentStatus.refunded.ordinal());
			order.update();
			rendSuccessJson(refund);
		} catch (Exception e) {
			rendFailedJson("退货失败请重新退货！");
		}
	}
	
	 /**
		 * 地区
		 */
		public void area() {
			Long parentId = getParaToLong("parentId");
			List<Area> areas = new ArrayList<Area>();
			Area parent =Area.dao.findById(parentId);
			if (parent != null) {
				areas =areaService.getChildren(parent.getId());
			} else {
				areas = areaService.findRoots();
			}
			Map<Long, String> options = new HashMap<Long, String>();
			for (Area area : areas) {
				options.put(area.getId(), area.getName());
			}
			renderJson(options);
		}
	
	@Override
	protected Class<Order> getModelClass() {
		return Order.class;
	}

}
