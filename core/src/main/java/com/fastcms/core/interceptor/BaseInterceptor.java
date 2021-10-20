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
package com.fastcms.core.interceptor;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.utils.RequestUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/5
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class BaseInterceptor implements HandlerInterceptor {

	static final String[] exclude = new String[] {"doLogin", "doRegister"};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(FastcmsConstants.STATIC_PATH_KEY, FastcmsConstants.STATIC_RESOURCE_PATH);

		if(!SecurityUtils.getSubject().isAuthenticated()) {
			if(!isExclude(request) && RequestUtils.isAjaxRequest(request)) {
				response.sendError(HttpStatus.FORBIDDEN.value());
				return false;
			}
		}

		return true;
	}

	boolean isExclude(HttpServletRequest request) {
		for (String s : exclude) {
			if(request.getRequestURI().contains(s)) return true;
		}
		return false;
	}

}
