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
package com.fastcms.web.filter;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.auth.ControllerMethodsCache;
import com.fastcms.web.security.JwtTokenManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author： wjun_java@163.com
 * @date： 2021/10/24
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    public static final String ACCESS_TOKEN = "accessToken";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenManager tokenManager;

    private final ControllerMethodsCache controllerMethodsCache;

    public JwtAuthTokenFilter(JwtTokenManager tokenManager, ControllerMethodsCache controllerMethodsCache) {
        this.tokenManager = tokenManager;
        this.controllerMethodsCache = controllerMethodsCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        if (request.getRequestURI().startsWith(FastcmsConstants.API_PREFIX_MAPPING)
                || request.getRequestURI().startsWith(FastcmsConstants.PLUGIN_MAPPING)) {

            final String jwt = resolveToken(request);

            if (StringUtils.isNotBlank(jwt) && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    tokenManager.validateToken(jwt);
                    Authentication authentication = this.tokenManager.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } catch (ExpiredJwtException | SignatureException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server failed," + e.getMessage());
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "not auth");
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private String resolveToken(HttpServletRequest request) {
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

}
