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

import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

/**
 * @author： wjun_java@163.com
 * @date： 2023/2/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class SiteContextHolder {

    private static final ThreadLocal<Site> siteContextHolder = new NamedThreadLocal<>("SiteContext");

    private SiteContextHolder() {
    }

    public static void resetSite() {
        siteContextHolder.remove();
    }

    public static void setSite(@Nullable Site site) {
        if (site == null) {
            resetSite();
        }
        else {
            siteContextHolder.set(site);
        }
    }

    @Nullable
    public static Site getSite() {
        return siteContextHolder.get();
    }

}
