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

import com.fastcms.plugin.FastcmsPluginManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册带@Component注解的类到spring容器
 * 该Component中只能注入主程序中已存在的bean，
 * 不可注入插件中的Extensions，但可以注入插件中的mapper
 * @author： wjun_java@163.com
 * @date： 2022/1/23
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class ComponentRegister extends AbstractPluginRegister {

	public ComponentRegister(FastcmsPluginManager pluginManger) {
		super(pluginManger);
	}

	@Override
	public void registry(String pluginId) throws Exception {
		List<Class<?>> componentClasses = getComponentClasses(pluginId);

		// 修复 https://gitee.com/xjd2020/fastcms/issues/I5BIN1
		// 在创建bean之前注册component service相关beanDefinition
		componentClasses.forEach(item -> registerBeanDefinition(item));

		componentClasses.forEach(item -> registryBean(item));

	}

	@Override
	public void unRegistry(String pluginId) throws Exception {

		List<Class<?>> componentClasses = getComponentClasses(pluginId);

		componentClasses.forEach(item -> destroyBean(item));

		componentClasses.forEach(item -> removeBeanDefinition(item));

	}

	List<Class<?>> getComponentClasses(String pluginId) throws Exception {
		List<Class<?>> classList = new ArrayList<>();
		for (Class<?> pluginClass : getPluginClasses(pluginId)) {
			Component annotation = pluginClass.getAnnotation(Component.class);
			if(annotation != null) {
				classList.add(pluginClass);
				continue;
			}

			Service service = pluginClass.getAnnotation(Service.class);
			if(service != null) {
				classList.add(pluginClass);
			}

		}
		return classList;
	}

}
