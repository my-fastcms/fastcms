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
package com.fastcms.core.config;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2022/7/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractConfigListener implements ConfigListener {

	@Override
	public void change(Map<String, String> datasMap) {
		if (!isMatch(datasMap)) {
			return;
		}
		doChange(datasMap);
	}

	@Override
	public boolean isMatch(Map<String, String> datasMap) {
		for (Map.Entry<String, String> entry : datasMap.entrySet()) {
			String key = entry.getKey();
			if(StringUtils.isNotBlank(key) &&
					(key.trim().equalsIgnoreCase(getMatchKey()) || key.trim().startsWith(getMatchKey()))) {
				return true;
			}
		}
		return false;
	}

	protected abstract String getMatchKey();

	protected abstract void doChange(Map<String, String> datasMap);

}
