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
package com.fastcms.utils;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.plugin.FastcmsPluginManager;

import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class PluginUtils {

    public static final String PAGE_DATA_ATTR = "pageData";

    private PluginUtils() {

    }

    /**
     * 获取插件扩展
     * @param type
     * @param <T>
     * @return
     */
    public static <T>List<T> getExtensions(Class<T> type) {
        return getPluginManager().getExtensions(type);
    }

    /**
     * 获取系统插件管理器
     * @return
     */
    public static FastcmsPluginManager getPluginManager() {
        return ApplicationUtils.getBean(FastcmsPluginManager.class);
    }

    /**
     * 获取插件配置界面url host
     * @return
     */
    public static String getConfigUrlHost() {
        return FastcmsConstants.PLUGIN_MAPPING;
    }

}
