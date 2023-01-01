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

package com.fastcms.core.directive;

import com.fastcms.plugin.FastcmsPluginManager;
import freemarker.core.Environment;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 是否安装某个插件
 * @author： wjun_java@163.com
 * @date： 2021/5/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("hasPlugin")
public class HasPluginDirective extends BaseDirective {

    private static final String PLUGIN_ID = "pluginId";

    @Autowired
    private FastcmsPluginManager pluginManager;

    @Override
    public Object doExecute(Environment env, Map params) {
        final String pluginId = getStr(PLUGIN_ID, params);
        PluginWrapper plugin = pluginManager.getPlugin(pluginId);
        return plugin != null;
    }

}
