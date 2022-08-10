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
import com.fastcms.cms.utils.ArticleUtils;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.SnowFlake;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.entity.Order;
import com.fastcms.entity.OrderItem;
import com.fastcms.service.IOrderItemService;
import com.fastcms.service.IOrderService;
import com.fastcms.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2022/03/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractFastcmsOrderService implements IFastcmsOrderService {

    private static final SnowFlake SNOW_FLAKE = new SnowFlake(1, 1);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IArticleService articleService;

    protected String getOrderSN() {
        return String.valueOf(SNOW_FLAKE.genNextId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(CreateOrderParam createOrderParam) throws FastcmsException {

        if (AuthUtils.getUserId() == null) throw new FastcmsException(FastcmsException.INVALID_PARAM, "下单人不能为空");

        List<ProductParam> productParams = Arrays.asList(createOrderParam.getProducts());

        if(CollectionUtils.isEmpty(productParams)) throw new FastcmsException(FastcmsException.INVALID_PARAM, "商品不能为空");

        checkCreateOrderParam(createOrderParam);

        //订单项
        List<OrderItem> orderItemList = new ArrayList<>();

        for (ProductParam item : productParams) {
            final Long num = item.getNum();
            Article product = articleService.getById(item.getId());

            if(product != null && Article.STATUS_PUBLISH.equals(product.getStatus()) && ArticleUtils.getPrice(product) != null) {
                BigDecimal productPrice = ArticleUtils.getPrice(product);
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(item.getId());
                orderItem.setProductCount(num.intValue());
                orderItem.setTotalAmount(new BigDecimal(num).multiply(productPrice));
                orderItem.setSellerId(product.getCreateUserId());
                orderItemList.add(orderItem);
            }

        }

        if(CollectionUtils.isEmpty(orderItemList)) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "订单项不能为空");
        }

        Order order = new Order();

        order.setOrderTitle(articleService.getById(orderItemList.get(0).getProductId()).getTitle());
        order.setCreateUserId(AuthUtils.getUserId());
        order.setOrderSn(getOrderSN());
        order.setOrderAmount(orderItemList.stream().map(OrderItem::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_UP));
        order.setBuyerMsg(createOrderParam.getBuyerMsg());

        //根据优惠券，会员价等，计算出最终订单需要支付金额
        order.setPayAmount(order.getOrderAmount());

        order.setInvoiceStatus(Order.INVOICE_STATUS_NOT_APPLY);
        order.setPayStatus(Order.STATUS_PAY_PRE);
        order.setTradeStatus(Order.TRADE_STATUS_TRADING);
        order.setStatus(Order.ORDER_STATUS_NORMAL);

        order.setJsonExt(createOrderParam.getJsonExt());

        processOrderBeforePersistence(order, createOrderParam);

        orderService.save(order);

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }

        orderItemService.saveBatch(orderItemList);

        return order;
    }

    /**
     * 校验创建订单的请求参数
     * @param createOrderParam
     */
    protected abstract void checkCreateOrderParam(CreateOrderParam createOrderParam) throws FastcmsException;

    /**
     * 订单数据持久化前对不同业务进行扩展
     * 比如拼团订单，会员订单，折扣订单等逻辑不同
     * 会覆盖之前设置过的属性
     * @param order
     * @param createOrderParam
     */
    protected abstract void processOrderBeforePersistence(Order order, CreateOrderParam createOrderParam);

}