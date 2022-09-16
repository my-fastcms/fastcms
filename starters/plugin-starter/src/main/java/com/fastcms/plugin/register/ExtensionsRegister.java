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

import java.util.Set;

/**
 * 扫描带有@Extension的插件扩展类
 * wjun_java@163.com
 */
public class ExtensionsRegister extends AbstractPluginRegister {

    public ExtensionsRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
    }

    @Override
    public void registry(String pluginId) throws Exception {
        for (String extensionClassName : getExtensionClassNames(pluginId)) {
            try {
                registerExtension(getPluginExtensionClass(pluginId, extensionClassName));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public void unRegistry(String pluginId) throws Exception {
        for (String extensionClassName : getExtensionClassNames(pluginId)) {
            try {
                unRegisterExtension(getPluginExtensionClass(pluginId, extensionClassName));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    Set<String> getExtensionClassNames(String pluginId) {
        return pluginManger.getExtensionClassNames(pluginId);
    }

    Class<?> getPluginExtensionClass(String pluginId, String extensionClassName) throws ClassNotFoundException {
        return pluginManger.getPlugin(pluginId).getPluginClassLoader().loadClass(extensionClassName);
    }

    private void registerExtension(Class<?> extensionClass) {
        registryBean(extensionClass);
    }

    private void unRegisterExtension(Class<?> extensionClass) {
        removeBeanDefinition(extensionClass);
        destroyBean(extensionClass);
    }

}
