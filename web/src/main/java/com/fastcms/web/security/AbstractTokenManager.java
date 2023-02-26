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
import com.fastcms.oauth2.registration.FastcmsOAuth2ClientRegistration;
import com.fastcms.utils.ApplicationUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2023/02/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractTokenManager implements TokenManager, FastcmsOAuth2ClientRegistration, InitializingBean {

    protected static final String AUTHORITIES_KEY = "auth";

    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";

    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    protected AuthConfigs authConfigs;

    @Override
    public String createToken(User user, Collection<? extends GrantedAuthority> authorities) {
        long now = System.currentTimeMillis();
        Date validity;
        validity = new Date(now + authConfigs.getTokenValidityInSeconds() * 1000L);
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, user.getId());
        claims.put(USER_NAME, user.getUserName());
        if (authorities != null) {
            claims.put(AUTHORITIES_KEY, StringUtils.join(authorities.toArray(), ","));
        }
        return Jwts.builder().setClaims(claims).setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(authConfigs.getSecretKeyBytes()), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public String createToken(User user) {
        return createToken(user, null);
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(authConfigs.getSecretKeyBytes()).build().parseClaimsJws(token).getBody();
        Collection<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES_KEY));
        String username = (String) claims.get(USER_NAME);
        return doGetAuthentication(username, authorities);
    }

    public abstract Authentication doGetAuthentication(String userName, Collection<GrantedAuthority> authorities);

    @Override
    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(authConfigs.getSecretKeyBytes()).build().parseClaimsJws(token);
    }

    @Override
    public FastcmsUser createTokenUser(FastcmsAuthUserInfo fastcmsAuthUserInfo) {
        String token = createToken(fastcmsAuthUserInfo.getUser(), fastcmsAuthUserInfo.getAuthorities());
        return new FastcmsUser(fastcmsAuthUserInfo.getUser(), token, authConfigs.getTokenValidityInSeconds(), fastcmsAuthUserInfo.isAdmin(), fastcmsAuthUserInfo.hasRole());
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(bearerToken)) {
            return bearerToken.startsWith(TOKEN_PREFIX) ? bearerToken.substring(TOKEN_PREFIX.length()) : bearerToken;
        }

        final String token = request.getParameter(ACCESS_TOKEN);
        if (StringUtils.isNotBlank(token)) {
            return token.startsWith(TOKEN_PREFIX) ? token.substring(TOKEN_PREFIX.length()) : token;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this instanceof DefaultTokenManager == false) {
            ApplicationUtils.getBean(DelegatingTokenManager.class).addTokenManager(getRegistrationId(), this);
        }
    }

}
