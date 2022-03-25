package com.fastcms.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.auth.ActionTypes;
import com.fastcms.core.auth.AuthConstants;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.auth.Secured;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.entity.Order;
import com.fastcms.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理
 * @author： wjun_java@163.com
 * @date： 2021/10/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 订单列表
     * @param page      分页
     * @param orderSn   订单号
     * @param title     商品标题
     * @param status    订单状态
     * @param payStatus 支付状态
     * @return
     */
    @GetMapping("list")
    @Secured(resource = AuthConstants.ADMIN_RESOURCE_NAME_PREFIX + "orders", action = ActionTypes.READ)
    public RestResult<Page<IOrderService.OrderListVo>> list(PageModel page,
                                                        @RequestParam(name = "orderSn", required = false) String orderSn,
                                                        @RequestParam(name = "title", required = false) String title,
                                                        @RequestParam(name = "payStatus", required = false) String payStatus,
                                                        @RequestParam(name = "tradeStatus", required = false) String tradeStatus,
                                                        @RequestParam(name = "status", required = false) String status) {
        QueryWrapper queryWrapper = Wrappers.query().eq(StrUtils.isNotBlank(orderSn), "o.order_sn", orderSn)
                .eq(!AuthUtils.isAdmin(), "o.user_id", AuthUtils.getUserId())
                .like(StrUtils.isNotBlank(title), "o.order_title", title)
                .eq(StrUtils.isNotBlank(status), "o.status", status)
                .eq(StrUtils.isNotBlank(payStatus), "o.pay_status", payStatus)
                .eq(StrUtils.isNotBlank(tradeStatus), "o.trade_status", tradeStatus)
                .orderByDesc("o.created");
        return RestResultUtils.success(orderService.pageOrder(page.toPage(), queryWrapper));
    }

    /**
     * 订单详情
     * @param orderId   订单号
     * @return
     */
    @GetMapping("detail/{orderId}")
    @Secured(resource = AuthConstants.ADMIN_RESOURCE_NAME_PREFIX + "orders", action = ActionTypes.READ)
    public RestResult<IOrderService.OrderDetailVo> detail(@PathVariable(name = "orderId") Long orderId) {
        return RestResultUtils.success(orderService.getOrderDetail(orderId));
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @PostMapping("delete/{orderId}")
    @Secured(resource = AuthConstants.ADMIN_RESOURCE_NAME_PREFIX + "articles", action = ActionTypes.WRITE)
    public Object deleteOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getById(orderId);
        if(order != null) {
            order.setStatus(Order.ORDER_STATUS_DEL);
            orderService.updateById(order);
        }
        return RestResultUtils.success();
    }

}
