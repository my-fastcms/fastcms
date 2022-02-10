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

import com.fastcms.cms.entity.Article;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.SnowFlake;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.entity.Order;
import com.fastcms.entity.OrderItem;
import com.fastcms.service.IOrderItemService;
import com.fastcms.service.IOrderService;
import com.fastcms.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2022/02/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class DefaultFastcmsOrderService implements IFastcmsOrderService {

    private static final SnowFlake SNOW_FLAKE = new SnowFlake(1, 1);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IArticleService articleService;

    public static String getOrderSN() {
        return String.valueOf(SNOW_FLAKE.genNextId());
    }

    @Override
    @Transactional
    public Long createOrder(CreateOrderParam createOrderParam) throws FastcmsException {

        if (AuthUtils.getUserId() == null) throw new FastcmsException(FastcmsException.INVALID_PARAM, "下单人不能为空");

        if(CollectionUtils.isEmpty(createOrderParam.getProducts())) throw new FastcmsException(FastcmsException.INVALID_PARAM, "articleId不能为空");

        Article article = articleService.getById(createOrderParam.getProducts().get(0).getId());
        if(article == null) throw new FastcmsException(FastcmsException.SERVER_ERROR, "商品不存在");

        //订单项
        List<OrderItem> orderItemList = new ArrayList<>();

        for (ProductParam item : createOrderParam.getProducts()) {
            Long num = item.getNum();
            Article product = articleService.getById(item.getId());

            if(product != null && Article.STATUS_PUBLISH.equals(product.getStatus()) && product.getExtFields().get("price") != null) {
                BigDecimal productPrice = new BigDecimal((String) product.getExtFields().get("price"));
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(item.getId());
                orderItem.setProductCount(num.intValue());
                orderItem.setTotalAmount(new BigDecimal(num).multiply(productPrice));
                orderItem.setSellerId(product.getUserId());
                orderItemList.add(orderItem);
            }

        }

        Order order = new Order();
        order.setUserId(AuthUtils.getUserId());
        order.setOrderSn(getOrderSN());
        order.setOrderAmount(orderItemList.stream().map(OrderItem::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_UP));
        order.setPayStatus(Order.STATUS_PAY_PRE);
        order.setBuyerMsg(createOrderParam.getBuyerMsg());
        order.setOrderTitle(article.getTitle());

        order.setInvoiceStatus(Order.INVOICE_STATUS_NOT_APPLY);

        //根据优惠券，会员价等，计算出最终订单需要支付金额
        order.setPayAmount(order.getOrderAmount());

        order.setTradeStatus(Order.TRADE_STATUS_TRADING);
        order.setStatus(Order.ORDER_STATUS_NORMAL);
        orderService.save(order);

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }

        orderItemService.saveBatch(orderItemList);

        return order.getId();
    }

}
