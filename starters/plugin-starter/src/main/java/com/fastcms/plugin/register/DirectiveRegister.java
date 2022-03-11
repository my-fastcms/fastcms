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
import com.fastcms.plugin.view.FastcmsTemplateFreeMarkerConfig;
import com.fastcms.plugin.view.PluginFreeMarkerConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.util.ArrayList;
import java.util.List;

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
		List<Class<?>> directiveClass = getDirectiveClass(getPluginClasses(pluginId));
		if (directiveClass.isEmpty()) return;

		FreeMarkerConfig freeMarkerConfig = (FreeMarkerConfig) getBean(FreeMarkerConfig.class);
		FastcmsTemplateFreeMarkerConfig fastcmsTemplateFreeMarkerConfig = (FastcmsTemplateFreeMarkerConfig) getBean(FastcmsTemplateFreeMarkerConfig.class);
		PluginFreeMarkerConfig pluginFreeMarkerConfig = (PluginFreeMarkerConfig) getBean(PluginFreeMarkerConfig.class);

		for (Class aClass : directiveClass) {
			Directive annotation = (Directive) aClass.getAnnotation(Directive.class);
			if (annotation != null && StringUtils.isNotBlank(annotation.value())) {
				registryBean(annotation.value(), aClass);
				if (freeMarkerConfig != null) {
					freeMarkerConfig.getConfiguration().setSharedVariable(annotation.value(), getBean(aClass));
				}

				if (fastcmsTemplateFreeMarkerConfig != null) {
					fastcmsTemplateFreeMarkerConfig.getConfiguration().setSharedVariable(annotation.value(), getBean(aClass));
				}

				if (pluginFreeMarkerConfig != null) {
					pluginFreeMarkerConfig.getConfiguration().setSharedVariable(annotation.value(), getBean(aClass));
				}
			}
		}

	}

	@Override
	public void unRegistry(String pluginId) throws Exception {
		getDirectiveClass(getPluginClasses(pluginId)).forEach(item -> {
			Directive annotation = item.getAnnotation(Directive.class);
			if (annotation != null && StringUtils.isNotBlank(annotation.value())) {
				destroyBean(annotation.value(), item);
			}
		});
	}

	List<Class<?>> getDirectiveClass(List<Class<?>> pluginClasses) {
		List<Class<?>> directiveClassList = new ArrayList<>();
		for (Class<?> pluginClass : pluginClasses) {
			Directive annotation = pluginClass.getAnnotation(Directive.class);
			if (annotation != null) {
				directiveClassList.add(pluginClass);
			}
		}
		return directiveClassList;
	}

}
