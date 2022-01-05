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


import com.fastcms.common.exception.AccessException;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.auth.ControllerMethodsCache;
import com.fastcms.core.auth.Secured;
import com.fastcms.core.auth.model.Permission;
import com.fastcms.core.auth.model.User;
import com.fastcms.core.auth.parser.ResourceParser;
import com.fastcms.core.utils.ExceptionUtil;
import com.fastcms.web.security.AuthManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： wjun_java@163.com
 * @date： 2021/10/24
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class AuthFilter implements Filter {

    private Log log = LogFactory.getLog(AuthFilter.class.getName());

    @Autowired
    private AuthManager authManager;

    @Autowired
    private ControllerMethodsCache methodsCache;

    private Map<Class<? extends ResourceParser>, ResourceParser> parserInstance = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        try {

            Method method = methodsCache.getMethod(req);

            if (method == null) {
                chain.doFilter(request, response);
                return;
            }

            if (method.isAnnotationPresent(Secured.class)) {

                if (log.isDebugEnabled()) {
                    log.debug("auth start, request: "+ req.getMethod() + ", " + req.getRequestURI());
                }

                Secured secured = method.getAnnotation(Secured.class);
                String action = secured.action().toString();
                String resource = secured.resource();

                if (StrUtils.isBlank(resource)) {
                    ResourceParser parser = getResourceParser(secured.parser());
                    resource = parser.parseName(req);
                }

                if (StrUtils.isBlank(resource)) {
                    throw new AccessException(FastcmsException.NO_RIGHT, "resource name invalid!");
                }

                authManager.auth(new Permission(resource, action), new User(AuthUtils.getUserId(), AuthUtils.getUsername()));

            }

            chain.doFilter(request, response);
        } catch (AccessException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getErrMsg());
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ExceptionUtil.getAllExceptionMsg(e));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server failed," + e.getMessage());
        }

    }

    private ResourceParser getResourceParser(Class<? extends ResourceParser> parseClass)
            throws IllegalAccessException, InstantiationException {
        ResourceParser parser = parserInstance.get(parseClass);
        if (parser == null) {
            parser = parseClass.newInstance();
            parserInstance.put(parseClass, parser);
        }
        return parser;
    }

}
