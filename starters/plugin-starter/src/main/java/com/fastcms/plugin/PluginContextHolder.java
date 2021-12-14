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
package com.fastcms.plugin;

import org.pf4j.PluginWrapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class PluginContextHolder {

	private static final Map<String, PluginWrapper> pluginMaps = Collections.synchronizedMap(new HashMap<>());

	public static void put(String pluginId, PluginWrapper pluginWrapper) {
		pluginMaps.putIfAbsent(pluginId, pluginWrapper);
	}

	public static PluginWrapper getPlugin(String pluginId) {
		return pluginMaps.get(pluginId);
	}

	public static void remove(String pluginId) {
		pluginMaps.remove(pluginId);
	}

	public static List<PluginWrapper> getPluginList() {
		return pluginMaps.values().stream().collect(Collectors.toList());
	}

	public static Class loadClass(String className) {
		List<PluginWrapper> pluginList = getPluginList();

		for (int i=0; i<pluginList.size();) {
			try {
				Class<?> aClass = pluginList.get(i).getPluginClassLoader().loadClass(className);
				return aClass;
			} catch (ClassNotFoundException e) {
				i ++;
			}
		}
		return null;
	}

}
