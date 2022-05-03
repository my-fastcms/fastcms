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

import com.fastcms.common.auth.Secured;
import com.fastcms.common.auth.model.Permission;
import com.fastcms.common.auth.model.User;
import com.fastcms.common.auth.parser.ResourceParser;
import com.fastcms.common.exception.AccessException;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.web.security.AuthManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * api权限校验拦截器
 * @author： wjun_java@163.com
 * @date： 2022/1/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class AuthInterceptor implements HandlerInterceptor {

	private Log log = LogFactory.getLog(AuthInterceptor.class.getName());

	private AuthManager authManager = ApplicationUtils.getBean(AuthManager.class);

	private Map<Class<? extends ResourceParser>, ResourceParser> parserInstance = new ConcurrentHashMap<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod == false) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		if (!method.isAnnotationPresent(Secured.class)) {
			return true;
		}

		if (log.isDebugEnabled()) {
			log.debug("auth start, request: "+ request.getMethod() + ", " + request.getRequestURI());
		}

		try {
			Secured secured = method.getAnnotation(Secured.class);
			final String action = secured.action().toString();
			String resource = secured.resource();

			if (StrUtils.isBlank(resource)) {
				ResourceParser parser = getResourceParser(secured.parser());
				resource = parser.parseName(request);
			}

			if (StrUtils.isBlank(resource)) {
				throw new AccessException(FastcmsException.NO_RIGHT, "resource name invalid!");
			}

			authManager.auth(new Permission(resource, action), new User(AuthUtils.getUserId()));
			return true;
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return false;
		}

	}

	private ResourceParser getResourceParser(Class<? extends ResourceParser> parseClass) throws IllegalAccessException, InstantiationException {
		ResourceParser parser = parserInstance.get(parseClass);
		if (parser == null) {
			parser = parseClass.newInstance();
			parserInstance.put(parseClass, parser);
		}
		return parser;
	}


}
