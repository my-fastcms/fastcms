package com.fastcms.service.impl;

import com.fastcms.entity.OrderItem;
import com.fastcms.mapper.OrderItemMapper;
import com.fastcms.service.IOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-12-21
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
