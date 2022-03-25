/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastcms.cms.order;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.egzosn.pay.common.bean.PayMessage;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.utils.PluginUtils;
import com.fastcms.core.utils.RequestUtils;
import com.fastcms.entity.Order;
import com.fastcms.entity.OrderItem;
import com.fastcms.entity.PaymentRecord;
import com.fastcms.service.IOrderItemService;
import com.fastcms.service.IOrderService;
import com.fastcms.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2022/03/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractFastcmsOrderService implements IFastcmsOrderService {

    @Autowired
    private IPaymentRecordService paymentRecordService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

    @Override
    public void payBackOrder(PayMessage payMessage) throws FastcmsException {

        final String orderSn = payMessage.getOutTradeNo();
        Order order = orderService.getOne(Wrappers.<Order>lambdaQuery().eq(Order::getOrderSn, orderSn));
        if(order == null) {
            throw new FastcmsException(">>>can not find Order by orderSn:" + orderSn);
        }

        if (order.getPayStatus() == Order.STATUS_PAY_SUCCESS) {
            throw new FastcmsException(">>>Order is already payed orderSn:" + orderSn);
        }

        order.setPayStatus(Order.STATUS_PAY_SUCCESS);
        order.setTradeStatus(Order.TRADE_STATUS_COMPLETED);
        orderService.updateById(order);

        List<OrderItem> orderItems = orderItemService.list(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderId, order.getId()));
        orderItems.forEach(item -> {
            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.setTrxNo(orderSn);
            paymentRecord.setTrxType(PaymentRecord.TRX_TYPE_ORDER);
            paymentRecord.setTrxNonceStr(StrUtils.uuid());
            paymentRecord.setPayType(payMessage.getPayType());
            paymentRecord.setOrderIp(RequestUtils.getIpAddress(RequestUtils.getRequest()));
            paymentRecord.setPayStatus(Order.STATUS_PAY_SUCCESS);
            paymentRecord.setProductRelativeId(item.getProductId());
            paymentRecord.setPayAmount(item.getTotalAmount());
            paymentRecord.setPayerFee(BigDecimal.ZERO);
            paymentRecord.setPaySuccessAmount(item.getTotalAmount());
            paymentRecord.setUserId(order.getUserId());
            paymentRecord.setPaySuccessTime(LocalDateTime.now());
//            paymentRecord.setThirdpartyUserOpenid(wxPayMessage.getOpenid());
//            paymentRecord.setThirdpartyTransactionId(wxPayMessage.getTransactionId());
//            paymentRecord.setThirdpartyAppid(wxPayMessage.getAppid());
//            paymentRecord.setThirdpartyMchId(wxPayMessage.getMchId());
//            paymentRecord.setThirdpartyType(WechatPaymentPlatform.platformName);
            setPaymentRecordPlatformInfo(paymentRecord, payMessage);
            paymentRecord.setPayStatus(Order.STATUS_PAY_SUCCESS);
            paymentRecordService.save(paymentRecord);
        });

        /**
         * 插件中对订单处理的业务扩展
         */
        List<IFastcmsOrderService> extensions = PluginUtils.getExtensions(IFastcmsOrderService.class);
        extensions.forEach(item -> {
            try {
                item.processOrder(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    /**
     * 由子类实现不同的支付平台对支付记录的信息补充
     * @param paymentRecord
     * @param payMessage
     */
    protected abstract void setPaymentRecordPlatformInfo(PaymentRecord paymentRecord, PayMessage payMessage);

}
