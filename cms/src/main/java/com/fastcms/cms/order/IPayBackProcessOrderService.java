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

import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.Order;
import org.pf4j.ExtensionPoint;

/**
 * 订单支付后业务逻辑处理
 * 各种订单类型在支付后，对应的处理逻辑不同
 * 比如分销订单需要结算佣金
 * 比如拼团订单需要关联团购信息
 * 等等...
 * 通常在插件中实现
 * @author： wjun_java@163.com
 * @date： 2022/04/01
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface IPayBackProcessOrderService extends ExtensionPoint {

    /**
     * 订单支付后业务逻辑扩展
     * @param order
     */
    void processOrder(Order order) throws FastcmsException;

    /**
     * 是否符合业务逻辑处理条件
     * @param order
     * @return
     */
    boolean isMatch(Order order);

}
