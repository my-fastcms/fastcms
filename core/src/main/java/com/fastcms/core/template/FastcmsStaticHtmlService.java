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

import org.springframework.core.Ordered;

/**
 * Fastcms静态化接口
 * @author： wjun_java@163.com
 * @date： 2021/9/30
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface FastcmsStaticHtmlService extends Ordered {

    /**
     * 返回是否开启静态化功能
     * @return
     */
    boolean isEnable();

    /**
     * 获取生成的静态文件后缀名
     * @return
     */
    String getFileSuffix();

    /**
     * 获取文章静态化访问路径
     * @return
     */
    String getArticleStaticPath();

    /**
     * 获取页面静态化访问路径
     * @return
     */
    String getPageStaticPath();

    /**
     * 获取分类静态化路径
     * @return
     */
    String getCategoryStaticPath();

    /**
     * 获取标签静态化路径
     * @return
     */
    String getTagStaticPath();

}
