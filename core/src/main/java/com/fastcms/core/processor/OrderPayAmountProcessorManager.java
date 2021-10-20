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
package com.fastcms.core.processor;

import com.fastcms.common.exception.FastcmsException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/7/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class OrderPayAmountProcessorManager implements OrderPayAmountProcessor {

	List<OrderPayAmountProcessor> orderPayAmountProcessorList = new ArrayList<>();

	@Override
	public void addPayAmountProcessor(OrderPayAmountProcessor orderPayAmountProcessor) {
		orderPayAmountProcessorList.add(orderPayAmountProcessor);
	}

	@Override
	public BigDecimal getAmount(BigDecimal orderAmount, OrderPayAmount orderPayAmount) throws FastcmsException {
		BigDecimal amount = BigDecimal.ZERO;
		for (OrderPayAmountProcessor orderPayAmountProcessor : orderPayAmountProcessorList) {
			amount = amount.add(orderPayAmountProcessor.getAmount(orderAmount, orderPayAmount));
		}
		return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
