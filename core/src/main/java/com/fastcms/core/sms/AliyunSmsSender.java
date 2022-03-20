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
package com.fastcms.core.sms;

import com.fastcms.utils.ConfigUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/20
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class AliyunSmsSender implements FastcmsSmsSender {

	static final String ALIYUN_SMS_APP_KEY = "aliyunSmsAppKey";

	static final String ALIYUN_SMS_APP_SECRET = "aliyunSmsAppSecret";

	static final String ALIYUN_SMS_IS_ENABLE = "aliyunSmsIsEnable";

	@Override
	public boolean send(SmsMessage smsMessage) {
		return false;
	}

	@Override
	public boolean isEnable() {
		final String config = ConfigUtils.getConfig(ALIYUN_SMS_IS_ENABLE);
		if (StringUtils.isBlank(config)) return true;
		try {
			return Boolean.parseBoolean(config);
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	String getAppKey() {
		return ConfigUtils.getConfig(ALIYUN_SMS_APP_KEY);
	}

	String getAppSecret() {
		return ConfigUtils.getConfig(ALIYUN_SMS_APP_SECRET);
	}

}
