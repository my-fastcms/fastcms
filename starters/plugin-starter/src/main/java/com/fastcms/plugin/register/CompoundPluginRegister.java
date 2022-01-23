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
import com.fastcms.plugin.PluginRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * wjun_java@163.com
 * 插件主键注册管理器
 */
public class CompoundPluginRegister extends AbstractPluginRegister implements PluginRegister {

    List<PluginRegister> registerList;

    public CompoundPluginRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
        registerList = Collections.synchronizedList(new ArrayList<>());

        addRegister(new MyBatisMapperRegister(pluginManger));
        addRegister(new ComponentRegister(pluginManger));
        addRegister(new ExtensionsRegister(pluginManger));
        addRegister(new ControllerRegister(pluginManger));
        addRegister(new InterceptorRegister(pluginManger));
        addRegister(new FreeMarkerViewRegister(pluginManger));

    }

    @Override
    public void registry(String pluginId) throws Exception {
        for (PluginRegister pluginRegister : registerList) {
            pluginRegister.registry(pluginId);
        }
    }

    @Override
    public void unRegistry(String pluginId) throws Exception {
        for (PluginRegister pluginRegister : registerList) {
            pluginRegister.unRegistry(pluginId);
        }
    }

    void addRegister(PluginRegister pluginRegister) {
        registerList.add(pluginRegister);
    }

}
