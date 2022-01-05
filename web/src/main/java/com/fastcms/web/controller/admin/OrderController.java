package com.fastcms.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
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
     * @param page
     * @param orderSn
     * @param title
     * @param status
     * @return
     */
    @GetMapping("list")
    public RestResult<Page<IOrderService.OrderVo>> list(PageModel page,
                                                        @RequestParam(name = "orderSn", required = false) String orderSn,
                                                        @RequestParam(name = "title", required = false) String title,
                                                        @RequestParam(name = "status", required = false) String status) {
        QueryWrapper queryWrapper = Wrappers.query().eq(StrUtils.isNotBlank(orderSn), "o.order_sn", orderSn)
                .eq(!AuthUtils.isAdmin(), "o.user_id", AuthUtils.getUserId())
                .eq(StrUtils.isNotBlank(status), "o.pay_status", status)
                .like(StrUtils.isNotBlank(title), "o.order_title", title)
                .orderByDesc("o.created");
        return RestResultUtils.success(orderService.pageOrder(page.toPage(), queryWrapper));
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @GetMapping("detail/{orderId}")
    public RestResult<IOrderService.OrderVo> detail(@PathVariable(name = "orderId") Long orderId) {
        return RestResultUtils.success(orderService.getOrderDetail(orderId));
    }

}
