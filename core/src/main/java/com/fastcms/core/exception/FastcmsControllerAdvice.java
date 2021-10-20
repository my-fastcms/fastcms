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
package com.fastcms.core.exception;

import com.fastcms.common.exception.FastcmsException;
import com.fastcms.core.response.Response;
import com.fastcms.core.utils.RequestUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestControllerAdvice
@ControllerAdvice
public class FastcmsControllerAdvice {

	@ExceptionHandler(BindException.class)
	public ResponseEntity<String> bindExceptionHandler(BindException e) {
		return Response.fail(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		return Response.fail(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
	}

	@ExceptionHandler(FastcmsException.class)
	public ResponseEntity<String> fastcmsExceptionHandler(FastcmsException e) {
		return Response.fail(e.getMessage());
	}

	@ExceptionHandler(AuthorizationException.class)
	public Object authorizationExceptionHandler(AuthorizationException e, HttpServletRequest request) {
		if(RequestUtils.isAjaxRequest(request)) {
			return Response.fail(e.getMessage());
		}
		return new ModelAndView("/admin/unauthor");
	}

}
