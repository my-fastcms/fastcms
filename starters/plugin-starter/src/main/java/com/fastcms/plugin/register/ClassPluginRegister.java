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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class ClassPluginRegister extends AbstractPluginRegister implements PluginRegister {

	@Override
	public void initialize() throws Exception {

	}

	@Override
	public void registry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {
		List<Class<?>> classList = pluginRegistryWrapper.getClassList().stream().filter(item -> !item.isInterface()).collect(Collectors.toList());
		if(!classList.isEmpty()) {
			List<Class<?>> registryClassList = new ArrayList<>();
			for (Class<?> aClass : classList) {
				registryClassList.add(aClass);
			}
			if(!registryClassList.isEmpty()) {
				pluginRegistryWrapper.getPluginApplicationContext().register(registryClassList.toArray(new Class[registryClassList.size()]));
			}
		}
	}

	@Override
	public void unRegistry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {
		List<Class<?>> classList = pluginRegistryWrapper.getClassList().stream().filter(item -> !item.isInterface()).collect(Collectors.toList());
		for (Class<?> aClass : classList) {
			String[] beanNamesForType = pluginRegistryWrapper.getPluginApplicationContext().getBeanNamesForType(aClass);
			if(beanNamesForType != null && beanNamesForType.length>0) {
				pluginRegistryWrapper.getPluginApplicationContext().removeBeanDefinition(beanNamesForType[0]);
			}
		}
	}

}
