package com.dbumama.market.service.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.BuyerReceiver;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Cart;
import com.dbumama.market.model.DeliverySet;
import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.model.Order;
import com.dbumama.market.model.OrderGheader;
import com.dbumama.market.model.OrderGuser;
import com.dbumama.market.model.OrderItem;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductSpecItem;
import com.dbumama.market.model.Refund;
import com.dbumama.market.model.Shipping;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.order.OrderCreateParamDto;
import com.dbumama.market.service.api.order.OrderException;
import com.dbumama.market.service.api.order.OrderItemResultDto;
import com.dbumama.market.service.api.order.OrderListParamDto;
import com.dbumama.market.service.api.order.OrderMobileResultDto;
import com.dbumama.market.service.api.order.OrderPayResultDto;
import com.dbumama.market.service.api.order.OrderResultDto;
import com.dbumama.market.service.api.order.OrderService;
import com.dbumama.market.service.api.order.RefundParamDto;
import com.dbumama.market.service.api.order.SendGoodParamDto;
import com.dbumama.market.service.api.serinum.SerinumService;
import com.dbumama.market.service.api.ump.PromotionService;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.enmu.GroupStatus;
import com.dbumama.market.service.enmu.OrderStatus;
import com.dbumama.market.service.enmu.OrderType;
import com.dbumama.market.service.enmu.PaymentStatus;
import com.dbumama.market.service.enmu.ShippingStatus;
import com.dbumama.market.service.sql.QueryHelper;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@Service("orderService")
public class OrderServiceImpl extends AbstractServiceImpl implements OrderService{

	@Autowired
	private PromotionService promotionService;	//限时打折
	@Autowired
	private SerinumService serinumService;		//序号

	private static final BuyerReceiver receiverDao = new BuyerReceiver().dao();
	private static final Cart cartDao = new Cart().dao();
	private static final DeliverySet deliveryDao = new DeliverySet().dao();
	private static final DeliveryTemplate deliveryTpldao = new DeliveryTemplate().dao();
	private static final Order orderdao = new Order().dao();
	private static final OrderGheader orderGheaderdao = new OrderGheader().dao();
	private static final OrderGuser orderGuserdao = new OrderGuser().dao();
	private static final OrderItem orderItemdao = new OrderItem().dao();
	private static final Product productDao = new Product().dao();
	private static final ProductSpecItem prodSpecItemdao = new ProductSpecItem().dao();
	private static final SpecificationValue specValueDao = new SpecificationValue().dao();
	
	@Override
	public Page<OrderResultDto> list(OrderListParamDto orderParamDto) throws OrderException {
		if(orderParamDto == null || orderParamDto.getSellerId() == null)
			throw new OrderException("查询订单参数错误");
		
		String select = " SELECT  o.*, o.id as o_id, o.created as o_created, b.id as b_id, b.nickname, b.open_id, r.*, r.phone as r_phone, r.city as r_city, r.province as r_province, r.district as r_district ";
		String sqlExceptSelect = " FROM "+Order.table+" o "
				+ " left join " + BuyerUser.table + " b on o.buyer_id=b.id "
				+ " left join " + BuyerReceiver.table + " r on o.receiver_id=r.id "
				+ " where 1=1 and (o.order_type = 0 or o.order_type is null) ";
		
		StringBuffer where = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		setQuery(where, params, orderParamDto);
		sqlExceptSelect = sqlExceptSelect + " " + where.toString() + " order by o.created desc ";
		Page<Record> orders = Db.paginate(orderParamDto.getPageNo(), orderParamDto.getPageSize(), select, sqlExceptSelect, params.toArray());
		List<OrderResultDto> orderList = new ArrayList<OrderResultDto>();
		for(Record order : orders.getList()){
			OrderResultDto orderListResultDto = getOrderResult(order);
			orderList.add(orderListResultDto);
		}
		
		return new Page<OrderResultDto>(orderList, orderParamDto.getPageNo(), orderParamDto.getPageSize(), orders.getTotalPage(), orders.getTotalRow());
	}
		
	//统一设置订单公共查询条件
	private void setQuery(StringBuffer where, List<Object> params, OrderListParamDto orderParamDto){
		where.append(" and o.seller_id=?");
		params.add(orderParamDto.getSellerId());
		
		if(StrKit.notBlank(orderParamDto.getStartDate()) && StrKit.notBlank(orderParamDto.getEndDate())){
			where.append(" and (o.created between ? and ?) ");
			params.add(orderParamDto.getStartDate());
			params.add(orderParamDto.getEndDate());
		}
		
		if(StrKit.notBlank(orderParamDto.getBuyerNickName())){
			where.append(" and b.nickname like ?");
			params.add("%"+orderParamDto.getBuyerNickName()+"%");
		}
		
		if(StrKit.notBlank(orderParamDto.getReceiverName())){
			where.append(" and r.receiver_name = ?");
			params.add(orderParamDto.getReceiverName());
		}
		
		if(StrKit.notBlank(orderParamDto.getReceiverPhone())){
			where.append(" and r.receiver_phone = ?");
			params.add(orderParamDto.getReceiverPhone());
		}
		
		if(StrKit.notBlank(orderParamDto.getOrderStatus())){
			final String osArr [] = orderParamDto.getOrderStatus().split("_");
			String type = "";
			String value = "";
			try {
				type = osArr[0];
				value = osArr[1];
			} catch (Exception e) {
				throw new OrderException("不支持的订单状态");
			}
			setOrderStatusCondition(type, value, where, params);
		}
	}

	@Override
	@Transactional(rollbackFor = OrderException.class)
	public Long create(OrderCreateParamDto orderParamDto) throws OrderException {
		if(orderParamDto == null || orderParamDto.getSellerId() == null 
				|| orderParamDto.getBuyerId() == null || orderParamDto.getReceiverId() == null
				|| StrKit.isBlank(orderParamDto.getItems())){
			throw new OrderException("创建订单失败，请检查参数");
		}
		
		//解析出提交的订单数据
		OrderResultDto orderDto = null;
		try {
			orderDto = balance(orderParamDto.getBuyerId(), orderParamDto.getReceiverId(), orderParamDto.getItems());
		} catch (OrderException e) {
			throw new OrderException(e.getMessage());
		}
		if(orderDto == null) throw new OrderException("创建订单失败，解析订单数据出错");
		
		Order order = new Order();
		order.setSellerId(orderParamDto.getSellerId());
		order.setBuyerId(orderParamDto.getBuyerId());
		order.setReceiverId(orderParamDto.getReceiverId());
		order.setOrderSn(serinumService.getOrderSn());
		order.setOrderType(orderDto.getOrderType());		//订单类型
		order.setOrderStatus(OrderStatus.unconfirmed.ordinal());
		order.setPaymentStatus(PaymentStatus.unpaid.ordinal());
		order.setShippingStatus(ShippingStatus.unshipped.ordinal());
		order.setMemo(orderParamDto.getMemo());
		order.setTotalPrice(orderDto.getTotalPrice());		//商品价格总和，不含邮费
		order.setPostFee(orderDto.getPostFee());			//订单邮费
		BigDecimal payFee = order.getTotalPrice().add(order.getPostFee());	//最终支付金额
		order.setPayFee(payFee);
		order.setTradeNo(getTradeNo());
		order.setActive(true);
		order.setCreated(new Date());
		order.setUpdated(new Date());
		
		try {
			order.save();
		} catch (ActiveRecordException e) {
			throw new OrderException(e.getMessage());
		}
		
		//保存订单项数据
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (OrderItemResultDto orderItemDto : orderDto.getOrderItems()) {
			Product product = productDao.findById(orderItemDto.getProductId());
			OrderItem orderItem = new OrderItem();
			orderItem.setSn(product.getSn());
			orderItem.setName(product.getName());
			orderItem.setProductImg(product.getImage());
			orderItem.setQuantity(orderItemDto.getQuantity());
			orderItem.setProductId(product.getId());
			orderItem.setOrderId(order.getId());
			if(orderItemDto.getSpecificationValues() != null){
				final StringBuffer sbff = new StringBuffer();
				for(SpecificationValue sfv : orderItemDto.getSpecificationValues()){
					sbff.append(sfv.getId()).append(",");
				}
				orderItem.setSpecificationValue(sbff.length()>0 ? sbff.deleteCharAt(sbff.length()-1).toString() : sbff.toString());
			}
			orderItem.setPrice(new BigDecimal(orderItemDto.getPrice()));
			orderItem.setActive(true);
			orderItem.setCreated(new Date());
			orderItem.setUpdated(new Date());
			orderItems.add(orderItem);
		}
		
		try {
			Db.batchSave(orderItems, orderItems.size());
		} catch (ActiveRecordException e) {
			throw new OrderException(e.getMessage());
		}
		
		//创建订单成功后检查是否有购物车数据，删除掉
		for(OrderItemResultDto tempOrderItem : orderDto.getOrderItems()){
			Cart c = cartDao.findFirst(" select * from " + Cart.table + " where buyer_id=? and product_id=? ", orderParamDto.getBuyerId(), tempOrderItem.getProductId());
			if(c!=null) c.delete();
		}
		
		return order.getId();
	}
	
	@Override
	public List<OrderMobileResultDto> list4Mobile(OrderListParamDto orderParamDto) throws OrderException {
		if(orderParamDto == null || orderParamDto.getSellerId() == null || orderParamDto.getBuyerId() == null)
			throw new OrderException("调用手机端订单列表数据接口缺少参数");
		
		String select = " SELECT  * ";
		String sqlExceptSelect = " FROM "+Order.table;

		QueryHelper queryHelper = new QueryHelper(select, sqlExceptSelect);
		queryHelper.addWhere("seller_id", orderParamDto.getSellerId())
		.addWhereNOT_EQUAL("order_type", 2)//排除拼团订单
		.addWhere("buyer_id", orderParamDto.getBuyerId())
		.addWhere("payment_status", StrKit.notBlank(orderParamDto.getPaymentStatus()) ? PaymentStatus.valueOf(orderParamDto.getPaymentStatus()).ordinal() : null)
		.addWhere("shipping_status", StrKit.notBlank(orderParamDto.getShippingStatus()) ? ShippingStatus.valueOf(orderParamDto.getShippingStatus()).ordinal() : null)
		.addOrderBy("desc", "created");
		
		if(StrKit.notBlank(orderParamDto.getOrderStatus())){
			queryHelper.addWhere("order_status", OrderStatus.valueOf(orderParamDto.getOrderStatus()).ordinal());
		}else{
			queryHelper.addWhereNOT_EQUAL("order_status", OrderStatus.cancelled.ordinal());
		}
		
		queryHelper.build();
		
		Page<Order> orders = orderdao.paginate(orderParamDto.getPageNo(), orderParamDto.getPageSize(), queryHelper.getSelect(), queryHelper.getSqlExceptSelect(), queryHelper.getParams());

		List<OrderMobileResultDto> results = new ArrayList<OrderMobileResultDto>();
		for(Order order : orders.getList()){
			OrderMobileResultDto orderListResultDto = new OrderMobileResultDto();
			orderListResultDto.setCreated(order.getCreated());
			orderListResultDto.setOrderId(order.getId());
			orderListResultDto.setSn(order.getOrderSn());
			if(order.getInt("payment_status") == PaymentStatus.unpaid.ordinal()){
				orderListResultDto.setStatus("待支付");
			}else if(order.getInt("payment_status") == PaymentStatus.paid.ordinal()){
				orderListResultDto.setStatus("已支付");
			}
			if(order.getInt("shipping_status") == ShippingStatus.shipped.ordinal()){
				orderListResultDto.setStatus("已发货");
			}
			if(order.getInt("order_status") == OrderStatus.completed.ordinal()){
				orderListResultDto.setStatus("已完成");
			}else if(order.getInt("order_status") == OrderStatus.cancelled.ordinal()){
				orderListResultDto.setStatus("已取消");
			}
			orderListResultDto.setTotalPrice(order.getPayFee().toString());
			orderListResultDto.setOrderItems(getOrderItems(orderListResultDto.getOrderId(),orderParamDto.getBuyerId()));
			results.add(orderListResultDto);
		}
		
		return results;
	}
	
	@Override
	public OrderResultDto getOrder(Long orderId) throws OrderException {
		if(orderId == null) throw new OrderException("调用订单详情接口出错，请传入订单id");
		Record record = Db.findFirst(" SELECT  o.*, o.id as o_id, o.created as o_created, r.*, r.id as r_id, r.phone as r_phone, r.city as r_city, r.province as r_province, r.district as r_district, bu.nickname FROM "+Order.table+" o "
				+ " left join " + BuyerReceiver.table + " r on o.receiver_id=r.id "
				+ " left join " + BuyerUser.table + " bu on o.buyer_id=bu.id "
				+ " where o.id=?", orderId);
		return getOrderResult(record);
	}
	
	@Override
	public OrderResultDto getOrder(Long orderId, Long buyerId) throws OrderException {
		if(orderId == null || buyerId == null) throw new OrderException("获取拼团订单详情缺少必要参数");
		
		Order _order = orderdao.findById(orderId);
		if(_order == null) throw new OrderException("订单不存在");
		
		if(_order.getOrderType() != OrderType.pintuan.ordinal()){
			return getOrder(orderId);
		}
		
		Record order = Db.findFirst("select og.*, og.created as og_created, og.pay_fee as og_pay_fee, og.post_fee as og_post_fee, "
				+ "og.price as og_price, ogh.product_id as og_product_id, og.memo as og_memo, "
				+ "og.quantity as og_quantity, og.payment_status as og_payment_status, og.transaction_id as og_transaction_id,"
				+ "r.*, r.id as r_id, r.phone as r_phone, r.city as r_city, r.province as r_province, r.district as r_district, "
				+ "o.*, oi.*, o.id as o_id from " 
				+ OrderGuser.table + " og "
				+ " left join " + OrderGheader.table + " ogh on og.gheader_id=ogh.id "
				+ " left join " + BuyerReceiver.table + " r on og.receiver_id=r.id "
				+ " left join " + Order.table + " o on ogh.order_id=o.id "
				+ " left join " + OrderItem.table + " oi on ogh.product_id=oi.product_id and ogh.order_id=oi.order_id "
				+ " where ogh.order_id=? and og.buyer_id=? ", orderId, buyerId);
		
		OrderResultDto orderDetailDto = new OrderResultDto();
		orderDetailDto.setOrderId(order.getLong("o_id"));
		orderDetailDto.setPayFee(order.getBigDecimal("og_pay_fee"));   			//需计算应付金额
		orderDetailDto.setBuyerNick(order.getStr("nickname"));
		if(StrKit.notBlank(order.getStr("og_memo"))){
			orderDetailDto.setBuyerMemo(order.getStr("og_memo"));
			orderDetailDto.setBuyerMemoSimple("留言:" + orderDetailDto.getBuyerMemo());
			if(orderDetailDto.getBuyerMemo().length()>5){
				orderDetailDto.setBuyerMemoSimple("留言:"+orderDetailDto.getBuyerMemo().substring(0, 5).concat("..."));
			}
		}
		orderDetailDto.setTotalPrice(order.getBigDecimal("og_pay_fee"));		//商品金额
		orderDetailDto.setPostFee(order.getBigDecimal("og_post_fee"));			//运费
		orderDetailDto.setOrderCreated(order.getDate("og_created"));
		orderDetailDto.setOrderSn(order.getStr("order_sn"));
		orderDetailDto.setReceiverProvince(order.getStr("r_province"));
		orderDetailDto.setReceiverCity(order.getStr("r_city"));
		orderDetailDto.setReceiverCountry(order.getStr("district"));
		orderDetailDto.setReceiverAddr(order.getStr("address"));
		orderDetailDto.setReceiverName(order.getStr("receiver_name"));
		orderDetailDto.setReceiverPhone(order.getStr("r_phone"));
		orderDetailDto.setZipCode(order.getStr("zip_code"));
		orderDetailDto.setTransactionId(order.getStr("og_transaction_id"));
		orderDetailDto.setOrderType(order.getInt("order_type"));
		
		if(order.getInt("group_status") == GroupStatus.grouping.ordinal())
			orderDetailDto.setGroupStatus("组团中");
		else if(order.getInt("group_status") == GroupStatus.success.ordinal())
			orderDetailDto.setGroupStatus("组团成功");
		else if(order.getInt("group_status") == GroupStatus.fail.ordinal()){
			orderDetailDto.setGroupStatus("组团失败");
		}
		
		if(order.getInt("og_payment_status") == PaymentStatus.unpaid.ordinal()){
			orderDetailDto.setOrderStatus("待支付");
		}else if(order.getInt("og_payment_status") == PaymentStatus.paid.ordinal()){
			orderDetailDto.setOrderStatus("已支付");
		}else if(order.getInt("og_payment_status") == PaymentStatus.refunded.ordinal()){
			orderDetailDto.setOrderStatus("已退款<br>"+orderDetailDto.getTransactionId());
		}
		
		if(order.getInt("shipping_status") == ShippingStatus.shipped.ordinal()){
			orderDetailDto.setOrderStatus("已发货");
		}
		
		if(order.getInt("order_status") == OrderStatus.completed.ordinal()){
			orderDetailDto.setOrderStatus("已完成");
		}else if(order.getInt("order_status") == OrderStatus.cancelled.ordinal()){
			orderDetailDto.setOrderStatus("已取消");
		}
		
		//查询订单项
		List<OrderItemResultDto> orderItemDtos = new ArrayList<OrderItemResultDto>();
		orderDetailDto.setOrderItems(orderItemDtos);
		
		OrderItemResultDto itemDto = new OrderItemResultDto();
		orderItemDtos.add(itemDto);
		itemDto.setPrice(order.getBigDecimal("og_price").toString());
		itemDto.setProductId(order.getLong("og_product_id"));
		itemDto.setProductImg(getImageDomain() + order.getStr("product_img"));
		itemDto.setProductName(order.getStr("name").length()>10?order.getStr("name").substring(0, 10).concat("..."):order.getStr("name"));
		itemDto.setQuantity(order.getInt("og_quantity"));
		itemDto.setSn(order.getStr("sn"));
		if(isReviewed(buyerId,orderDetailDto.getOrderId(),itemDto.getProductId())){
			itemDto.setIsReview(true);
		}else{
			itemDto.setIsReview(false);
		}
		
		String speciValueIds = order.getStr("spec_value");
		if(StrKit.notBlank(speciValueIds)){
			String [] orderIdArrs = speciValueIds.split(",");
			List<Long> ids = new ArrayList<Long>();
			StringBuffer condition = new StringBuffer(); 
			for(String id : orderIdArrs){
				ids.add(Long.valueOf(id));
				condition.append("?").append(",");
			}
			condition.deleteCharAt(condition.length() -1);
			List<SpecificationValue> specificationValues = specValueDao.find("select * from " + SpecificationValue.table + 
					" where id in("+condition+") ", ids.toArray());
			itemDto.setSpecificationValues(specificationValues);
			final StringBuffer valueNames = new StringBuffer();
			for(SpecificationValue specificationValue : itemDto.getSpecificationValues()){
				valueNames.append(specificationValue.getName()).append("/");
			}
			if(valueNames.length()>0) valueNames.deleteCharAt(valueNames.length()-1);
			itemDto.setSpecificationValueNames(valueNames.toString());
		}
		return orderDetailDto;
	}

	@Override
	public List<OrderResultDto> getOrders(String orderIds) throws OrderException {
		return getOrders(orderIds, null);
	} 
	
	@Override
	public List<OrderResultDto> getOrders(String orderIds, Map<String, String> statusMap) throws OrderException {
		if(StrKit.isBlank(orderIds)) throw new OrderException("调用批量获取订单详情接口参数错误");
		final String [] orderIdArrs = orderIds.split("-");
		List<Object> params = new ArrayList<Object>();
		final StringBuffer condition = new StringBuffer(); 
		for(String id : orderIdArrs){
			params.add(Long.valueOf(id));
			condition.append("?").append(",");
		}
		condition.deleteCharAt(condition.length() -1);
		
		final StringBuffer where = new StringBuffer(" where 1=1 ");
		where.append(" and o.id in ("+condition+")");
		
		if(statusMap != null){
			for(String key : statusMap.keySet()){
				setOrderStatusCondition(key, statusMap.get(key), where, params);
			}
		}
		
		List<Record> orders = Db.find(" SELECT  o.*, o.id as o_id, o.created as o_created, r.*, r.id as r_id, r.phone as r_phone, r.city as r_city, r.province as r_province, r.district as r_district, b.*, b.id as b_id FROM "+Order.table+" o "
				+ " left join " + BuyerUser.table + " b on o.buyer_id=b.id "
				+ " left join " + BuyerReceiver.table + " r on o.receiver_id=r.id "
				+ where.toString(), params.toArray());
		
		List<OrderResultDto> orderResultList = new ArrayList<OrderResultDto>();
		for(Record record : orders){
			OrderResultDto orderDetailDto = getOrderResult(record);
			orderResultList.add(orderDetailDto);
		}
		
		return orderResultList;
	}
	
	@Override
	public void batchShipping(String items) throws OrderException {
		if(StrKit.isBlank(items)) throw new OrderException("调用批量发货缺少必要参数");
		
		JSONArray jsonArray = null;
		try {
			jsonArray = JSONArray.parseArray(items);
		} catch (Exception e) {
			throw new OrderException(e.getMessage());
		}
				
		List<String> batchError = new ArrayList<String>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json = jsonArray.getJSONObject(i);
			SendGoodParamDto sendGoodParam = new SendGoodParamDto();
			sendGoodParam.setOrderId(json.getLong("orderId"));
			sendGoodParam.setBuyerId(json.getLong("buyer_id"));
			sendGoodParam.setExpKey(json.getString("exp_key"));
			sendGoodParam.setBillNumber(json.getString("bill_number"));
			try {
				shipping(sendGoodParam);
			} catch (OrderException e) {
				batchError.add("订单[" + sendGoodParam.getOrderId() + "]发货失败，原因："+e.getMessage());
			}
		}
		if(batchError.size()>0){
			StringBuffer msg = new StringBuffer();
			for(String e : batchError){
				msg.append(e);
			}
			throw new OrderException(msg.toString());
		}
	}
	
	@Override
	@Transactional(rollbackFor = OrderException.class)
	public String shipping(SendGoodParamDto sendGoodParam) throws OrderException {
		if(sendGoodParam == null || StrKit.isBlank(sendGoodParam.getExpKey()) 
				|| StrKit.isBlank(sendGoodParam.getBillNumber()) || sendGoodParam.getOrderId() == null){
			throw new OrderException("调用订单发货接口缺少必要参数");
		}
		
		Order order = orderdao.findById(sendGoodParam.getOrderId());
		if(sendGoodParam.getBuyerId()!=null){
			if(order == null || order.getGroupStatus() != GroupStatus.success.ordinal())
				throw new OrderException("只有组团成功的订单才能发货");	
		}else{
		if(order == null || order.getPaymentStatus() != PaymentStatus.paid.ordinal())
			throw new OrderException("只有支付状态的订单才能发货");
		
		if(order.getShippingStatus() == ShippingStatus.shipped.ordinal())
			throw new OrderException("订单已发货，无需重复发货");
		}
		
		order.setShippingStatus(ShippingStatus.shipped.ordinal());
		order.update();
		
		Shipping ship=new Shipping();
		ship.setOrderId(sendGoodParam.getOrderId());
		ship.setBuyerId(sendGoodParam.getBuyerId());
		ship.setExpKey(sendGoodParam.getExpKey());
		ship.setBillNumber(sendGoodParam.getBillNumber());
		ship.setActive(1);
		ship.setCreated(new Date());
		ship.setUpdated(new Date());
		
		try {
			ship.save();
		} catch (Exception e) {
			throw new OrderException(e.getMessage());
		}
		
		return order.getOrderSn();
	}

	@Override
	@Transactional(rollbackFor = OrderException.class)
	public void refund(RefundParamDto refundParam) throws OrderException {
		if(refundParam == null || refundParam.getOrderId() == null || refundParam.getRefundFee() == null)
			throw new OrderException("订单退款缺少必要参数");
		
		Order order = orderdao.findById(refundParam.getOrderId());
		if(order == null) throw new OrderException("订单不存在");
		if(order.getPaymentStatus() != PaymentStatus.paid.ordinal()) throw new OrderException("未支付订单不允许退款");
		if(order.getPaymentStatus() == PaymentStatus.refunded.ordinal()) throw new OrderException("订单已退款，不可重复退款");
		if(order.getOrderStatus() == OrderStatus.completed.ordinal()) throw new OrderException("交易成功不可退款");
		if(order.getOrderStatus() == OrderStatus.cancelled.ordinal()) throw new OrderException("已取消订单不可退款");
		
		if(refundParam.getRefundFee().compareTo(new BigDecimal(0)) !=1) throw new OrderException("退款金额少于0");
		
		order.setPaymentStatus(PaymentStatus.refunded.ordinal());
		order.update();
		
		Refund refund=new Refund();
		refund.setOrderId(order.getId());
		refund.setTransactionId(order.getTransactionId());
		refund.setCash(refundParam.getRefundFee());
		refund.setCreated(new Date());
		refund.setUpdated(new Date());
		refund.setActive(true);
		try {
			refund.save();
		} catch (Exception e) {
			throw new OrderException(e.getMessage());
		}
		//需要调用接口线上退款？？？
	}
	
	private void setOrderStatusCondition(String type, String value, StringBuffer where, List<Object> params){
		if("o".equals(type)){
			//订单状态，完成还是取消
			where.append(" and o.order_status = ?");	
			params.add(OrderStatus.valueOf(value).ordinal());
		}else if("p".equals(type)){
			//支付状态
			if(value.equals("paid")){
				//查询出已支付，未发货订单
				where.append(" and o.payment_status = ? and o.shipping_status = ? ");
				params.add(PaymentStatus.valueOf(value).ordinal());
				params.add(ShippingStatus.unshipped.ordinal());
			}else{
				where.append(" and o.payment_status = ?");
				params.add(PaymentStatus.valueOf(value).ordinal());					
			}
		}else if("s".equals(type)){
			//发货状态
			where.append(" and o.shipping_status = ?");
			params.add(ShippingStatus.valueOf(value).ordinal());
		}else if("g".equals(type)){
			//拼团状态
			where.append(" and o.group_status = ?");
			params.add(GroupStatus.valueOf(value).ordinal());
		}else {
			throw new OrderException("不支持的订单状态");
		}
	}
	
	private OrderResultDto getOrderResult(Record order){
		OrderResultDto orderDetailDto = new OrderResultDto();
		orderDetailDto.setOrderId(order.getLong("o_id"));
		orderDetailDto.setPayFee(order.getBigDecimal("pay_fee"));   			//需计算应付金额
		orderDetailDto.setBuyerNick(order.getStr("nickname"));
		if(StrKit.notBlank(order.getStr("memo"))){
			orderDetailDto.setBuyerMemo(order.getStr("memo"));
			orderDetailDto.setBuyerMemoSimple("留言:" + orderDetailDto.getBuyerMemo());
			if(orderDetailDto.getBuyerMemo().length()>5){
				orderDetailDto.setBuyerMemoSimple("留言:"+orderDetailDto.getBuyerMemo().substring(0, 5).concat("..."));
			}
		}
		orderDetailDto.setTotalPrice(order.getBigDecimal("total_price"));	//商品金额
		orderDetailDto.setPostFee(order.getBigDecimal("post_fee"));			//运费
		orderDetailDto.setOrderCreated(order.getDate("o_created"));
		orderDetailDto.setOrderSn(order.getStr("order_sn"));
		orderDetailDto.setReceiverProvince(order.getStr("r_province"));
		orderDetailDto.setReceiverCity(order.getStr("r_city"));
		if(StrKit.notBlank(order.getStr("r_district")))
			orderDetailDto.setReceiverCountry(order.getStr("r_district"));
		orderDetailDto.setReceiverAddr(order.getStr("address"));
		orderDetailDto.setReceiverName(order.getStr("receiver_name"));
		orderDetailDto.setReceiverPhone(order.getStr("r_phone"));
		orderDetailDto.setZipCode(order.getStr("zip_code"));
		orderDetailDto.setTransactionId(order.getStr("transaction_id"));
		orderDetailDto.setOrderType(order.getInt("order_type"));
		if(order.getInt("payment_status") == PaymentStatus.unpaid.ordinal()){
			orderDetailDto.setOrderStatus("待支付");
		}else if(order.getInt("payment_status") == PaymentStatus.paid.ordinal()){
			orderDetailDto.setOrderStatus("已支付");
		}else if(order.getInt("payment_status") == PaymentStatus.refunded.ordinal()){
			orderDetailDto.setOrderStatus("已退款<br>"+orderDetailDto.getTransactionId());
		}
		
		if(order.getInt("shipping_status") == ShippingStatus.shipped.ordinal()){
			orderDetailDto.setOrderStatus("已发货");
		}
		
		if(order.getInt("order_status") == OrderStatus.completed.ordinal()){
			orderDetailDto.setOrderStatus("已完成");
		}else if(order.getInt("order_status") == OrderStatus.cancelled.ordinal()){
			orderDetailDto.setOrderStatus("已取消");
		}
		
		//查询订单项
		List<OrderItemResultDto> orderItemDtos = getOrderItems(orderDetailDto.getOrderId(),null);
		orderDetailDto.setOrderItems(orderItemDtos);
		return orderDetailDto;
	}
	
	/**
	 * 订单项
	 * @param orderId
	 * @param buyerId
	 * @return
	 */
	private List<OrderItemResultDto> getOrderItems (Long orderId, Long buyerId){
		List<OrderItemResultDto> orderItemDtos = new ArrayList<OrderItemResultDto>();
		List<OrderItem> orderItems = orderItemdao.find("select * from " + OrderItem.table + " where order_id=? ", orderId);
		for(OrderItem orderItem : orderItems){
			OrderItemResultDto itemDto = new OrderItemResultDto();
			itemDto.setPrice(orderItem.getPrice().toString());
			itemDto.setProductId(orderItem.getProductId());
			itemDto.setProductImg(getImageDomain() + orderItem.getProductImg());
			itemDto.setProductName(orderItem.getName().length()>10?orderItem.getName().substring(0, 10).concat("..."):orderItem.getName());
			itemDto.setQuantity(orderItem.getQuantity());
			itemDto.setSn(orderItem.getSn());
			if(buyerId !=null){
				if(isReviewed(buyerId,orderId,orderItem.getProductId())){
					itemDto.setIsReview(true);
				}else{
					itemDto.setIsReview(false);
				}
			}
			
			String speciValueIds = orderItem.getSpecificationValue();
			if(StrKit.notBlank(speciValueIds)){
				String [] orderIdArrs = speciValueIds.split(",");
				List<Long> ids = new ArrayList<Long>();
				StringBuffer condition = new StringBuffer(); 
				for(String id : orderIdArrs){
					ids.add(Long.valueOf(id));
					condition.append("?").append(",");
				}
				condition.deleteCharAt(condition.length() -1);
				List<SpecificationValue> specificationValues = specValueDao.find("select * from " + SpecificationValue.table + 
						" where id in("+condition+") ", ids.toArray());
				itemDto.setSpecificationValues(specificationValues);
				final StringBuffer valueNames = new StringBuffer();
				for(SpecificationValue specificationValue : itemDto.getSpecificationValues()){
					valueNames.append(specificationValue.getName()).append("/");
				}
				if(valueNames.length()>0) valueNames.deleteCharAt(valueNames.length()-1);
				itemDto.setSpecificationValueNames(valueNames.toString());
				
				ProductSpecItem stock=prodSpecItemdao.findFirst(
        				"select * FROM "+ProductSpecItem.table+" WHERE product_id = ? and specification_value = ?", itemDto.getProductId(), speciValueIds);
        		if(stock != null){
	        		itemDto.setPrice(stock.getPrice().toString());
        		}
			}
			
			itemDto.setTotalPrice(new BigDecimal(itemDto.getPrice()).multiply(new BigDecimal(itemDto.getQuantity())));
			orderItemDtos.add(itemDto);
		}
		return orderItemDtos;
	}
	
	@Override
	public OrderResultDto balance(Long buyerId, Long receiverId, String items) throws OrderException {
		if(StrKit.isBlank(items)) throw new OrderException("调用结算接口缺少必要参数");

		JSONArray jsonArray = null;
		try {
			 jsonArray = JSONArray.parseArray(items);
		} catch (Exception e) {
			throw new OrderException(e.getMessage());
		}
		
		if(jsonArray==null || jsonArray.size()<=0) throw new OrderException("请选择要结算的项");
		
		BuyerReceiver receiver = receiverDao.findById(receiverId);
		if(receiverId == null){
			receiver = receiverDao.findFirst(" select * from " + BuyerReceiver.table + " where buyer_id=? and is_default=1", buyerId);
		}
		
		OrderResultDto orderDto = new OrderResultDto();
		List<OrderItemResultDto> orderItemDtos = new ArrayList<OrderItemResultDto>();
		orderDto.setOrderItems(orderItemDtos);
		for(int i=0; i<jsonArray.size(); i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			final String prodId = jsonObj.getString("productId");
			final String specivalues = jsonObj.getString("speci");
			Product product = productDao.findById(prodId);
			if(product.getIsMarketable() == true 
					&& product.getStock()!=null && product.getStock()>0){
				OrderItemResultDto orderItem = new OrderItemResultDto();
				orderItem.setProductName(product.getName());
				orderItem.setQuantity(
						product.getStock()!=null && jsonObj.getInteger("pcount")>product.getStock()
						? product.getStock()
						: jsonObj.getInteger("pcount"));
				orderItem.setPrice(product.getPrice());
				orderItem.setProductId(product.getId());
				orderItem.setSn(product.getSn());
				orderItem.setProductImg(product.getImage());
				if(StrKit.isBlank(specivalues)){
					//没有传规格值，视统一规格
					String promoPrice = promotionService.getProductPromotionPrice(product);
	    			if(StrKit.notBlank(promoPrice)) orderItem.setPrice(promoPrice);
				}else{
					JSONArray jsonArr = JSON.parseArray(specivalues);
					if(jsonArr ==null || jsonArr.size() <=0){
						//没有传规格值，视统一规格
						String promoPrice = promotionService.getProductPromotionPrice(product);
		    			if(StrKit.notBlank(promoPrice)) orderItem.setPrice(promoPrice);
					}else{
						//多规格
						final StringBuffer sfvalueBuff = new StringBuffer();
						List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>();
		        		for(int k=0;k<jsonArr.size();k++){
		        			JSONObject json = jsonArr.getJSONObject(k);
		        			Long spvid = json.getLong("spvId");
		        			sfvalueBuff.append(spvid).append(",");
		        			specificationValues.add(specValueDao.findById(spvid));
		        		}
		        		orderItem.setSpecificationValues(specificationValues);
		        		ProductSpecItem stock=prodSpecItemdao.findFirst(
		        				"select * FROM "+ProductSpecItem.table+" WHERE product_id = ? and specification_value = ?", 
		        				product.getId(), sfvalueBuff.deleteCharAt(sfvalueBuff.length()-1).toString());
		        		if(stock == null || stock.getPrice() == null){
		        			throw new OrderException("请选择完整的规格值");
		        		}
		        		orderItem.setPrice(stock.getPrice().toString());
		        		//限时折扣价
		        		String promoPrice = promotionService.getProductPromotionPrice(product, stock);
		        		if(StrKit.notBlank(promoPrice)) orderItem.setPrice(promoPrice);
					}
				}
				//订单项商品小计金额
				orderItem.setTotalPrice(new BigDecimal(orderItem.getQuantity())
						.multiply(new BigDecimal(orderItem.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP));
				orderItemDtos.add(orderItem);
				
			}
		}
		
		//商品总数量
		Integer num = 0;
		for(OrderItemResultDto orderItemDto : orderDto.getOrderItems()){
			num += orderItemDto.getQuantity();
		}
		orderDto.setNum(num);
		//订单总金额，不包含邮费
		BigDecimal totalPrice = new BigDecimal(0);
		for(OrderItemResultDto orderItemDto : orderDto.getOrderItems()){
			totalPrice = totalPrice.add(orderItemDto.getTotalPrice());
		}
		orderDto.setTotalPrice(totalPrice);
		
		//计算邮费
		BigDecimal orderPostFee = new BigDecimal(0);
		if(receiver != null){
			for(OrderItemResultDto orderItemDto : orderDto.getOrderItems()){
				Product product = productDao.findById(orderItemDto.getProductId());
				BigDecimal postFees = getDeliveryFees(product, orderDto, receiver, orderDto.getNum());
				orderPostFee = orderPostFee.add(postFees);	
			}
		}
		orderDto.setPostFee(orderPostFee);
		//最终得出订单应支付金额
		orderDto.setPayFee(orderDto.getTotalPrice().add(orderDto.getPostFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
		return orderDto;
	}
    
	/**
	 * 算商品邮费
	 * @param product
	 * @param orderDto
	 * @param buyerReceiver
	 * @param pnum （订单中商品的总数量）按件算邮费使用
	 * @return
	 */
	private BigDecimal getDeliveryFees(Product product, OrderResultDto orderDto, BuyerReceiver buyerReceiver, final int pnum) throws OrderException{
		if(product.getDeliveryType() == null || product.getDeliveryType()==0)
			//统一邮费
			return product.getDeliveryFees() == null ? new BigDecimal(0) : product.getDeliveryFees();
			
		//根据邮费模板算邮费
		BigDecimal deliveryFees = new BigDecimal(0);
		DeliveryTemplate dt = deliveryTpldao.findById(product.getDeliveryTemplateId());
		if(dt == null || dt.getActive() !=1) throw new OrderException("计算邮费出错，运费模板不存在或被删除");
		
		//找出买家收货地址id
		final String areaId=buyerReceiver.getAreaTreePath()+buyerReceiver.getAreaId();
		//找出运费模板的配置项
		List<DeliverySet> ds = deliveryDao.find("SELECT * FROM "+DeliverySet.table+"  WHERE template_id = ? and active =1", product.getDeliveryTemplateId());
		
		if(ds == null || ds.size()<=0)
			return new BigDecimal(0);
			
    	if(dt.getValuationType()==1){
    		//按商品件数计算邮费
    		for (DeliverySet deliverySet : ds) {
				String setAreaIds = deliverySet.getAreaId();
				if(contains(setAreaIds, areaId)){
					if(pnum<=deliverySet.getAddStandards()){
						//如果商品数量少于或等于模板中设置的起始值，按起始邮费算
						return deliverySet.getStartFees();
					}else{
						//如果商品数量大于起始值，计算超过的商品数量
						final int overNum = pnum - deliverySet.getStartStandards();
						//看看商品到底超过多少件
						final int count = overNum % deliverySet.getAddStandards() == 0 ? overNum/deliverySet.getAddStandards() : overNum/deliverySet.getAddStandards() + 1;
						return deliverySet.getStartFees().add(new BigDecimal(count).multiply(deliverySet.getAddFees()));
					}
				}
			}	
    	}else if(dt.getValuationType()==2){
    		//按商品物流重量算邮费
    		//1.获取规格
    		List<SpecificationValue> specificationValues = null;
    		List<OrderItemResultDto> orderItems = orderDto.getOrderItems();
    		for(OrderItemResultDto orderItem : orderItems){
    			if(orderItem.getProductId() == product.getId()){
    				specificationValues = orderItem.getSpecificationValues();
    				break;
    			}
    		}
    		
    		if(specificationValues == null) throw new OrderException("计算邮费出错，找不到对应的规格值");
    		
    		StringBuffer specifValues = new StringBuffer();
    		for(SpecificationValue sv : specificationValues){
    			specifValues.append(sv).append(",");
    		}
    		
    		ProductSpecItem productStock = prodSpecItemdao.findFirst(
    				"select * from " + ProductSpecItem.table + " where product_id=? and specification_value=? ",
    				product.getId(), specifValues.deleteCharAt(specifValues.length()-1).toString());
    		
    		if(productStock == null) throw new OrderException("计算邮费出错，找不到对应规格设置的物流重量值");
    		
    		final int weight = productStock.getWeight().intValue();//物流重量
    		for (DeliverySet deliverySet : ds) {
    			String setAreaIds = deliverySet.getAreaId();
    			if(contains(setAreaIds, areaId)){
    				if(weight<=deliverySet.getAddStandards()){
						//如果商品数量少于或等于模板中设置的起始值，按起始邮费算
						return deliverySet.getStartFees();
					}else{
						//如果商品数量大于起始值，计算超过的重量
						final int overNum = weight - deliverySet.getStartStandards();
						//看看商品到底超过多少重量
						final int count = overNum % deliverySet.getAddStandards() == 0 ? overNum/deliverySet.getAddStandards() : overNum/deliverySet.getAddStandards() + 1;
						return deliverySet.getStartFees().add(new BigDecimal(count).multiply(deliverySet.getAddFees()));
					}
    			}
			}
    	}
		return deliveryFees;
	}
	
	/**
	 * 判断邮件模板中设置的地址是否包含买家的收货地址
	 * @param areaIdsets
	 * @param areaIds
	 * @return
	 */
	private boolean contains(String areaIdsets, String areaIds){
		for(String areaIdSet : areaIdsets.split(",")){
			for(String areaId : areaIds.split(",")){
				if(areaIdSet.equals(areaId)) return true;
			}
		}
		return false;
	}

	public Boolean isReviewed(Long buyerId, Long orderId, Long productId) throws OrderException {
		String sql = "SELECT count(*) FROM `t_product_review` review WHERE review.buyer_id = ? AND review.product_id = ? AND review.order_id= ?";
		Long count =Db.queryLong(sql,buyerId,productId,orderId);
		return count > 0;
	}

	@Override
	public void cancel(Long orderId) throws OrderException {
		if(orderId == null) throw new OrderException("取消订单缺少必要参数");
		Order order = orderdao.findById(orderId);
		if(order == null || order.getPaymentStatus() != PaymentStatus.unpaid.ordinal()) throw new OrderException("该订单不能取消，或已支付");
		order.setOrderStatus(OrderStatus.cancelled.ordinal());
		order.update();
	}
	
	@Override
	public void confirm(Long orderId) throws OrderException {
		if(orderId == null) throw new OrderException("取消订单缺少必要参数");
		Order order = orderdao.findById(orderId);
		if(order == null 
				|| order.getPaymentStatus() != PaymentStatus.paid.ordinal() 
				|| order.getShippingStatus() != ShippingStatus.shipped.ordinal()) 
			throw new OrderException("该订单不能确认收货");
		order.setOrderStatus(OrderStatus.completed.ordinal());//确认收货视为交易成功
		order.update();		
	}
	
	@Override
	@Transactional(rollbackFor = OrderException.class)
	public void cancel(Long orderId, Long buyerId) throws OrderException {
		if(orderId == null) throw new OrderException("取消拼团订单缺少必要参数");
		Order order = orderdao.findById(orderId);
		if(order == null) throw new OrderException("订单不存在，id：" + orderId);
		if(order.getOrderType() != OrderType.pintuan.ordinal()){
			cancel(orderId);
			return;
		}
		
		//取消拼团订单
		if(buyerId == null) throw new OrderException("取消拼团订单缺少必要buyerId");
		
		if(order.getGroupStatus() != GroupStatus.grouping.ordinal()) throw new OrderException("组团中的订单才可取消");
		
		//团数据
		OrderGheader ogh = orderGheaderdao.findFirst("select * from " + OrderGheader.table + " where order_id=? and active=1", orderId);
		if(ogh == null) throw new OrderException("取消拼团订单失败，拼团数据异常");
		
		OrderGuser oguser = orderGuserdao.findFirst("select * from " + OrderGuser.table 
				+ " where gheader_id=? and buyer_id=? ", ogh.getId(), buyerId);
		if(oguser == null || oguser.getPaymentStatus() != PaymentStatus.unpaid.ordinal()) {
			throw new OrderException("该订单不可取消，或已支付");
		}
		
		if(order.getBuyerId() == buyerId){
			//如果是团长取消订单的情况
			List<OrderGuser> ogusers = orderGuserdao.find("select * from " + OrderGuser.table + " where gheader_id=? ", ogh.getId());
			for(OrderGuser og : ogusers){
				if(og.getPaymentStatus() == PaymentStatus.paid.ordinal()) 
					throw new OrderException("自己的团，并且有成员已支付，不可取消"); 
			}
			
			for(OrderGuser og : ogusers){
				og.delete();
			}
			
			order.setOrderStatus(OrderStatus.cancelled.ordinal());
			try {
				ogh.delete();
				order.update();	
			} catch (Exception e) {
				throw new OrderException(e.getMessage());
			}
		}else{
			oguser.delete();//直接物理删除该数据，只删除团成员拼团数据
		}
	}
	
	@Override
	public OrderPayResultDto getOrderPayInfo(Long orderId, Long userId) throws OrderException {
		if(orderId == null) throw new OrderException("获取订单缺少必要参数");
		
		Order order = orderdao.findById(orderId);
		if(order == null || order.getPaymentStatus() == PaymentStatus.paid.ordinal())
			throw new OrderException("订单不存在或已支付");
		
		OrderPayResultDto orderPayResultDto = new OrderPayResultDto();
		orderPayResultDto.setOrderSn(order.getOrderSn());
		orderPayResultDto.setPayFee(order.getPayFee());
		orderPayResultDto.setTradeNo(order.getTradeNo());
		
		if(order.getOrderType() == OrderType.pintuan.ordinal()){
			//一个订单只有一条开团数据
			OrderGheader gheader = orderGheaderdao.findFirst(" select * from " + OrderGheader.table + " where order_id=? and active=1 ", orderId);
			if(gheader == null) throw new OrderException("获取订单支付信息失败，没有找到开团数据");
			
			/*List<OrderItem> orderItems = OrderItem.dao.find("select * from " + OrderItem.table + " where order_id=? ", orderId);
			if(orderItems == null || orderItems.size() <=0) throw new OrderException("订单数据异常");*/
			
			OrderGuser guser = orderGuserdao.findFirst("select * from " + OrderGuser.table 
					+ " where gheader_id=? and buyer_id=? ", gheader.getId(), userId);
			if(guser == null) throw new OrderException("获取订单支付信息失败，用户参团数据不存在");
			
			orderPayResultDto.setPayFee(guser.getPayFee());
			orderPayResultDto.setTradeNo(guser.getTradeNo());
		} 
		
		return orderPayResultDto;
	}

}
