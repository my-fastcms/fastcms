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

import com.egzosn.pay.common.bean.PayMessage;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.Order;
import org.pf4j.ExtensionPoint;

/**
 * 订单业务扩展抽象类
 * @author： wjun_java@163.com
 * @date： 2022/3/27
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractExtensionOrderService implements IFastcmsOrderService, ExtensionPoint {

	@Override
	public void payBackOrder(PayMessage payMessage) throws FastcmsException {
		throw new FastcmsException("not support");
	}

	@Override
	public void processOrder(Order order) throws FastcmsException {
		doProcessOrder(order);
	}

	public abstract void doProcessOrder(Order order) throws FastcmsException;

}
