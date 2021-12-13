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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/9
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class CompoundPluginRegister implements PluginRegister, ApplicationContextAware {

	ApplicationContext applicationContext;
	List<PluginRegister> pluginRegisterList = new ArrayList<>();

	@Override
	public void initialize() throws Exception {

		Map<String, PluginRegister> pluginRegisterMap = applicationContext.getBeansOfType(PluginRegister.class);
		pluginRegisterMap.values().stream().sorted(Comparator.comparing(PluginRegister::getOrder)).forEach(item -> {
			if(item instanceof CompoundPluginRegister == false) {
				pluginRegisterList.add(item);
			}
		});

		for (PluginRegister pluginRegister : pluginRegisterList) {
			pluginRegister.initialize();
		}

	}

	@Override
	public void registry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {

		for (PluginRegister pluginRegister : pluginRegisterList) {
			pluginRegister.registry(pluginRegistryWrapper);
		}

	}

	@Override
	public void unRegistry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {

		for (PluginRegister pluginRegister : pluginRegisterList) {
			pluginRegister.unRegistry(pluginRegistryWrapper);
		}

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
