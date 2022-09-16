package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.Order;
import com.fastcms.extension.IndexDataExtension;
import com.fastcms.mapper.OrderMapper;
import com.fastcms.service.IOrderService;
import org.pf4j.Extension;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    final static String INDEX_ORDER_STAT_DATA_KEY = "orderStatData";

    @Override
    public Page<OrderListVo> pageOrder(Page pageParam, QueryWrapper queryWrapper) {
        return getBaseMapper().pageOrder(pageParam, queryWrapper);
    }

    @Override
    public OrderDetailVo getOrderDetail(Long orderId) {
        OrderDetailVo orderDetail = getBaseMapper().getOrderDetail(orderId);
        return orderDetail;
    }

    @Override
    public OrderStatVo getOrderStatData() {
        return getBaseMapper().getOrderStatData();
    }

    @Override
    public List<Order> getUnPayOrderByLimitTime(int time) {
        return getBaseMapper().getUnPayOrderByLimitTime(time);
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> result = new HashMap<>();
        result.put(INDEX_ORDER_STAT_DATA_KEY, getOrderStatData());
        return result;
    }

}
