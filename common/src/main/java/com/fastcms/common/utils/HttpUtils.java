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
package com.fastcms.common.utils;

import com.fastcms.common.http.*;
import com.fasterxml.jackson.databind.JavaType;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class HttpUtils {

	static final HttpClientRequest requestClient;

	static final Map<String, ResultHandler> responseHandlerMap = new HashMap<>();

	static final int TIMEOUT = 5000;

	static final String STRING_TYPE = "java.lang.String";

	static final String REST_RESULT_TYPE = "com.fastcms.common.model.RestResult";

	static final String DEFAULT_BEAN_TYPE = "default_bean_handler";

	static final String GET = "GET";

	static final String POST = "POST";

	static {
		requestClient = new JdkHttpClientRequest(HttpClientConfig.builder().setConTimeOutMillis(TIMEOUT).setReadTimeOutMillis(TIMEOUT >> 1).build());
		responseHandlerMap.put(STRING_TYPE, new StringResultHandler());
		responseHandlerMap.put(REST_RESULT_TYPE, new RestResultHandler());
		responseHandlerMap.put(DEFAULT_BEAN_TYPE, new BeanResultHandler());
	}

	public static <T> HttpRestResult<T> get(String url) throws Exception {
		return execute(url, GET, new RequestHttpEntity(Header.EMPTY, Query.EMPTY), String.class);
	}

	public static <T> HttpRestResult<T> get(String url, Header header, Query query, Type resultType) throws Exception {
		return execute(url, GET, new RequestHttpEntity(header, query), resultType);
	}

	public static <T> HttpRestResult<T> get(String url, HttpClientConfig config, Header header, Query query, Type resultType) throws Exception {
		RequestHttpEntity requestHttpEntity = new RequestHttpEntity(config, header, query);
		return execute(url, GET, requestHttpEntity, resultType);
	}

	public static <T> HttpRestResult<T> post(String url, Header header, Query query, Object body, Type resultType) throws Exception {
		return execute(url, POST, new RequestHttpEntity(header, query, body), resultType);
	}

	public static <T> HttpRestResult<T> postForm(String url, Header header, Query query, Map<String, String> bodyValues,  Type resultType) throws Exception {
		RequestHttpEntity requestHttpEntity = new RequestHttpEntity(header.setContentType(MediaType.APPLICATION_FORM_URLENCODED), query, bodyValues);
		return execute(url, POST, requestHttpEntity, resultType);
	}

	public static <T> HttpRestResult<T> postForm(String url, Header header, Map<String, String> bodyValues, Type resultType) throws Exception {
		RequestHttpEntity requestHttpEntity = new RequestHttpEntity(header.setContentType(MediaType.APPLICATION_FORM_URLENCODED), bodyValues);
		return execute(url, POST, requestHttpEntity, resultType);
	}

	public static <T> HttpRestResult<T> postForm(String url, HttpClientConfig config, Header header, Map<String, String> bodyValues, Type resultType) throws Exception {
		RequestHttpEntity requestHttpEntity = new RequestHttpEntity(config, header.setContentType(MediaType.APPLICATION_FORM_URLENCODED), bodyValues);
		return execute(url, POST, requestHttpEntity, resultType);
	}

	public static <T> HttpRestResult<T> postJson(String url, Header header, Query query, String body, Type responseType)
			throws Exception {
		RequestHttpEntity requestHttpEntity = new RequestHttpEntity(header.setContentType(MediaType.APPLICATION_JSON), query, body);
		return execute(url, POST, requestHttpEntity, responseType);
	}

	public static  <T> HttpRestResult<T> postJson(String url, Header header, String body, Type responseType) throws Exception {
		RequestHttpEntity requestHttpEntity = new RequestHttpEntity(header.setContentType(MediaType.APPLICATION_JSON), body);
		return execute(url, POST, requestHttpEntity, responseType);
	}

	public static <T> HttpRestResult<T> exchangeForm(String url, Header header, Query query, Map<String, String> bodyValues, String httpMethod, Type resultType) throws Exception {
		RequestHttpEntity requestHttpEntity = new RequestHttpEntity(header.setContentType(MediaType.APPLICATION_FORM_URLENCODED), query, bodyValues);
		return execute(url, httpMethod, requestHttpEntity, resultType);
	}

	public static <T> HttpRestResult<T> exchange(String url, HttpClientConfig config, Header header, Query query, Object body, String httpMethod, Type resultType) throws Exception {
		RequestHttpEntity requestHttpEntity = new RequestHttpEntity(config, header, query, body);
		return execute(url, httpMethod, requestHttpEntity, resultType);
	}

	private static <T> HttpRestResult<T> execute(String url, String httpMethod, RequestHttpEntity requestEntity, Type resultType) throws Exception {
		URI uri = buildUri(url, requestEntity.getQuery());

		ResultHandler<T> resultHandler = selectResultHandler(resultType);
		HttpClientResponse response = null;
		try {
			response = requestClient.execute(uri, httpMethod, requestEntity);
			return resultHandler.handle(response);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	private static ResultHandler selectResultHandler(Type resultType) {
		ResultHandler resultHandler = null;
		if (resultType == null) {
			resultHandler = responseHandlerMap.get(STRING_TYPE);
		}
		if (resultHandler == null) {
			JavaType javaType = JacksonUtils.constructJavaType(resultType);
			String name = javaType.getRawClass().getName();
			resultHandler = responseHandlerMap.get(name);
		}
		if (resultHandler == null) {
			resultHandler = responseHandlerMap.get(DEFAULT_BEAN_TYPE);
		}
		resultHandler.setResultType(resultType);
		return resultHandler;
	}

	private static URI buildUri(String url, Query query) throws URISyntaxException {
		if (query != null && !query.isEmpty()) {
			url = url + "?" + query.toQueryUrl();
		}
		return new URI(url);
	}

}
