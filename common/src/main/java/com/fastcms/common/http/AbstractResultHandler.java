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
package com.fastcms.common.http;

import com.fastcms.common.utils.IoUtils;

import java.lang.reflect.Type;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractResultHandler<T> implements ResultHandler<T> {

	private Type responseType;

	public static final int SC_OK = 200;
	public static final int SC_BAD_REQUEST = 400;
	public static final int SC_UNAUTHORIZED = 401;
	public static final int SC_FORBIDDEN = 403;
	public static final int SC_NOT_FOUND = 404;
	public static final int SC_INTERNAL_SERVER_ERROR = 500;

	@Override
	public final void setResultType(Type responseType) {
		this.responseType = responseType;
	}

	@Override
	public final HttpRestResult<T> handle(HttpClientResponse response) throws Exception {
		if (SC_OK != response.getStatusCode()) {
			return handleError(response);
		}
		return convertResult(response, this.responseType);
	}

	private HttpRestResult<T> handleError(HttpClientResponse response) throws Exception {
		Header headers = response.getHeaders();
		String message = IoUtils.toString(response.getBody(), headers.getCharset());
		return new HttpRestResult<T>(headers, response.getStatusCode(), null, message);
	}

	/**
	 * Abstract convertResult method, Different types of converters for expansion.
	 *
	 * @param response     http client response
	 * @param responseType responseType
	 * @return HttpRestResult
	 * @throws Exception ex
	 */
	public abstract HttpRestResult<T> convertResult(HttpClientResponse response, Type responseType) throws Exception;

}
