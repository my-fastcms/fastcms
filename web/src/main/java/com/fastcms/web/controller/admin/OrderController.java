package com.fastcms.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.entity.Order;
import com.fastcms.entity.UserAmountPayout;
import com.fastcms.service.IOrderService;
import com.fastcms.service.IPaymentRecordService;
import com.fastcms.service.IUserAmountPayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fastcms.service.IResourceService.ResourceI18n.*;

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

    @Autowired
    private IUserAmountPayoutService userAmountPayoutService;

    @Autowired
    private IPaymentRecordService paymentRecordService;

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
    @Secured(name = RESOURCE_NAME_ORDER_LIST, resource = "orders:list", action = ActionTypes.READ)
    public RestResult<Page<IOrderService.OrderListVo>> list(PageModel page,
                                                        @RequestParam(name = "orderSn", required = false) String orderSn,
                                                        @RequestParam(name = "title", required = false) String title,
                                                        @RequestParam(name = "payStatus", required = false) String payStatus,
                                                        @RequestParam(name = "tradeStatus", required = false) String tradeStatus,
                                                        @RequestParam(name = "status", required = false) String status) {
        QueryWrapper queryWrapper = Wrappers.query().eq(StrUtils.isNotBlank(orderSn), "o.order_sn", orderSn)
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
    @Secured(name = RESOURCE_NAME_ORDER_DETAIL, resource = "orders:detail", action = ActionTypes.READ)
    public RestResult<IOrderService.OrderDetailVo> detail(@PathVariable(name = "orderId") Long orderId) {
        return RestResultUtils.success(orderService.getOrderDetail(orderId));
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @PostMapping("delete/{orderId}")
    @Secured(name = RESOURCE_NAME_ORDER_DELETE, resource = "orders:delete", action = ActionTypes.WRITE)
    public Object deleteOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getById(orderId);
        if(order != null) {
            order.setStatus(Order.ORDER_STATUS_DEL);
            orderService.updateById(order);
        }
        return RestResultUtils.success();
    }

    /**
     * 提现管理
     * @param page
     * @param userName
     * @param status
     * @return
     */
    @GetMapping("cashout/list")
    @Secured(name = RESOURCE_NAME_ORDER_CASHOUT_LIST, resource = "orders:cashout/list", action = ActionTypes.READ)
    public RestResult<Page<IUserAmountPayoutService.CashOutListVo>> cashOutList(PageModel page,
                                                                                @RequestParam(name = "userName", required = false) String userName,
                                                                                @RequestParam(name = "status", required = false) String status) {
        QueryWrapper queryWrapper = Wrappers.query().eq(StrUtils.isNotBlank(userName), "u.user_name", userName)
                .eq(StringUtils.isNotBlank(status), "uas.status", status)
                .orderByDesc("uas.created");
        return RestResultUtils.success(userAmountPayoutService.pageCashOut(page.toPage(), queryWrapper));
    }

    /**
     * 提现单详情
     * @param payoutId   提现单号
     * @return
     */
    @GetMapping("cashout/detail/{payoutId}")
    @Secured(name = RESOURCE_NAME_ORDER_CASHOUT_INFO, resource = "orders:cashout/detail", action = ActionTypes.READ)
    public RestResult<IUserAmountPayoutService.CashOutDetailVo> getCashOutDetail(@PathVariable(name = "payoutId") Long payoutId) {
        return RestResultUtils.success(userAmountPayoutService.getCashOutDetail(payoutId));
    }

    /**
     * 确认提现单
     * @param payoutId
     * @return
     * @throws FastcmsException
     */
    @PostMapping("cashout/confirm/{payoutId}")
    @Secured(name = RESOURCE_NAME_ORDER_CASHOUT_CONFIRM, resource = "orders:cashout/confirm", action = ActionTypes.WRITE)
    public Object confirmCashOut(@PathVariable("payoutId") Long payoutId) throws FastcmsException {
        userAmountPayoutService.auditCashOut(payoutId);
        return RestResultUtils.success();
    }

    /**
     * 拒绝提现单
     * @param payoutId
     * @param feedback
     * @return
     * @throws FastcmsException
     */
    @PostMapping("cashout/refuse/{payoutId}")
    @Secured(name = RESOURCE_NAME_ORDER_CASHOUT_REFUSE, resource = "orders:cashout/refuse", action = ActionTypes.WRITE)
    public Object refuseCashOut(@PathVariable("payoutId") Long payoutId, @RequestParam("feedback") String feedback) {
        UserAmountPayout userAmountPayout = userAmountPayoutService.getById(payoutId);
        if (userAmountPayout != null) {
            userAmountPayout.setFeedback(feedback);
            userAmountPayout.setStatus(UserAmountPayout.AMOUNT_STATUS_REFUSE);
            userAmountPayoutService.updateById(userAmountPayout);
        }
        return RestResultUtils.success();
    }

    /**
     * 支付记录
     * @param page
     * @param userName
     * @return
     */
    @GetMapping("payment/list")
    @Secured(name = RESOURCE_NAME_ORDER_PAYMENT_LIST, resource = "orders:payment/list", action = ActionTypes.READ)
    public RestResult<Page<IPaymentRecordService.PaymentListVo>> paymentList(PageModel page,
                                                                             @RequestParam(name = "trxNo", required = false) String trxNo,
                                                                             @RequestParam(name = "userName", required = false) String userName) {
        QueryWrapper queryWrapper = Wrappers.query().eq(StrUtils.isNotBlank(userName), "u.user_name", userName)
                .eq(StringUtils.isNotBlank(trxNo), "pr.trx_no", trxNo)
                .orderByDesc("pr.created");
        return RestResultUtils.success(paymentRecordService.pagePaymentRecord(page.toPage(), queryWrapper));
    }

    /**
     * 支付记录详情
     * @param paymentId   订单号
     * @return
     */
    @GetMapping("payment/detail/{paymentId}")
    @Secured(name = RESOURCE_NAME_ORDER_PAYMENT_INFO, resource = "orders:payment/detail", action = ActionTypes.READ)
    public RestResult<IPaymentRecordService.PaymentDetailVo> paymentDetail(@PathVariable(name = "paymentId") Long paymentId) {
        return RestResultUtils.success(paymentRecordService.getPaymentDetail(paymentId));
    }

}
