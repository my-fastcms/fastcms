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
package com.fastcms.plugin.register;

import com.fastcms.plugin.Directive;
import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.view.FastcmsFreeMarkerConfig;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 插件中的freemarker标签注册
 * @author： wjun_java@163.com
 * @date： 2022/2/27
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class DirectiveRegister extends AbstractPluginRegister {

	public DirectiveRegister(FastcmsPluginManager pluginManger) {
		super(pluginManger);
	}

	@Override
	public void registry(String pluginId) throws Exception {
		List<Class<?>> directiveClass = getDirectiveClass(pluginId);
		if (directiveClass.isEmpty()) return;

		FreeMarkerConfig freeMarkerConfig = (FreeMarkerConfig) getBean(FreeMarkerConfig.class);

		Map<String, FastcmsFreeMarkerConfig> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(getApplicationContext(), FastcmsFreeMarkerConfig.class, true, false);

		for (Class aClass : directiveClass) {
			Directive annotation = (Directive) aClass.getAnnotation(Directive.class);
			if (annotation != null && StringUtils.isNotBlank(annotation.value())) {
				registryBean(annotation.value(), aClass);

				matchingBeans.values().forEach(item -> {
					try {
						freeMarkerConfig.getConfiguration().setSharedVariable(annotation.value(), getBean(aClass));
					} catch (TemplateModelException e) {
						e.printStackTrace();
						throw new RuntimeException(e.getMessage());
					}
				});

				if (freeMarkerConfig != null) {
					freeMarkerConfig.getConfiguration().setSharedVariable(annotation.value(), getBean(aClass));
				}

			}
		}

	}

	@Override
	public void unRegistry(String pluginId) throws Exception {
		getDirectiveClass(pluginId).forEach(item -> {
			Directive annotation = item.getAnnotation(Directive.class);
			if (annotation != null && StringUtils.isNotBlank(annotation.value())) {
				destroyBean(annotation.value(), item);
			}
		});
	}

	List<Class<?>> getDirectiveClass(String pluginId) throws Exception {
		return getPluginClasses(pluginId).stream().filter(item -> item.getAnnotation(Directive.class) != null).collect(Collectors.toList());
	}

}
