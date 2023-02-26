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
import com.fastcms.entity.UserOpenid;
import com.fastcms.service.IUserService;
import com.fastcms.utils.ApplicationUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： wjun_java@163.com
 * @date： 2023/02/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class DelegatingTokenManager implements TokenManager, ApplicationListener<ApplicationStartedEvent> {

    private static final Map<String, TokenManager> tokenManagerMap = new ConcurrentHashMap<>();

    static final String USER_NAME = "username";

    @Autowired
    protected AuthConfigs authConfigs;

    @Autowired
    DefaultTokenManager defaultTokenManager;

    @Override
    public String createToken(User user, Collection<? extends GrantedAuthority> authorities) {
        return getTokenManager(user).createToken(user, authorities);
    }

    @Override
    public String createToken(User user) {
        return getTokenManager(user).createToken(user);
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(authConfigs.getSecretKeyBytes()).build().parseClaimsJws(token).getBody();
        String username = (String) claims.get(USER_NAME);
        User user = ApplicationUtils.getBean(IUserService.class).getByUsername(username);
        return getTokenManager(user).getAuthentication(token);
    }

    @Override
    public void validateToken(String token) {
        defaultTokenManager.validateToken(token);
    }

    @Override
    public FastcmsUser createTokenUser(FastcmsAuthUserInfo fastcmsAuthUserInfo) {
        return getTokenManager(fastcmsAuthUserInfo.getUser()).createTokenUser(fastcmsAuthUserInfo);
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return defaultTokenManager.resolveToken(request);
    }

    TokenManager getTokenManager(User user) {
        UserOpenid userOpenid = ApplicationUtils.getBean(IUserService.class).getUserOpenid(user);
        if (userOpenid != null) {
            TokenManager tokenManager = getTokenManager(userOpenid.getType());
            if (tokenManager != null) {
                return tokenManager;
            }
        }
        return defaultTokenManager;
    }

    TokenManager getTokenManager(String registrationId) {
        return tokenManagerMap.get(registrationId);
    }

    public void addTokenManager(String registrationId, TokenManager tokenManager) {
        if (tokenManagerMap.get(registrationId) != null) {
            removeTokenManager(registrationId);
        }
        tokenManagerMap.put(registrationId, tokenManager);
    }

    public void removeTokenManager(String registrationId) {
        tokenManagerMap.remove(registrationId);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Map<String, AbstractTokenManager> abstractTokenManagerMap = event.getApplicationContext().getBeansOfType(AbstractTokenManager.class);
        abstractTokenManagerMap.values().forEach(item -> addTokenManager(item.getRegistrationId(), item));
    }

}
