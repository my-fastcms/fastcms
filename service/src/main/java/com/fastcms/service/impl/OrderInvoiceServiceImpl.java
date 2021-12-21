package com.fastcms.service.impl;

import com.fastcms.entity.OrderInvoice;
import com.fastcms.mapper.OrderInvoiceMapper;
import com.fastcms.service.IOrderInvoiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票信息表 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-12-21
 */
@Service
public class OrderInvoiceServiceImpl extends ServiceImpl<OrderInvoiceMapper, OrderInvoice> implements IOrderInvoiceService {

}
