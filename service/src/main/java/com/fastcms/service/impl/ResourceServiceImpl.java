package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.Resource;
import com.fastcms.mapper.ResourceMapper;
import com.fastcms.service.IResourceService;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.CollectionUtils;
import com.fastcms.utils.I18nUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 接口资源
 * @author wjun_java@163.com
 * @since 2022-05-01
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void syncResources() {

		getBaseMapper().deleteAll();

		List<Resource> resourceList = new ArrayList<>();
		RequestMappingHandlerMapping requestMappingHandlerMapping = ApplicationUtils.getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
			HandlerMethod handlerMethod = handlerMethods.get(requestMappingInfo);
			if (handlerMethod.getMethod().isAnnotationPresent(Secured.class)) {
				Set<PathPattern> patterns = requestMappingInfo.getPathPatternsCondition().getPatterns();
				if (CollectionUtils.isNotEmpty(patterns)) {
					Secured secured = handlerMethod.getMethod().getAnnotation(Secured.class);
					String message = I18nUtils.getMessage(secured.name());
					if (StrUtils.isNotBlank(message)) {
						resourceList.add(new Resource(I18nUtils.getMessage(secured.name()), secured.resource(), secured.action().toString()));
					}
				}
			}
		}

		saveBatch(resourceList);
	}

	@Override
	public List<String> getUserResourceList(Long userId) {
		return getBaseMapper().getUserResourceList(userId);
	}

}
