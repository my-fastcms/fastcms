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
package com.fastcms.core.auth;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class AuthUtils {

    private AuthUtils() {

    }

    public static Long getUserId() {
        return getUser() == null ? null : getUser().getUser().getId();
    }

    public static FastcmsAuthUserInfo getUser() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof FastcmsAuthUserInfo) {
            return (FastcmsAuthUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }

    public static Boolean isAdmin() {
        return getUser() == null ? false : getUser().isAdmin();
    }

    public static Boolean isLogin() {
        return getUser() != null;
    }

}
