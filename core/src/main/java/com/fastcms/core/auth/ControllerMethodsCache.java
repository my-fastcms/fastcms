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
package com.fastcms.core.auth;

import com.fastcms.common.auth.AuthConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.exception.FastcmsRuntimeException;
import com.fastcms.common.utils.ArrayUtils;
import com.fastcms.core.auth.condition.ParamRequestCondition;
import com.fastcms.core.auth.condition.PathRequestCondition;
import com.fastcms.utils.RequestUtils;
import com.fastcms.utils.CollectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author： wjun_java@163.com
 * @date： 2022/5/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class ControllerMethodsCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerMethodsCache.class);

	private ConcurrentMap<RequestMappingInfo, Method> methods = new ConcurrentHashMap<>();

	private final ConcurrentMap<String, List<RequestMappingInfo>> urlLookup = new ConcurrentHashMap<>();

	public Method getMethod(HttpServletRequest request) {
		String path = getPath(request);
		String httpMethod = request.getMethod();

		String urlKey = httpMethod + AuthConstants.REQUEST_PATH_SEPARATOR + path.replaceFirst(RequestUtils.getRequest().getContextPath(), "");
		List<RequestMappingInfo> requestMappingInfos = urlLookup.get(urlKey);
		if (CollectionUtils.isEmpty(requestMappingInfos)) {
			if (urlKey.endsWith("/")) {
				urlKey = urlKey.substring(0, urlKey.length()-1);
				requestMappingInfos = urlLookup.get(urlKey);
			}

			if (CollectionUtils.isEmpty(requestMappingInfos)) {
				return null;
			}

		}
		List<RequestMappingInfo> matchedInfo = findMatchedInfo(requestMappingInfos, request);
		if (CollectionUtils.isEmpty(matchedInfo)) {
			return null;
		}
		RequestMappingInfo bestMatch = matchedInfo.get(0);
		if (matchedInfo.size() > 1) {
			RequestMappingInfo.RequestMappingInfoComparator comparator = new RequestMappingInfo.RequestMappingInfoComparator();
			matchedInfo.sort(comparator);
			bestMatch = matchedInfo.get(0);
			RequestMappingInfo secondBestMatch = matchedInfo.get(1);
			if (comparator.compare(bestMatch, secondBestMatch) == 0) {
				throw new IllegalStateException(
						"Ambiguous methods mapped for '" + request.getRequestURI() + "': {" + bestMatch + ", "
								+ secondBestMatch + "}");
			}
		}
		return methods.get(bestMatch);
	}

	private String getPath(HttpServletRequest request) {
		try {
			return new URI(request.getRequestURI()).getPath();
		} catch (URISyntaxException e) {
			LOGGER.error("parse request to path error", e);
			throw new FastcmsRuntimeException(FastcmsException.NOT_FOUND, "Invalid URI");
		}
	}

	private List<RequestMappingInfo> findMatchedInfo(List<RequestMappingInfo> requestMappingInfos,
													 HttpServletRequest request) {
		List<RequestMappingInfo> matchedInfo = new ArrayList<>();
		for (RequestMappingInfo requestMappingInfo : requestMappingInfos) {
			ParamRequestCondition matchingCondition = requestMappingInfo.getParamRequestCondition()
					.getMatchingCondition(request);
			if (matchingCondition != null) {
				matchedInfo.add(requestMappingInfo);
			}
		}
		return matchedInfo;
	}

	/**
	 * find target method from this package.
	 *
	 * @param packageName package name
	 */
	public void initClassMethod(String packageName) {
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(RequestMapping.class);

		for (Class clazz : classesList) {
			initClassMethod(clazz);
		}
	}

	/**
	 * find target method from class list.
	 *
	 * @param classesList class list
	 */
	public void initClassMethod(Set<Class<?>> classesList) {
		for (Class clazz : classesList) {
			initClassMethod(clazz);
		}
	}

	/**
	 * find target method from target class.
	 *
	 * @param clazz {@link Class}
	 */
	private void initClassMethod(Class<?> clazz) {
		RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
		for (String classPath : requestMapping.value()) {
			for (Method method : clazz.getMethods()) {
				if (!method.isAnnotationPresent(RequestMapping.class)) {
					parseSubAnnotations(method, classPath);
					continue;
				}
				requestMapping = method.getAnnotation(RequestMapping.class);
				RequestMethod[] requestMethods = requestMapping.method();
				if (requestMethods.length == 0) {
					requestMethods = new RequestMethod[1];
					requestMethods[0] = RequestMethod.GET;
				}
				for (String methodPath : requestMapping.value()) {
					if (!methodPath.startsWith("/")) {
						methodPath = "/" + methodPath;
					}
					String urlKey = requestMethods[0].name() + AuthConstants.REQUEST_PATH_SEPARATOR + classPath + methodPath;
					addUrlAndMethodRelation(urlKey, requestMapping.params(), method);
				}
			}
		}
	}

	private void parseSubAnnotations(Method method, String classPath) {

		final GetMapping getMapping = method.getAnnotation(GetMapping.class);
		final PostMapping postMapping = method.getAnnotation(PostMapping.class);
		final PutMapping putMapping = method.getAnnotation(PutMapping.class);
		final DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
		final PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);

		if (getMapping != null) {
			put(RequestMethod.GET, classPath, getMapping.value(), getMapping.params(), method);
		}

		if (postMapping != null) {
			put(RequestMethod.POST, classPath, postMapping.value(), postMapping.params(), method);
		}

		if (putMapping != null) {
			put(RequestMethod.PUT, classPath, putMapping.value(), putMapping.params(), method);
		}

		if (deleteMapping != null) {
			put(RequestMethod.DELETE, classPath, deleteMapping.value(), deleteMapping.params(), method);
		}

		if (patchMapping != null) {
			put(RequestMethod.PATCH, classPath, patchMapping.value(), patchMapping.params(), method);
		}

	}

	private void put(RequestMethod requestMethod, String classPath, String[] requestPaths, String[] requestParams,
					 Method method) {
		if (ArrayUtils.isEmpty(requestPaths)) {
			String urlKey = requestMethod.name() + AuthConstants.REQUEST_PATH_SEPARATOR + classPath;
			addUrlAndMethodRelation(urlKey, requestParams, method);
			return;
		}
		for (String requestPath : requestPaths) {
			if (!requestPath.startsWith("/")) {
				requestPath = "/".concat(requestPath);
			}
			String urlKey = requestMethod.name() + AuthConstants.REQUEST_PATH_SEPARATOR + classPath + requestPath;
			addUrlAndMethodRelation(urlKey, requestParams, method);
		}
	}

	private void addUrlAndMethodRelation(String urlKey, String[] requestParam, Method method) {
		RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
		requestMappingInfo.setPathRequestCondition(new PathRequestCondition(urlKey));
		requestMappingInfo.setParamRequestCondition(new ParamRequestCondition(requestParam));
		List<RequestMappingInfo> requestMappingInfos = urlLookup.get(urlKey);
		if (requestMappingInfos == null) {
			urlLookup.putIfAbsent(urlKey, new ArrayList<>());
			requestMappingInfos = urlLookup.get(urlKey);
		}
		requestMappingInfos.add(requestMappingInfo);
		methods.put(requestMappingInfo, method);
	}

}
