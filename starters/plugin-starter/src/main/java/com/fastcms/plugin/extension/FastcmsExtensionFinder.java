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
package com.fastcms.plugin.extension;

import org.pf4j.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author： wjun_java@163.com
 * @date： 2022/1/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsExtensionFinder implements ExtensionFinder, PluginStateListener {

	protected PluginManager pluginManager;
	protected List<ExtensionFinder> finders = new ArrayList<>();

	public FastcmsExtensionFinder(PluginManager pluginManager) {
		this.pluginManager = pluginManager;

		add(new FastcmsLegacyExtensionFinder(pluginManager));
	}

	@Override
	public <T> List<ExtensionWrapper<T>> find(Class<T> type) {
		List<ExtensionWrapper<T>> extensions = new ArrayList<>();
		for (ExtensionFinder finder : finders) {
			extensions.addAll(finder.find(type));
		}

		return extensions;
	}

	@Override
	public <T> List<ExtensionWrapper<T>> find(Class<T> type, String pluginId) {
		List<ExtensionWrapper<T>> extensions = new ArrayList<>();
		for (ExtensionFinder finder : finders) {
			extensions.addAll(finder.find(type, pluginId));
		}

		return extensions;
	}

	@Override
	public List<ExtensionWrapper> find(String pluginId) {
		List<ExtensionWrapper> extensions = new ArrayList<>();
		for (ExtensionFinder finder : finders) {
			extensions.addAll(finder.find(pluginId));
		}

		return extensions;
	}

	@Override
	public Set<String> findClassNames(String pluginId) {
		Set<String> classNames = new HashSet<>();
		for (ExtensionFinder finder : finders) {
			classNames.addAll(finder.findClassNames(pluginId));
		}

		return classNames;
	}

	@Override
	public void pluginStateChanged(PluginStateEvent event) {
		for (ExtensionFinder finder : finders) {
			if (finder instanceof PluginStateListener) {
				((PluginStateListener) finder).pluginStateChanged(event);
			}
		}
	}

	public FastcmsExtensionFinder add(ExtensionFinder finder) {
		finders.add(finder);

		return this;
	}

}
