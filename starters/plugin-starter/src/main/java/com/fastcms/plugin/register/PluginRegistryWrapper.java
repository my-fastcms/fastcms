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

import com.fastcms.plugin.PluginApplicationContextHolder;
import org.pf4j.PluginWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/12
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class PluginRegistryWrapper {

	private ApplicationContext mainApplicationContext;
	private AnnotationConfigApplicationContext pluginApplicationContext;
	private PluginWrapper pluginWrapper;
	private List<Class<?>> classList;
	private List<Resource> resourceList;

	public PluginRegistryWrapper(PluginWrapper pluginWrapper, ApplicationContext applicationContext) {
		this.pluginWrapper = pluginWrapper;
		this.mainApplicationContext = applicationContext;
		this.pluginApplicationContext = getPluginApplicationContext(pluginWrapper);
		this.pluginApplicationContext.setParent(mainApplicationContext);
		this.classList = new ArrayList<>();
		this.resourceList = new ArrayList<>();
	}

	AnnotationConfigApplicationContext getPluginApplicationContext(PluginWrapper pluginWrapper) {
		AnnotationConfigApplicationContext pluginApplicationContext = PluginApplicationContextHolder.getApplicationContext(pluginWrapper.getPluginId());
		if(pluginApplicationContext == null) pluginApplicationContext = new AnnotationConfigApplicationContext();
		PluginApplicationContextHolder.addPluginApplicationContext(pluginWrapper.getPluginId(), pluginApplicationContext);
		return PluginApplicationContextHolder.getApplicationContext(pluginWrapper.getPluginId());
	}

	public ApplicationContext getMainApplicationContext() {
		return mainApplicationContext;
	}

	public void setMainApplicationContext(ApplicationContext mainApplicationContext) {
		this.mainApplicationContext = mainApplicationContext;
	}

	public AnnotationConfigApplicationContext getPluginApplicationContext() {
		return pluginApplicationContext;
	}

	public void setPluginApplicationContext(AnnotationConfigApplicationContext pluginApplicationContext) {
		this.pluginApplicationContext = pluginApplicationContext;
	}

	public PluginWrapper getPluginWrapper() {
		return pluginWrapper;
	}

	public void setPluginWrapper(PluginWrapper pluginWrapper) {
		this.pluginWrapper = pluginWrapper;
	}

	public List<Class<?>> getClassList() {
		return classList;
	}

	public void setClassList(List<Class<?>> classList) {
		this.classList = classList;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}
}
