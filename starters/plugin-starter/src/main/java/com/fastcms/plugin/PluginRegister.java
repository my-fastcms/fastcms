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

/**
 * wjun_java@163.com
 * 插件组件注册器
 * 比如注册拦截器
 */
public interface PluginRegister {

    /**
     * 安装时注册插件组件
     * @param pluginId
     */
    void registry(String pluginId) throws Exception;

    /**
     * 卸载时取消插件组件注册
     * @param pluginId
     */
    void unRegistry(String pluginId) throws Exception;

}
