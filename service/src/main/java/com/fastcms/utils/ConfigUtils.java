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
package com.fastcms.utils;

import com.fastcms.common.utils.StrUtils;
import com.fastcms.service.IConfigService;

import java.math.BigDecimal;

/**
 * 获取系统配置工具类
 * @author： wjun_java@163.com
 * @date： 2021/11/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class ConfigUtils {

	public static String getConfig(String key) {
		return getConfig(key, null);
	}

	public static String getConfig(String key, String defaultValue) {
		return ApplicationUtils.getApplicationContext().getBean(IConfigService.class).getValue(key) == null
				? defaultValue : ApplicationUtils.getApplicationContext().getBean(IConfigService.class).getValue(key);
	}

	public static Integer getInt(String key, Integer defaultValue) {
		try {
			return Integer.parseInt(getConfig(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Integer getInt(String key) {
		return getInt(key, 0);
	}

	public static Long getLong(String key, Long defaultValue) {
		try {
			return Long.parseLong(getConfig(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Long getLong(String key) {
		return getLong(key, 0L);
	}

	public static Boolean getBool(String key, Boolean defaultValue) {
		final String config = getConfig(key);
		if (StrUtils.isBlank(config)) {
			return defaultValue;
		}
		return Boolean.parseBoolean(config);
	}

	public static Boolean getBool(String key) {
		return getBool(key, false);
	}

	public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		try {
			return BigDecimal.valueOf(Long.valueOf(ConfigUtils.getConfig(key)));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static BigDecimal getBigDecimal(String key) {
		return getBigDecimal(key, BigDecimal.ZERO);
	}

}
