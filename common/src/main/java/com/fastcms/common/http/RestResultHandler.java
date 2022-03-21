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

import com.fastcms.common.model.RestResult;
import com.fastcms.common.utils.JacksonUtils;

import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class RestResultHandler<T> extends AbstractResultHandler<T> {

	@Override
	public HttpRestResult convertResult(HttpClientResponse response, Type responseType) throws Exception {
		final Header headers = response.getHeaders();
		InputStream body = response.getBody();
		T extractBody = JacksonUtils.toObj(body, responseType);
		HttpRestResult<T> httpRestResult = convert((RestResult<T>) extractBody);
		httpRestResult.setHeader(headers);
		return httpRestResult;
	}

	private static <T> HttpRestResult<T> convert(RestResult<T> restResult) {
		HttpRestResult<T> httpRestResult = new HttpRestResult<T>();
		httpRestResult.setCode(restResult.getCode());
		httpRestResult.setData(restResult.getData());
		httpRestResult.setMessage(restResult.getMessage());
		return httpRestResult;
	}

}
