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
package com.fastcms.web.controller.api;

import com.fastcms.cms.order.IFastcmsOrderService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.entity.Order;
import com.fastcms.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 * @author： wjun_java@163.com
 * @date： 2022/02/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/order")
public class OrderApi {

    private static final Logger LOG = LoggerFactory.getLogger(OrderApi.class);

    @Autowired
    IFastcmsOrderService fastcmsOrderService;

    @Autowired
    private IOrderService orderService;

    /**
     * 创建订单
     * @param orderParam
     * @return
     */
    @PostMapping("save")
    public RestResult<Long> save(@RequestBody IFastcmsOrderService.CreateOrderParam orderParam) {
        try {
            Long orderId = fastcmsOrderService.createOrder(orderParam);
            return RestResultUtils.success(orderId);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 检查订单支付状态
     * @param orderId
     * @return
     */
    @GetMapping("status/check/{orderId}")
    public Object checkOrderPayStatus(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getById(orderId);
        if(order != null && order.getPayStatus() == Order.STATUS_PAY_SUCCESS) {
            return RestResultUtils.success("订单已支付");
        }
        return RestResultUtils.failed("订单未支付");
    }

}
