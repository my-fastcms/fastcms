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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author： wjun_java@163.com
 * @date： 2023/02/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class DelegatingTokenManager implements TokenManager {

    @Autowired
    DefaultTokenManager defaultTokenManager;

    @Override
    public String createToken(User user, Collection<? extends GrantedAuthority> authorities) {
        return defaultTokenManager.createToken(user, authorities);
    }

    @Override
    public String createToken(User user) {
        return defaultTokenManager.createToken(user);
    }

    @Override
    public Authentication getAuthentication(String token) {
        return defaultTokenManager.getAuthentication(token);
    }

    @Override
    public void validateToken(String token) {
        defaultTokenManager.validateToken(token);
    }

    @Override
    public FastcmsUser createTokenUser(FastcmsAuthUserInfo fastcmsAuthUserInfo) {
        return defaultTokenManager.createTokenUser(fastcmsAuthUserInfo);
    }

}
