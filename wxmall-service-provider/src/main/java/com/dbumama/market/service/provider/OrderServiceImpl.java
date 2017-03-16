package com.dbumama.market.service.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.AuthUser;
import com.dbumama.market.model.BuyerReceiver;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Cart;
import com.dbumama.market.model.DeliverySet;
import com.dbumama.market.model.DeliveryTemplate;
import com.dbumama.market.model.Order;
import com.dbumama.market.model.OrderItem;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductSpecItem;
import com.dbumama.market.model.Shipping;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.authuser.AuthUserService;
import com.dbumama.market.service.api.order.OrderCreateParamDto;
import com.dbumama.market.service.api.order.OrderException;
import com.dbumama.market.service.api.order.OrderItemResultDto;
import com.dbumama.market.service.api.order.OrderListParamDto;
import com.dbumama.market.service.api.order.OrderMobileResultDto;
import com.dbumama.market.service.api.order.OrderResultDto;
import com.dbumama.market.service.api.order.OrderService;
import com.dbumama.market.service.api.product.ProductFullCutResultDto;
import com.dbumama.market.service.api.serinum.SerinumService;
import com.dbumama.market.service.api.ump.PromotionService;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.enmu.OrderStatus;
import com.dbumama.market.service.enmu.PaymentStatus;
import com.dbumama.market.service.enmu.ShippingStatus;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.TemplateData;
import com.weixin.sdk.api.TemplateMsgApi;

@Service("orderService")
public class OrderServiceImpl extends AbstractServiceImpl implements OrderService{

	@Autowired
	private AuthUserService authUserService;
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private SerinumService serinumService;
	
	@Override
	public Page<OrderResultDto> list(OrderListParamDto orderParamDto) throws OrderException {
		if(orderParamDto == null || orderParamDto.getSellerId() == null)
			throw new OrderException("查询订单参数错误");
		
		String select = " SELECT  o.*, o.id as o_id, o.created as o_created, b.id as b_id, b.nickname, b.open_id, r.*, r.phone as r_phone, r.city as r_city, r.province as r_province ";
		String sqlExceptSelect = " FROM "+Order.table+" o "
				+ " left join " + BuyerUser.table + " b on o.buyer_id=b.id "
				+ " left join " + BuyerReceiver.table + " r on o.receiver_id=r.id "
				+ " where 1=1 ";
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer where = new StringBuffer();
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
		sqlExceptSelect = sqlExceptSelect + " " + where.toString() + " order by o.created desc ";
		Page<Record> orders = Db.paginate(orderParamDto.getPageNo(), orderParamDto.getPageSize(), select, sqlExceptSelect, params.toArray());
		List<OrderResultDto> orderList = new ArrayList<OrderResultDto>();
		for(Record order : orders.getList()){
			OrderResultDto orderListResultDto = getOrderResult(order);
			orderList.add(orderListResultDto);
		}
		
		return new Page<OrderResultDto>(orderList, orderParamDto.getPageNo(), orderParamDto.getPageSize(), orders.getTotalPage(), orders.getTotalRow());
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
		order.setOrderStatus(OrderStatus.unconfirmed.ordinal());
		order.setPaymentStatus(PaymentStatus.unpaid.ordinal());
		order.setShippingStatus(ShippingStatus.unshipped.ordinal());
		order.setMemo(orderParamDto.getMemo());
		order.setInvoiceTitle(orderParamDto.getInvoiceTitle());
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
			Product product = Product.dao.findById(orderItemDto.getProductId());
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
			Cart c = Cart.dao.findFirst(" select * from " + Cart.table + " where buyer_id=? and product_id=? ", orderParamDto.getBuyerId(), tempOrderItem.getProductId());
			if(c!=null) c.delete();
		}
		
		//发送模板消息
		new SendTemplateMsgThread(order).start();
		
		return order.getId();
	}
	
	class SendTemplateMsgThread extends Thread{
		
		private Order order;
		
		public SendTemplateMsgThread(Order order){
			this.order = order;
		}
		
		@Override
		public void run() {
			AuthUser authUser = authUserService.getUsedAuthUser(order.getSellerId());
			if(authUser == null) {
				logger.error("系统异常，公众账号不存在");
				return;	
			}
			//购买者
			BuyerUser buyerUser = BuyerUser.dao.findById(order.getBuyerId());
			if(buyerUser == null){
				logger.error("订单数据异常，找不到购买者");
				return;
			}
			
			//关注过公众号才推送消息
			if(buyerUser.getSubscribe() == null || buyerUser.getSubscribe() != 1) {
				logger.error("该用户["+buyerUser.getOpenId()+"]未关注公众号，不能收到消息");
				return;	
			}
			
			//获取模板
			JSONObject json = new JSONObject();
			json.put("template_id_short", "TM00016");
			String templateId = TemplateMsgApi.getTemplateId(json.toString());
			if(StrKit.isBlank(templateId)) return;

			TemplateData templateData = TemplateData.New().setTemplate_id(templateId)
					.setTouser(buyerUser.getOpenId())
					.setUrl("http://zhianjian.dbumama.com/order/detail/"+order.getId())
					.add("first", "[" + buyerUser.getNickname() + "]您好，我们已为您创建订单，并分配商品库存，请及时支付", "#173177")
					.add("orderID", order.getOrderSn(), "#173177")
					.add("orderMoneySum", order.getTotalPrice() + "，邮费：" + order.getPostFee(), "#173177")
					/*.add("backupFieldName", "", "#173177")
					.add("backupFieldData", "", "#173177")*/
					.add("remark", "温馨提示：未支付订单系统将在24小时内取消", "#173177");
			ApiResult apiResult = TemplateMsgApi.send(templateData.build());
			if(!apiResult.isSucceed()){
				logger.error("error_code:" + apiResult.getErrorCode() + ",error_msg" + apiResult.getErrorMsg());
			}
		}
	}

	@Override
	public List<OrderMobileResultDto> list4Mobile(OrderListParamDto orderParamDto) throws OrderException {
		if(orderParamDto == null || orderParamDto.getSellerId() == null || orderParamDto.getBuyerId() == null)
			throw new OrderException("调用手机端订单列表数据接口缺少参数");
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer where = new StringBuffer();
		where.append(" and seller_id=? ");
		params.add(orderParamDto.getSellerId());
		where.append(" and buyer_id=? ");
		params.add(orderParamDto.getBuyerId());
		if(StrKit.notBlank(orderParamDto.getPaymentStatus())){
			where.append(" and payment_status=?");
			params.add(PaymentStatus.valueOf(orderParamDto.getPaymentStatus()).ordinal());
		}
		
		if(StrKit.notBlank(orderParamDto.getShippingStatus())){
			where.append(" and shipping_status=? ");
			params.add(ShippingStatus.valueOf(orderParamDto.getShippingStatus()).ordinal());
		}
		
		if(StrKit.notBlank(orderParamDto.getOrderStatus())){
			where.append(" and order_status=? ");
			params.add(OrderStatus.valueOf(orderParamDto.getOrderStatus()).ordinal());
		}
		
		List<OrderMobileResultDto> results = new ArrayList<OrderMobileResultDto>();
		String select = " SELECT  * ";
		String sqlExceptSelect = " FROM "+Order.table + " where 1=1 " + where.toString() + " order by created desc ";
		Page<Order> orders = Order.dao.paginate(orderParamDto.getPageNo(), orderParamDto.getPageSize(), select, sqlExceptSelect, params.toArray());

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
		Record order = Db.findFirst(" SELECT  o.*, o.id as o_id, o.created as o_created, r.*, r.id as r_id, r.phone as r_phone, r.city as r_city, r.province as r_province FROM "+Order.table+" o "
				+ " left join " + BuyerReceiver.table + " r on o.receiver_id=r.id"
				+ " where o.id=?", orderId);
		return getOrderResult(order);
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
		
		List<Record> orders = Db.find(" SELECT  o.*, o.id as o_id, o.created as o_created, r.*, r.id as r_id, r.phone as r_phone, r.city as r_city, r.province as r_province, b.*, b.id as b_id FROM "+Order.table+" o "
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
	public void saveShipping(String items) throws OrderException {
		JSONArray jsonArray = JSONArray.parseArray(items);
		for(int i=0;i<jsonArray.size();i++){
			
			JSONObject json = jsonArray.getJSONObject(i);
			Shipping shop=new Shipping();
			shop.set("order_id", json.getString("orderId"));
			shop.set("exp_key", json.getString("exp_key"));
			shop.set("bill_number", json.getString("bill_number"));
			shop.set("active", 1);
			shop.set("created", new Date());
			shop.set("updated", new Date());
			try{
				shop.save();
				Order order=Order.dao.findById(json.getString("orderId"));
				if(order.getShippingStatus() == ShippingStatus.unshipped.ordinal()){
					order.setShippingStatus(ShippingStatus.shipped.ordinal());
					order.update();					
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new OrderException("系统异常，保存出错");
			}
		}
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
		}
		orderDetailDto.setTotalPrice(order.getBigDecimal("total_price"));	//商品金额
		orderDetailDto.setPostFee(order.getBigDecimal("post_fee"));			//运费
		orderDetailDto.setOrderCreated(order.getDate("o_created"));
		orderDetailDto.setOrderSn(order.getStr("order_sn"));
		orderDetailDto.setReceiverProvince(order.getStr("r_province"));
		orderDetailDto.setReceiverCity(order.getStr("r_city"));
		orderDetailDto.setReceiverCountry(order.getStr("district"));
		orderDetailDto.setReceiverAddr(order.getStr("address"));
		orderDetailDto.setReceiverName(order.getStr("receiver_name"));
		orderDetailDto.setReceiverPhone(order.getStr("r_phone"));
		orderDetailDto.setZipCode(order.getStr("zip_code"));
		orderDetailDto.setTransactionId(order.getStr("transaction_id"));
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
	
	private List<OrderItemResultDto> getOrderItems (Long orderId,Long buyerId){
		List<OrderItemResultDto> orderItemDtos = new ArrayList<OrderItemResultDto>();
		List<OrderItem> orderItems = OrderItem.dao.find("select * from " + OrderItem.table + " where order_id=? ", orderId);
		for(OrderItem orderItem : orderItems){
			OrderItemResultDto itemDto = new OrderItemResultDto();
			itemDto.setPrice(orderItem.getPrice().toString());
			itemDto.setProductId(orderItem.getProductId());
			itemDto.setProductImg(getImageDomain() + orderItem.getProductImg());
			itemDto.setProductName(orderItem.getName());
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
				List<SpecificationValue> specificationValues = SpecificationValue.dao.find("select * from " + SpecificationValue.table + 
						" where id in("+condition+") ", ids.toArray());
				itemDto.setSpecificationValues(specificationValues);
				final StringBuffer valueNames = new StringBuffer();
				for(SpecificationValue specificationValue : itemDto.getSpecificationValues()){
					valueNames.append(specificationValue.getName()).append("/");
				}
				if(valueNames.length()>0) valueNames.deleteCharAt(valueNames.length()-1);
				itemDto.setSpecificationValueNames(valueNames.toString());
				
				ProductSpecItem stock=ProductSpecItem.dao.findFirst(
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
		if(StrKit.isBlank(items) || buyerId == null) throw new OrderException("调用结算接口参数异常");

		JSONArray jsonArray = null;
		try {
			 jsonArray = JSONArray.parseArray(items);
		} catch (Exception e) {
			throw new OrderException(e.getMessage());
		}
		
		if(jsonArray==null || jsonArray.size()<=0) throw new OrderException("请选择要结算的项");
		
		BuyerReceiver receiver = null;
		if(receiverId == null){
			receiver = BuyerReceiver.dao.findFirst(" select * from " + BuyerReceiver.table + " where buyer_id=? and is_default=1", buyerId);
		}else{
			receiver = BuyerReceiver.dao.findById(receiverId);
		}
		//if(receiver == null) throw new OrderException("结算订单出错，收货地址不存在");  首次购买没有收货地址，同样需要结算，此时不需要算运费
		
		OrderResultDto orderDto = new OrderResultDto();
		List<OrderItemResultDto> orderItemDtos = new ArrayList<OrderItemResultDto>();
		orderDto.setOrderItems(orderItemDtos);
		for(int i=0; i<jsonArray.size(); i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			final String prodId = jsonObj.getString("productId");
			final String specivalues = jsonObj.getString("speci");
			Product product = Product.dao.findById(prodId);
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
					//多规格
					final StringBuffer sfvalueBuff = new StringBuffer();
					List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>();
	        		JSONArray jsonArr = JSON.parseArray(specivalues);
	        		for(int k=0;k<jsonArr.size();k++){
	        			JSONObject json = jsonArr.getJSONObject(k);
	        			Long spvid = json.getLong("spvId");
	        			sfvalueBuff.append(spvid).append(",");
	        			specificationValues.add(SpecificationValue.dao.findById(spvid));
	        		}
	        		orderItem.setSpecificationValues(specificationValues);
	        		ProductSpecItem stock=ProductSpecItem.dao.findFirst(
	        				"select * FROM "+ProductSpecItem.table+" WHERE product_id = ? and specification_value = ?", 
	        				product.getId(), sfvalueBuff.deleteCharAt(sfvalueBuff.length()-1).toString());
	        		if(stock != null && stock.getPrice() != null){
	        			orderItem.setPrice(stock.getPrice().toString());
	        		}
	        		//限时折扣价
	        		String promoPrice = promotionService.getProductPromotionPrice(product, stock);
	        		if(StrKit.notBlank(promoPrice)) orderItem.setPrice(promoPrice);
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
				Product product = Product.dao.findById(orderItemDto.getProductId());
				BigDecimal postFees = getDeliveryFees(product, orderDto, receiver, orderDto.getNum());
				orderPostFee = orderPostFee.add(postFees);	
			}
		}
		orderDto.setPostFee(orderPostFee);
		//计算满减
		setFullCut(orderDto);
		//最终得出订单应支付金额
		orderDto.setPayFee(orderDto.getTotalPrice().add(orderDto.getPostFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
		return orderDto;
	}
	
	/**
	 * 计算订单是否包含满减商品
	 * @param orderDto
	 */
	private void setFullCut(OrderResultDto orderDto){
		//该订单的满减数据，算法是：把订单中所有商品的满减设置数据找出来，
		//然后从小到大进行排序，最后把订单总金额跟集合中的满减数据一一比较，从小比到大，直到找到符合条件的满减数据
		List<ProductFullCutResultDto> orderFullCutDtos = new ArrayList<ProductFullCutResultDto>();
		for(OrderItemResultDto orderItemDto : orderDto.getOrderItems()){
			if(orderItemDto.getFullCutDtos() != null && orderItemDto.getFullCutDtos().size()>0){
				orderFullCutDtos.addAll(orderItemDto.getFullCutDtos());
			}
		}
		/**
		 * 按满减的金额进行升序排列
		 */
		Collections.sort(orderFullCutDtos, new Comparator<ProductFullCutResultDto>(){

			@Override
			public int compare(ProductFullCutResultDto o1, ProductFullCutResultDto o2) {
				return o1.getMeet().subtract(o2.getMeet()).intValue();
			}
			
		});
		BigDecimal fullCutTotalPrice = new BigDecimal(0);
		for(ProductFullCutResultDto fullCut : orderFullCutDtos){
			if(orderDto.getTotalPrice().compareTo(fullCut.getMeet()) != -1){//一个个比，从小比到大
				if(fullCut.getCash() != null){
					fullCutTotalPrice = orderDto.getTotalPrice().subtract(fullCut.getCash()).setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				if(fullCut.getPostage() == 1){
					//说明包邮
					if(orderDto.getPostFee().compareTo(new BigDecimal(0)) ==1){
						orderDto.setOldPostFee(orderDto.getPostFee());
						orderDto.setPostFee(new BigDecimal(0));
					}
				}
			}
		}
		
		//说明有满减价格
		if(fullCutTotalPrice.compareTo(new BigDecimal(0)) ==1){
			orderDto.setOldPrice(orderDto.getTotalPrice());
			orderDto.setTotalPrice(fullCutTotalPrice);
		}
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
		DeliveryTemplate dt = DeliveryTemplate.dao.findById(product.getDeliveryTemplateId());
		if(dt == null || dt.getActive() !=1) throw new OrderException("计算邮费出错，运费模板不存在或被删除");
		
		//找出买家收货地址id
		final String areaId=buyerReceiver.getAreaTreePath()+buyerReceiver.getAreaId();
		//找出运费模板的配置项
		List<DeliverySet> ds = DeliverySet.dao.find("SELECT * FROM "+DeliverySet.table+"  WHERE template_id = ? and active =1", product.getDeliveryTemplateId());
		if(ds == null || ds.size()<=0) throw new OrderException("计算邮费出错，运费模板没有配置项");
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
    		
    		ProductSpecItem productStock = ProductSpecItem.dao.findFirst(
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

}
