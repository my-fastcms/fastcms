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
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class ControllerPluginRegister extends AbstractPluginRegister {

	RequestMappingHandlerMapping requestMappingHandlerMapping;

	ApplicationContext applicationContext;

	Method getMappingForMethod;

	public ControllerPluginRegister (ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void initialize() throws Exception {
		requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		getMappingForMethod = ReflectionUtils.findMethod(RequestMappingHandlerMapping.class, "getMappingForMethod", Method.class, Class.class);
		getMappingForMethod.setAccessible(true);
	}

	@Override
	public void registry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {
		for (Class<?> aClass : pluginRegistryWrapper.getClassList()) {
			Controller annotation = aClass.getAnnotation(Controller.class);
			if(annotation != null) {
				Object bean = pluginRegistryWrapper.getPluginApplicationContext().getBean(aClass);
				Method[] methods = aClass.getMethods();
				for (Method method : methods) {
					if (method.getAnnotation(RequestMapping.class) != null
							|| method.getAnnotation(GetMapping.class) != null
							|| method.getAnnotation(PostMapping.class) != null) {
						RequestMappingInfo requestMappingInfo = (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, method, aClass);
						requestMappingHandlerMapping.registerMapping(requestMappingInfo, bean, method);
					}
				}
			}
		}
	}

	@Override
	public void unRegistry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {
		for (RequestMappingInfo requestMappingInfo : getRequestMappingInfo(pluginRegistryWrapper)) {
			requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
		}
	}

	List<RequestMappingInfo> getRequestMappingInfo(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {
		List<RequestMappingInfo> requestMappingInfoList = new ArrayList<>();
		for (Class<?> aClass : pluginRegistryWrapper.getClassList()) {
			Controller annotation = aClass.getAnnotation(Controller.class);
			if (annotation != null) {
				Method[] methods = aClass.getMethods();
				for (Method method : methods) {
					RequestMappingInfo requestMappingInfo = (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, method, aClass);
					requestMappingInfoList.add(requestMappingInfo);
				}
			}
		}
		return requestMappingInfoList;
	}

}
