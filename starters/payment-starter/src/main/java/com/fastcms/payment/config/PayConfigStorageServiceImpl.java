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
package com.fastcms.payment.config;

import org.springframework.beans.factory.InitializingBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class PayConfigStorageServiceImpl implements PayConfigStorageService, InitializingBean {

	private Map<String, PaymentPlatformConfig> configMap = Collections.synchronizedMap(new HashMap<>());

	@Override
	public PaymentPlatformConfig getConfig(String platform) {
		return configMap.get(platform);
	}

	@Override
	public void initConfigStorageMap() {

		if(!configMap.isEmpty()) configMap.clear();

		//微信支付配置
		WxPayConfig wxConfig = new WxPayConfig();
//		wxConfig.setAppId(configService.getValue(fastcmsConstants.WECHAT_MINI_APP_ID));
//		wxConfig.setMchId(configService.getValue(fastcmsConstants.WECHAT_MCH_ID));
//		wxConfig.setNotifyUrl(configService.getValue(fastcmsConstants.WEBSITE_DOMAIN) + "/payback/" + wxConfig.getPayType());
//		wxConfig.setReturnUrl(configService.getValue(fastcmsConstants.WEBSITE_DOMAIN)  + "/payback/" + wxConfig.getPayType());
//		wxConfig.setSignType("MD5");
//		wxConfig.setInputCharset("UTF-8");
//		wxConfig.setKeyPrivate(configService.getValue(fastcmsConstants.WECHAT_MCH_SECRET));
		configMap.put(wxConfig.getPayType(), wxConfig);

		//支付宝支付配置 ...

		//其他配置 ...

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConfigStorageMap();
	}
}
