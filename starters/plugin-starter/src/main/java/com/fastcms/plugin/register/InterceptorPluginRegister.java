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

import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/11
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class InterceptorPluginRegister extends AbstractPluginRegister implements PluginRegister {

	AbstractHandlerMapping handlerMapping;
	List<HandlerInterceptor> handlerInterceptorList;
	ApplicationContext applicationContext;

	public InterceptorPluginRegister(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void initialize() throws Exception {
		handlerMapping = applicationContext.getBean(AbstractHandlerMapping.class);
		Field adaptedInterceptorsField = ReflectionUtils.findField(handlerMapping.getClass(), "adaptedInterceptors", List.class);
		adaptedInterceptorsField.setAccessible(true);
		handlerInterceptorList = (List<HandlerInterceptor>) adaptedInterceptorsField.get(handlerMapping);
	}

	@Override
	public void registry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {
		List<Class<?>> interceptorClassList = getClassList(pluginRegistryWrapper);
		if(interceptorClassList.isEmpty()) return;
		if(!interceptorClassList.isEmpty()) {
			for (Class<?> aClass : interceptorClassList) {
				InterceptPath interceptPaths = aClass.getAnnotation(InterceptPath.class);
				if(interceptPaths != null && interceptPaths.value() != null) {
					MappedInterceptor mappedInterceptor = new MappedInterceptor(interceptPaths.value(), (HandlerInterceptor) pluginRegistryWrapper.getPluginApplicationContext().getBean(aClass));
					handlerInterceptorList.add(mappedInterceptor);
				}
			}
		}
	}

	@Override
	public void unRegistry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {

	}

	List<Class<?>> getClassList(PluginRegistryWrapper pluginRegistryWrapper) {
		return pluginRegistryWrapper.getClassList().stream().filter(item -> HandlerInterceptor.class.isAssignableFrom(item)).collect(Collectors.toList());
	}

}
