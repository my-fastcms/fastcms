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
package com.fastcms.payment.listener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class PaymentSuccessListenerManager implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	private List<PaymentSuccessListener> listeners;

	public List<PaymentSuccessListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<PaymentSuccessListener> listeners) {
		this.listeners = listeners;
	}

	public void addListener(PaymentSuccessListener listener) {
		if (listeners == null) {
			synchronized (PaymentSuccessListenerManager.class) {
				listeners = Collections.synchronizedList(new ArrayList<>());
			}
		}
		listeners.add(listener);
	}

	public void removeListener(PaymentSuccessListener listener) {
		listeners.remove(listener);
	}

//	public void notifySuccess(PaymentRecord paymentRecord) {
//		if (listeners != null) {
//			for (PaymentSuccessListener listener : listeners) {
//				try {
//					listener.onSuccess(paymentRecord);
//				} catch (Exception e) {
//				}
//			}
//		}
//	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, PaymentSuccessListener> matchingBeans =
				BeanFactoryUtils.beansOfTypeIncludingAncestors(this.applicationContext, PaymentSuccessListener.class, true, false);
		matchingBeans.keySet().forEach(item -> {
			addListener(matchingBeans.get(item));
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
