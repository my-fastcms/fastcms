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

import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.utils.RequestUtils;
import com.fastcms.entity.Permission;
import com.fastcms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.fastcms.common.constants.FastcmsConstants.WEB_LOGIN_CODE_CACHE_NAME;

/**
 *  @author： wjun_java@163.com
 *  * @date： 2021/10/24
 *  * @description：
 *  * @modifiedBy：
 *  * @version: 1.0
 */
@Component
public class FastcmsAuthManager implements AuthManager {

    @Autowired
    private JwtTokenManager tokenManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private AuthConfigs authConfigs;

    @Override
    public FastcmsUser login(String username, String password, String code) throws AccessException {
        final String codeKey = RequestUtils.getClientId(RequestUtils.getRequest());
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(WEB_LOGIN_CODE_CACHE_NAME).get(codeKey);
        String codeInMemory = StrUtils.isBlank (codeKey) ? "" : (valueWrapper == null) ? "" : (String) valueWrapper.get();
        if(StrUtils.isNotBlank(codeKey)) {
            cacheManager.getCache(WEB_LOGIN_CODE_CACHE_NAME).evict(codeKey);
        }

        if(StrUtils.isBlank(code) || !code.equalsIgnoreCase(codeInMemory)) {
            throw new AccessException(500, "验证码错误");
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            String token = tokenManager.createToken(authenticate.getName());

            return new FastcmsUser(authenticate.getName(), token, authConfigs.getTokenValidityInSeconds(), true);

        } catch (AuthenticationException e) {
            throw new AccessException(FastcmsException.NO_RIGHT, e.getMessage());
        }
    }

    @Override
    public void auth(Permission permission, User user) throws AccessException {

    }

}
