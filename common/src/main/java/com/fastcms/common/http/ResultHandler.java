package com.fastcms.common.http;

import java.lang.reflect.Type;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface ResultHandler<T> {

	/**
	 * set response type.
	 *
	 * @param responseType responseType
	 */
	void setResultType(Type responseType);

	/**
	 * handle response convert to HttpRestResult.
	 *
	 * @param response http response
	 * @return HttpRestResult {@link HttpRestResult}
	 * @throws Exception ex
	 */
	HttpRestResult<T> handle(HttpClientResponse response) throws Exception;

}
