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

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/26
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface PluginRegister {

    /**
     * 插件组件初始化
     * @throws Exception
     */
    void initialize() throws Exception;

    /**
     * 插件组件注册
     * @param pluginRegistryWrapper
     * @throws Exception
     */
    void registry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception;

    /**
     * 插件组件取消注册
     * @param pluginRegistryWrapper
     * @throws Exception
     */
    void unRegistry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception;

}
