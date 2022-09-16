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
package com.fastcms.core.field;

import com.fastcms.utils.PluginUtils;
import com.fastcms.utils.ApplicationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/13
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class FieldRenderManager implements ApplicationListener<ApplicationStartedEvent> {

	Map<String, FastcmsFieldRender> fieldRenderMap = Collections.synchronizedMap(new HashMap<>());

	public FastcmsFieldRender getFieldRender(String fieldType) {
		FastcmsFieldRender fastcmsFieldRender = fieldRenderMap.get(fieldType);
		if (fastcmsFieldRender == null) {
			//从插件中找一次
			List<FastcmsFieldRender> extensions = PluginUtils.getExtensions(FastcmsFieldRender.class);
			extensions.forEach(item -> {
				Component annotation = item.getClass().getAnnotation(Component.class);
				if (StringUtils.isNotBlank(annotation.value())) {
					addFieldRender(annotation.value(), item);
				}
			});
			fastcmsFieldRender = fieldRenderMap.get(fieldType);
		}

		return fastcmsFieldRender;
	}

	public void addFieldRender(String name, FastcmsFieldRender fastcmsFieldRender) {
		//避免插件中的render覆盖系统默认的
		fieldRenderMap.putIfAbsent(name, fastcmsFieldRender);
	}

	public void removeFieldRender(String name) {
		fieldRenderMap.remove(name);
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		Map<String, FastcmsFieldRender> fastcmsFieldRenderMap = ApplicationUtils.getApplicationContext().getBeansOfType(FastcmsFieldRender.class);
		fieldRenderMap.putAll(fastcmsFieldRenderMap);
	}

}
