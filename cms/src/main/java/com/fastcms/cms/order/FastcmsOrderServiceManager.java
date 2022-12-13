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

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.Order;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.I18nUtils;
import com.fastcms.utils.PluginUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fastcms.service.IOrderService.OrderI18n.ORDER_CLASS_TYPE_IS_NULL;
import static com.fastcms.service.IOrderService.OrderI18n.ORDER_TYPE_NOT_NULL;

/**
 * 订单服务统一入口
 * @author： wjun_java@163.com
 * @date： 2022/04/01
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class FastcmsOrderServiceManager implements ApplicationListener<ApplicationReadyEvent> {

    Map<String, IFastcmsOrderService> allOrderServiceMap = Collections.synchronizedMap(new HashMap<>());

    public Order createOrder(CreateOrderParam createOrderParam) throws FastcmsException {
        if (createOrderParam == null) {
            throw new FastcmsException(I18nUtils.getMessage(FastcmsConstants.FASTCMS_SYSTEM_NO_DATA));
        }

        if (StringUtils.isBlank(createOrderParam.getOrderTypeClass())) {
            throw new FastcmsException(I18nUtils.getMessage(ORDER_TYPE_NOT_NULL));
        }

        IFastcmsOrderService fastcmsOrderService = allOrderServiceMap.get(createOrderParam.getOrderTypeClass());
        if (fastcmsOrderService == null) {
            throw new FastcmsException(String.format(I18nUtils.getMessage(ORDER_CLASS_TYPE_IS_NULL), createOrderParam.getOrderTypeClass()));
        }

        return fastcmsOrderService.createOrder(createOrderParam);

    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, IFastcmsOrderService> fastcmsOrderServiceMap = ApplicationUtils.getApplicationContext().getBeansOfType(IFastcmsOrderService.class);
        fastcmsOrderServiceMap.keySet().forEach(item -> {
            IFastcmsOrderService fastcmsOrderService = fastcmsOrderServiceMap.get(item);
            String name = fastcmsOrderService.getClass().getName();
            if (name.contains("$$")) {
                name = name.substring(0, name.indexOf("$$"));
            }
            allOrderServiceMap.putIfAbsent(name, fastcmsOrderService);
        });

        List<IFastcmsOrderService> extensions = PluginUtils.getExtensions(IFastcmsOrderService.class);
        extensions.forEach(item -> allOrderServiceMap.putIfAbsent(item.getClass().getName(), item));
    }

}
