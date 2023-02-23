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
package com.fastcms.core.site;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2023/2/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface SiteManager<S extends Site> {

    /**
     * 从请求request中匹配站点数据
     * @param request
     * @return
     */
    default S getSite(HttpServletRequest request) {
        return null;
    }

    /**
     * 加载自定义Site数据
     * @return
     */
    List<S> loadSites();

}
