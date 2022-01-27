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
package com.fastcms.core.directive;

import com.fastcms.common.utils.StrUtils;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleScalar;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/23
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("formatTime")
public class LocalDateTimeFormat extends BaseSubDirective {

	static final String DEFAULT_DATA_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	static final String FORMAT_PATTERN = "format";

	@Override
	public Object doExecute(Environment env, Map params) {
		StringModel time = null;
		try {
			time = (StringModel) params.get(PARAM_KEY);
		} catch (Exception e) {
			return "";
		}

		SimpleScalar format;
		String formatStr = DEFAULT_DATA_TIME_PATTERN;
		try {
			format = (SimpleScalar) params.get(FORMAT_PATTERN);
			if(format != null) {
				formatStr = format.getAsString();
				if(StrUtils.isBlank(formatStr)) {
					formatStr = DEFAULT_DATA_TIME_PATTERN;
				}
			}
		} catch (Exception e) {
			formatStr = DEFAULT_DATA_TIME_PATTERN;
		}

		String asString = time.getAsString();
		return StringUtils.isEmpty(asString) ? "" : LocalDateTime.parse(asString).format(DateTimeFormatter.ofPattern(formatStr));
	}

}
