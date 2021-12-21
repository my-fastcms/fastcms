package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.SnowFlake;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.Order;
import com.fastcms.entity.OrderItem;
import com.fastcms.entity.PaymentRecord;
import com.fastcms.mapper.OrderMapper;
import com.fastcms.service.IOrderItemService;
import com.fastcms.service.IOrderService;
import com.fastcms.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-12-21
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private static final SnowFlake SNOW_FLAKE = new SnowFlake(1, 1);

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IPaymentRecordService paymentRecordService;

    public static String getOrderSN() {
        return String.valueOf(SNOW_FLAKE.genNextId());
    }

    @Override
    public Page<OrderVo> pageOrder(Page pageParam, QueryWrapper queryWrapper) {
        return getBaseMapper().pageOrder(pageParam, queryWrapper);
    }

    @Override
    @Transactional
    public Long createOrder(CreateOrderParam createOrderParam) throws Exception {

        if(createOrderParam.getUserId() == null) throw new FastcmsException(FastcmsException.INVALID_PARAM, "user id is null");

        //订单项
        List<OrderItem> orderItemList = new ArrayList<>();

        List<ProductParam> products = createOrderParam.getProducts();
        if(products == null || products.size()<=0) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "未找到需购买商品项");
        }

//        Product firstProduct = null; //第一个商品名称作为订单标题
        for (ProductParam item : products) {
//            if(firstProduct == null) {
//                firstProduct = productService.getById(item.getId());
//            }
            Long num = item.getNum();
//            BigDecimal productPrice = getProductPrice(item); //获取商品单价
//            BigDecimal postageAmount = postageTemplateService.getPostageAmount(item, address.getAddress());
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getId());
            orderItem.setProductCount(num.intValue());
//            orderItem.setPostageCost(postageAmount);
//            orderItem.setTotalAmount(new BigDecimal(num).multiply(productPrice));
//            orderItem.setSku(item.getSku());
            orderItemList.add(orderItem);
        }

        Order order = new Order();
        order.setUserId(createOrderParam.getUserId());
        order.setOrderSn(getOrderSN());
        order.setOrderAmount(orderItemList.stream().map(OrderItem::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_UP));
        order.setPayStatus(Order.STATUS_PAY_PRE);
        order.setBuyerMsg(createOrderParam.getBuyerMsg());
//        order.setOrderTitle(firstProduct.getTitle());


        order.setInvoiceStatus(Order.INVOICE_STATUS_NOT_APPLY);

        //计算邮费
        order.setPostageAmount(orderItemList.stream().map(OrderItem::getPostageCost).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_UP));

        order.setPayAmount(order.getOrderAmount().add(order.getPostageAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));

        order.setTradeStatus(Order.TRADE_STATUS_TRADING);
        order.setStatus(Order.ORDER_STATUS_NORMAL);
        save(order);

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
//            Product product = productService.getById(orderItem.getProductId());
//            orderItem.setSellerId(product.getUserId());
        }

        orderItemService.saveBatch(orderItemList);

        return order.getId();
    }

    @Override
    @Transactional
    public PaymentRecord preparePaymentRecord(Order order, String openid, String ip) {
        PaymentRecord paymentRecord = paymentRecordService.getById(order.getPaymentId());
        if(paymentRecord == null) {
            paymentRecord = new PaymentRecord();
            paymentRecord.setTrxNo(StrUtils.uuid());
            paymentRecord.setTrxType(PaymentRecord.TRX_TYPE_ORDER);
            paymentRecord.setTrxNonceStr(StrUtils.uuid());
//            paymentRecord.setPayType(order);
            paymentRecord.setOrderIp(ip);
            paymentRecord.setPayStatus(Order.STATUS_PAY_PRE);
            paymentRecord.setProductRelativeId(order.getId());
            paymentRecord.setPayAmount(order.getPayAmount());
            paymentRecord.setPayerFee(BigDecimal.ZERO);
            paymentRecord.setPaySuccessAmount(order.getPayAmount());
            paymentRecord.setUserId(order.getUserId());
            paymentRecord.setPaySuccessTime(LocalDateTime.now());
            paymentRecord.setThirdpartyUserOpenid(openid);
            paymentRecordService.save(paymentRecord);
            order.setPaymentId(paymentRecord.getId());
            updateById(order);
        }
        return paymentRecord;
    }

    @Override
    public OrderCountVo getUCenterOrderCount(Long userId) {
        return getBaseMapper().getUCenterOrderCount(userId);
    }

    @Override
    public Page<Order> pageOrderOfApi(Page pageParam, QueryWrapper queryWrapper) {
        Page<Order> orderPage = getBaseMapper().pageOrderOfApi(pageParam, queryWrapper);
        return orderPage;
    }

    @Override
    public OrderVo getOrderDetail(Long orderId) {
        OrderVo orderDetail = getBaseMapper().getOrderDetail(orderId);
        return orderDetail;
    }

}
