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

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对所有 /fastcms/plugin/**的请求进行拦截，
 * 把用户token传递给plugin controller
 * @author： wjun_java@163.com
 * @date： 2022/1/1
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class PluginInterceptor implements HandlerInterceptor {

	static final String ACCESS_TOKEN = "accessToken";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		final String token = request.getParameter(ACCESS_TOKEN);
		request.setAttribute("token", token);
		return true;
	}

}
