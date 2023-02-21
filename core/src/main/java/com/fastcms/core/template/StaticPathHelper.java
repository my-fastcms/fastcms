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
import com.fastcms.core.site.SiteContextHolder;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.ConfigUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 生成静态化 url path
 * @author： wjun_java@163.com
 * @date： 2021/9/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@FunctionalInterface
public interface StaticPathHelper {

    /**
     * 获取访问的url
     * @return
     */
    String getUrl();

    /**
     * 获取网站指定域名
     * @return
     */
    @JsonIgnore
    default String getWebSiteDomain() {
        if (SiteContextHolder.getSite() != null) {
            return SiteContextHolder.getSite().getDomain();
        }
        return ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN);
    }

    /**
     * 是否需要伪静态
     * @return
     */
    @JsonIgnore
    default boolean isEnableFakeStatic() {
        return ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).isFakeStaticEnable();
    }

    /**
     * 是否启用静态化
     * @return
     */
    @JsonIgnore
    default boolean isEnableStatic() {
        return ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).isEnable();
    }

    /**
     * 是否开启静态化
     * 伪静态 + 真实静态化
     * @return
     */
    @JsonIgnore
    default boolean isEnable() {
        return isEnableStatic() || isEnableFakeStatic();
    }

    /**
     * 获取静态文件后缀名称
     * @return
     */
    @JsonIgnore
    default String getStaticSuffix() {
        return ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).getFileSuffix();
    }

    /**
     * 获取文章静态化访问路径
     * @return
     */
    @JsonIgnore
    default String getArticleStaticPath() {
        return ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).getArticleStaticPath();
    }

    /**
     * 获取页面静态化访问路径
     * @return
     */
    @JsonIgnore
    default String getPageStaticPath() {
        return ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).getPageStaticPath();
    }

    /**
     * 获取分类页面静态化访问路径
     * @return
     */
    @JsonIgnore
    default String getCategoryStaticPath() {
        return ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).getCategoryStaticPath();
    }

    @JsonIgnore
    default String getTagStaticPath() {
        return ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).getTagStaticPath();
    }

    /**
     * 获取当前模板
     * @return
     */
    @JsonIgnore
    default Template getTemplate() {
        return ApplicationUtils.getBean(DefaultTemplateService.class).getCurrTemplate();
    }

}
