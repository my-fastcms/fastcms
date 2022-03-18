package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.entity.Order;
import com.fastcms.service.IOrderService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表 Mapper 接口
 * @author wjun_java@163.com
 * @since 2021-12-21
 */
public interface OrderMapper extends BaseMapper<Order> {

    Page<IOrderService.OrderListVo> pageOrder(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    IOrderService.OrderCountVo getUCenterOrderCount(Long userId);

    IOrderService.OrderDetailVo getOrderDetail(Long orderId);

    IOrderService.OrderStatVo getOrderStatData();

    List<Order> getUnPayOrderByLimitTime(@Param("overTime") int overTime);

}
