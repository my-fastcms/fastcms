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

import org.pf4j.PluginManager;

import java.util.*;

/**
 * wjun_java@163.com
 */
public class FastcmsSpringExtensionFactory extends SpringExtensionFactory implements FastcmsExtensionFactory {

    private final List<String> extensionClassNames;

    private Map<String, Object> cache;

    public FastcmsSpringExtensionFactory(PluginManager pluginManager) {
        this(pluginManager, true);
    }

    public FastcmsSpringExtensionFactory(PluginManager pluginManager, String... extensionClassNames) {
        this(pluginManager, true, extensionClassNames);
    }

    public FastcmsSpringExtensionFactory(PluginManager pluginManager, boolean autowire, String... extensionClassNames) {
        super(pluginManager, autowire);

        this.extensionClassNames = Arrays.asList(extensionClassNames);

        cache = Collections.synchronizedMap(new HashMap<>()); // simple cache implementation
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> extensionClass) {
        return create(extensionClass.getName(), extensionClass);
    }

    @Override
    public <T> T create(String beanName, Class<T> extensionClass) {
        if (cache.containsKey(beanName)) {
            return (T) cache.get(beanName);
        }

        T extension = super.create(extensionClass);
        if (extensionClassNames.isEmpty() || extensionClassNames.contains(beanName)) {
            cache.put(beanName, extension);
        }

        return extension;
    }

    @Override
    public void destroy(Class<?> extensionClass) {
        String extensionClassName = extensionClass.getName();
        if(cache.containsKey(extensionClassName)) {
            cache.remove(extensionClassName);
        }
    }
}
