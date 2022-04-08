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
package com.fastcms.task;

import com.fastcms.common.executor.ExecutorFactory;
import com.fastcms.common.executor.NameThreadFactory;
import com.fastcms.entity.Order;
import com.fastcms.service.IOrderService;
import com.fastcms.utils.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * 到时间未支付订单自动取消
 * @author： wjun_java@163.com
 * @date： 2022/3/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class OrderAutoCancelTask {

	private static final String OVER_TIME = "unPayOrderCancelTime";
	private static final String ENABLE_AUTO_CANCEL_ORDER = "enableAutoCancelOrder";

	private static final Set<Long> orderSet = Collections.synchronizedSet(new HashSet<>());

	private static final List<Order> orderList = Collections.synchronizedList(new ArrayList<>());

	private static final ExecutorService executorService = ExecutorFactory.newFixedExecutorService(6, new NameThreadFactory("OrderAutoCancelThread"));

	@Autowired
	private IOrderService orderService;

	@Scheduled(cron = "0 */1 * * * ?")
	public void getOrder() {
		if (!isEnableAutoCancelOrder()) {
			return;
		}

		if (getOverTime() <=0) {
			return;
		}

		List<Order> unPayOrder = orderService.getUnPayOrderByLimitTime(getOverTime());

		unPayOrder.forEach(item -> {
			if (!orderSet.contains(item.getId())) {
				orderSet.add(item.getId());
				orderList.add(item);
			}
		});
		unPayOrder.clear();
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void dealOrder() {
		executorService.submit(() -> {
			orderList.forEach(item -> {
				item.setStatus(Order.ORDER_STATUS_DEL);
				item.setUpdated(LocalDateTime.now());
			});
			try {
				orderService.updateBatchById(orderList);
			} finally {
				orderSet.clear();
				orderList.clear();
			}
		});
	}

	int getOverTime() {
		try {
			return Integer.parseInt(ConfigUtils.getConfig(OVER_TIME));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	boolean isEnableAutoCancelOrder() {
		try {
			return Boolean.parseBoolean(ConfigUtils.getConfig(ENABLE_AUTO_CANCEL_ORDER));
		} catch (Exception e) {
			return false;
		}
	}

}
