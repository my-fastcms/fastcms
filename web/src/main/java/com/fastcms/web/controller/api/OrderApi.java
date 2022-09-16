<<<<<<< HEAD
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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.order.CreateOrderParam;
import com.fastcms.cms.order.FastcmsOrderServiceManager;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
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
    FastcmsOrderServiceManager orderServiceManager;

    @Autowired
    private IOrderService orderService;

    /**
     * 创建普通订单
     * @param orderParam
     * @return
     */
    @PostMapping("save")
    public RestResult<Long> save(@RequestBody CreateOrderParam orderParam) {
        try {
            Order order = orderServiceManager.createOrder(orderParam);
            return RestResultUtils.success(order.getId());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 订单列表
     * @param page
     * @return
     */
    @GetMapping("list")
    public RestResult<Page<IOrderService.OrderListVo>> list(PageModel page) {
        QueryWrapper queryWrapper = Wrappers.query()
                .eq("o.status", Order.ORDER_STATUS_NORMAL)
                .orderByDesc("o.created");
        return RestResultUtils.success(orderService.pageOrder(page.toPage(), queryWrapper));
    }

    /**
     * 订单详情
     * @param orderId 订单id
     * @return
     */
    @GetMapping("detail/{orderId}")
    public RestResult<IOrderService.OrderDetailVo> detail(@PathVariable(name = "orderId") Long orderId) {
        Order order = orderService.getById(orderId);
        if(order != null && AuthUtils.getUser() != null && order.getCreateUserId() != AuthUtils.getUserId()) {
            return RestResultUtils.failed("只能查看自己的订单");
        }
        return RestResultUtils.success(orderService.getOrderDetail(orderId));
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
=======
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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.order.CreateOrderParam;
import com.fastcms.cms.order.FastcmsOrderServiceManager;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
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
    FastcmsOrderServiceManager orderServiceManager;

    @Autowired
    private IOrderService orderService;

    /**
     * 创建普通订单
     * @param orderParam
     * @return
     */
    @PostMapping("save")
    public RestResult<Long> save(@RequestBody CreateOrderParam orderParam) {
        try {
            Order order = orderServiceManager.createOrder(orderParam);
            return RestResultUtils.success(order.getId());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 订单列表
     * @param page
     * @return
     */
    @GetMapping("list")
    public RestResult<Page<IOrderService.OrderListVo>> list(PageModel page) {
        QueryWrapper queryWrapper = Wrappers.query()
                .eq("o.status", Order.ORDER_STATUS_NORMAL)
                .orderByDesc("o.created");
        return RestResultUtils.success(orderService.pageOrder(page.toPage(), queryWrapper));
    }

    /**
     * 订单详情
     * @param orderId 订单id
     * @return
     */
    @GetMapping("detail/{orderId}")
    public RestResult<IOrderService.OrderDetailVo> detail(@PathVariable(name = "orderId") Long orderId) {
        Order order = orderService.getById(orderId);
        if(order != null && AuthUtils.getUser() != null && order.getCreateUserId() != AuthUtils.getUserId()) {
            return RestResultUtils.failed("只能查看自己的订单");
        }
        return RestResultUtils.success(orderService.getOrderDetail(orderId));
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
>>>>>>> 9b22e8ee2077deb1d0ca28d39702bca483d28388
