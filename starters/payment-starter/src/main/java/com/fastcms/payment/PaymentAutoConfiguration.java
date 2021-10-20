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
package com.fastcms.payment;

import com.fastcms.payment.config.PayConfigStorageService;
import com.fastcms.payment.config.PayConfigStorageServiceImpl;
import com.fastcms.payment.platform.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * 支付服务自动装配类
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
@ImportAutoConfiguration({AliPaymentPlatform.class, UnionPaymentPlatform.class, WxPaymentPlatform.class})
public class PaymentAutoConfiguration {

	@Autowired
	@Order
	public void loadPaymentPlatforms(List<PaymentPlatform> platforms){
		for (PaymentPlatform platform : platforms) {
			PaymentPlatforms.loadPaymentPlatform(platform);
		}
	}

	@Bean
	public PayConfigStorageService payConfigStorageService(){
		return new PayConfigStorageServiceImpl();
	}

	@Bean
	@Order
	public PayServiceManager payServiceManager(){
		return new FastcmsPayServiceManager();
	}

}
