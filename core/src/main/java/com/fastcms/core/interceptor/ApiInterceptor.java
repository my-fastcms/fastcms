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

import com.alibaba.fastjson.JSON;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.jwt.ApiToken;
import com.fastcms.core.response.Response;
import com.fastcms.utils.PasswordUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

	@Value("${jwt.secret}")
	private String jwtSecret;
	@Value("${md5.sign.key}")
	private String md5Key;

	/**
	 * api 的有效时间，默认为 10 分钟
	 */
	private static final long TIMEOUT = 10 * 60 * 1000;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if(request.getRequestURI().contains("/upload/doUpload")) {
			//放行api文件上传
			return true;
		}

		String appId = request.getParameter("appId");
		if (StringUtils.isBlank(appId)) {
			backError(response,"在Url中获取到appId内容，请注意Url是否正确。");
			return false;
		}

		String sign = request.getParameter("sign");
		if (StringUtils.isBlank(sign)) {
			backError(response, "签名数据不能为空，请提交 sign 数据。");
			return false;
		}

		Long time = Long.valueOf(request.getParameter("t"));
		if (time == null) {
			backError(response, "时间参数不能为空，请提交 t 参数数据。");
			return false;
		}

		// 时间验证，可以防止重放攻击
		if (Math.abs(System.currentTimeMillis() - time) > TIMEOUT) {
			backError(response, "请求超时，请重新请求。");
			return false;
		}

		String localSign = createLocalSign(request);
		if (sign.equals(localSign) == false) {
			backError(response, "数据签名错误。");
			return false;
		}

		if(!(handler instanceof HandlerMethod)){
			return true;
		}

		HandlerMethod handlerMethod=(HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		if(AnnotationUtils.getAnnotation(method, ApiToken.class) != null) {
			String token = request.getHeader("Jwt");

			if(StringUtils.isNotBlank(token)) {
				Map userData = parseJwtToken(token);
				if(userData == null) {
					backLoginError(response, "user data is null");
					return false;
				}

				final Object userId = userData.get(FastcmsConstants.USER_ID);
				if(userId == null){
					backLoginError(response, "jwt userId is null");
					return false;
				}

				request.setAttribute(FastcmsConstants.OPEN_ID, userData.get(FastcmsConstants.OPEN_ID));
				request.setAttribute(FastcmsConstants.USER_ID, userId);
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	private String createLocalSign(HttpServletRequest request) {

		String queryString = request.getQueryString();
		Map<String, String[]> params = request.getParameterMap();

		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		StringBuilder query = new StringBuilder();
		for (String key : keys) {
			if ("sign".equals(key)) {
				continue;
			}

			//只对get参数里的进行签名
			if (StringUtils.isNotBlank(queryString) && queryString.indexOf(key + "=") == -1) {
				continue;
			}

			String value = params.get(key)[0];
			if(StringUtils.isNotBlank(value) && !"undefined".equalsIgnoreCase(value))
				query.append(key).append(value);
		}
		query.append(md5Key);

		return PasswordUtils.md5(query.toString());
	}

	private SecretKey generalKey() {
		byte[] encodedKey = DatatypeConverter.parseBase64Binary(jwtSecret);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	private Map parseJwtToken(String token) {
		SecretKey secretKey = generalKey();
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token).getBody();

			String jsonString = claims.getSubject();
			if (StringUtils.isBlank(jsonString)) {
				return null;
			}

			return JSON.parseObject(jsonString, HashMap.class);

		} catch (SignatureException | MalformedJwtException e) {
			// don't trust the JWT!
			// jwt 签名错误或解析错误，可能是伪造的，不能相信
			e.printStackTrace();
		} catch (ExpiredJwtException e) {
			// jwt 已经过期
			e.printStackTrace();
		} catch (Throwable ex) {
			//其他错误
			ex.printStackTrace();
		}

		return null;
	}

	private void backError(HttpServletResponse response, String message) {
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			writer = response.getWriter();
			writer.print(JSON.toJSON(Response.fail(message)));
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void backLoginError(HttpServletResponse response, String message) {
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			writer = response.getWriter();
			writer.print(JSON.toJSON(Response.fail(999, message)));
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
