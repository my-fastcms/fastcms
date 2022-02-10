package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.Order;
import com.fastcms.entity.PaymentRecord;
import com.fastcms.extension.IndexDataExtension;
import com.fastcms.mapper.OrderMapper;
import com.fastcms.service.IOrderService;
import com.fastcms.service.IPaymentRecordService;
import org.pf4j.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-12-21
 */
@Service
@Extension
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService, IndexDataExtension {

    @Autowired
    private IPaymentRecordService paymentRecordService;

    @Override
    public Page<OrderVo> pageOrder(Page pageParam, QueryWrapper queryWrapper) {
        return getBaseMapper().pageOrder(pageParam, queryWrapper);
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
    public OrderVo getOrderDetail(Long orderId) {
        OrderVo orderDetail = getBaseMapper().getOrderDetail(orderId);
        return orderDetail;
    }

    @Override
    public OrderStatVo getOrderStatData() {
        return getBaseMapper().getOrderStatData();
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> result = new HashMap<>();
        result.put("orderStatData", getOrderStatData());
        return result;
    }
}
