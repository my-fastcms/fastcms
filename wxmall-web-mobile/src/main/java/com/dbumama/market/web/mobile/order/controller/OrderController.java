package com.dbumama.market.web.mobile.order.controller;

import java.util.Date;
import java.util.List;

import com.dbumama.market.model.Order;
import com.dbumama.market.model.ProductReview;
import com.dbumama.market.service.api.order.OrderCreateParamDto;
import com.dbumama.market.service.api.order.OrderResultDto;
import com.dbumama.market.service.api.order.OrderException;
import com.dbumama.market.service.api.order.OrderListParamDto;
import com.dbumama.market.service.api.order.OrderMobileResultDto;
import com.dbumama.market.service.api.order.OrderService;
import com.dbumama.market.service.enmu.OrderStatus;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path = "order")
public class OrderController extends BaseMobileController{

	@BY_NAME
	private OrderService orderService;
	
	public void index(){
		render("/order/index.html");
	}
	
	/**
	 * 待支付
	 */
	public void unpay(){
		setAttr("payment_status", "unpaid");
		render("/order/index.html");
	}
	
	/**
	 * 已支付，待收货
	 */
	public void payed(){
		setAttr("payment_status", "paid");
		setAttr("shipping_status", "shipped");
		setAttr("order_status", "unconfirmed");
		render("/order/index.html");
	}
	
	/**
	 * 确定收货
	 */
    public void queren(){
    	Long orderId = getParaToLong("orderId");
    	Order order=Order.dao.findById(orderId);
    	if (order != null) {
    		order.setOrderStatus(OrderStatus.completed.ordinal());
    		order.update();
    		rendSuccessJson("已收货");
    	}else{
    		rendFailedJson("请求失败");
    	}
    }
	
	/**
	 * 已支付，已发货，已完成
	 */
	public void completed(){
		setAttr("payment_status", "paid");
		setAttr("shipping_status", "shipped");
		setAttr("order_status", "completed");
		render("/order/index.html");
	}
	/**
	 * 去评价
	 */
	public void toReview(){
		Long orderId = getParaToLong("orderId");
		OrderResultDto dto=orderService.getOrder(orderId);
		setAttr("orderDto", dto);
		render("/order/review.html");
	}
	
	/**
	 * 保存评价
	 */
	public void saveReview(){
		Long orderId = getParaToLong("orderId");
		Long productId = getParaToLong("productId");
		String content=getPara("content");
		Integer score=getParaToInt("score");
		ProductReview review=new ProductReview();
		review.setOrderId(orderId);
		review.setProductId(productId);
		review.setContent(content);
		review.setScore(score);
		review.setBuyerId(getBuyerId());
		review.setActive(true);
		review.setCreated(new Date());
		review.setUpdated(new Date());
		review.setIsShow(true);
		try {
			review.save();
			rendSuccessJson("评论成功");
		} catch (Exception e) {
			rendFailedJson("评论失败，请重新评论");
		}
	}
	//创建订单
	public void create(){
		final String memo = getPara("memo");
		final String items = getPara("items");
		final String isInvoice = getPara("isInvoice");
		final String invoiceTitle = getPara("invoiceTitle"); 
		OrderCreateParamDto orderParamDto = new OrderCreateParamDto(getSellerId(), getBuyerId(), getParaToLong("receiverId"), items);
		orderParamDto.setMemo(memo);	//买家留言
		orderParamDto.setIsInvoice(isInvoice);
		orderParamDto.setInvoiceTitle(invoiceTitle);
		try {
			rendSuccessJson(orderService.create(orderParamDto));
		} catch (OrderException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void list(){
		OrderListParamDto orderParamDto = new OrderListParamDto(getSellerId(), getBuyerId(), getPageNo());
		orderParamDto.setOrderStatus(getPara("order_status"));
		orderParamDto.setPaymentStatus(getPara("payment_status"));
		orderParamDto.setShippingStatus(getPara("shipping_status"));
		try {
			List<OrderMobileResultDto> orderListResultDtos = orderService.list4Mobile(orderParamDto);
			rendSuccessJson(orderListResultDtos);
		} catch (OrderException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	/**
	 * 订单详情
	 */
	public void detail(){
		OrderResultDto orderDetail = orderService.getOrder(getParaToLong("orderId"));
		setAttr("orderDetail", orderDetail);
		render("/order/detail.html");
	}
	
	public void getPostFee(){
		final String items = getPara("items");
		final Long receiverId = getParaToLong("receiverId");
		try {
			OrderResultDto orderDto = orderService.balance(getBuyerId(), receiverId, items);
			rendSuccessJson(orderDto);
		} catch (OrderException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
}
