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

package com.fastcms.core.template;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.utils.StaticUtils;
import com.fastcms.utils.ConfigUtils;

/**
 * 生成静态化 url path
 * @author： wjun_java@163.com
 * @date： 2021/9/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface StaticPathHelper {

    /**
     * 文章访问路径
     */
    String ARTICLE_PATH = "/a/";

    /**
     * 页面访问路径
     */
    String PAGE_PATH = "/p/";

    /**
     * 分类访问路径
     */
    String CATEGORY_PATH = "/a/c";

    String getUrl();

    /**
     * 是否需要处理url
     * @param url
     * @return
     */
    default boolean isNeedProcess(String url) {
        return !url.endsWith(StaticUtils.getFakeStaticSuffix());
    }

    /**
     * 获取网站指定域名
     * @return
     */
    default String getWebSiteDomain() {
        return ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN);
    }

    /**
     * 是否需要伪静态
     * @return
     */
    default boolean isEnableFakeStatic() {
        return StaticUtils.isEnableFakeStatic();
    }

    /**
     * 获取伪静态后缀名称
     * @return
     */
    default String getFakeStaticSuffix() {
        return StaticUtils.getFakeStaticSuffix();
    }

}
