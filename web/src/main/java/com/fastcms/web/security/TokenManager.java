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
package com.fastcms.web.security;

import com.fastcms.core.auth.FastcmsAuthUserInfo;
import com.fastcms.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author： wjun_java@163.com
 * @date： 2023/02/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface TokenManager {

    /**
     * 根据账号以及权限信息生成token
     * @param user
     * @param authorities
     * @return
     */
    String createToken(User user, Collection<? extends GrantedAuthority> authorities);

    /**
     * 根据用户信息创建token
     * @param user
     * @return
     */
    String createToken(User user);

    /**
     * 根据token获取授权信息
     * @param token
     * @return
     */
    Authentication getAuthentication(String token);

    /**
     * 验证token有效期
     * @param token
     */
    void validateToken(String token);

    /**
     * 根据授权创建包含Token的授权用户信息
     * @param fastcmsAuthUserInfo
     * @return
     */
    FastcmsUser createTokenUser(FastcmsAuthUserInfo fastcmsAuthUserInfo);

    /**
     * 从请求中获取token
     * @param request
     * @return
     */
    String resolveToken(HttpServletRequest request);

}
