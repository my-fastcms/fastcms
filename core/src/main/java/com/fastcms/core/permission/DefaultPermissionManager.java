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
package com.fastcms.core.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.exception.FastcmsRuntimeException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.Permission;
import com.fastcms.service.IPermissionService;
import com.fastcms.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
@Service
public class DefaultPermissionManager implements PermissionManager {

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IRoleService roleService;

	final List<Class> controllerClassList = Collections.synchronizedList(new ArrayList<>());
	final Map<String, Permission> adminPermissionMaps = Collections.synchronizedMap(new HashMap<>());
	final Map<String, Permission> uCenterPermissionMaps = Collections.synchronizedMap(new HashMap<>());
	final List<Permission> needAddAdminPermissionList = Collections.synchronizedList(new ArrayList<>());

	@Override
	@Transactional
	public void initSystemPermissions(Class appClass) throws Exception {

		if(checkInit(FastcmsConstants.SYSTEM_MODULE_ID)) return;

		try {

			Permission modulePermission = initModulePermission(FastcmsConstants.SYSTEM_MODULE_ID, SystemModule.getValue(FastcmsConstants.SYSTEM_MODULE_ID));

			initSystemControllerClasses(appClass);

			initSystemPermissions();

			processNeedAddPermissions(modulePermission);

//			prepareSystemModulePermission(FastcmsConstants.SYSTEM_MODULE_ID);

		} catch (Exception e) {
			e.printStackTrace();
			throw new FastcmsRuntimeException(FastcmsException.SERVER_ERROR, e.getMessage());
		} finally {
			controllerClassList.clear();
			adminPermissionMaps.clear();
			uCenterPermissionMaps.clear();
		}

	}

	@Override
	public void refreshSystemPermissions(Class appClass) throws Exception {
		deletePermissions(FastcmsConstants.SYSTEM_MODULE_ID);
//		pluginService.removePlugin(fastcmsConstants.SYSTEM_MODULE_ID);
		initSystemPermissions(appClass);
	}

	@Override
	@Transactional
	public void deletePermissions(String moduleId) {
		//删除插件菜单以及菜单授权相关信息
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("module_id", moduleId);
		List<Permission> permissionList = permissionService.list(queryWrapper);
		if(permissionList != null) {
			deletePermissions(permissionList);
		}
	}

	Permission getModulePermission(String moduleId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("module_id", moduleId);
		queryWrapper.eq("type", IPermissionService.PermissionType.MODULE.getType());
		return permissionService.getOne(queryWrapper);
	}

	boolean checkInit(String moduleId) {
		return true;
	}

	Permission initModulePermission(String moduleId, String moduleName) {
		Permission permission = getModulePermission(moduleId);
		if(permission == null) {
			permission = new Permission();
			permission.setModuleId(moduleId);
			permission.setType(IPermissionService.PermissionType.MODULE.getType());
			permission.setName(moduleName);
			permission.setSortNum(FastcmsConstants.SYSTEM_MODULE_ID.equalsIgnoreCase(moduleId) ? Integer.MAX_VALUE : 1);
			if(FastcmsConstants.PLUGIN_MODULE_ID.equalsIgnoreCase(moduleId)) {
				permission.setSortNum(10000);
			}
			permissionService.saveOrUpdate(permission);
		}
		return permission;
	}

	void initSystemControllerClasses(Class appClass) throws Exception {
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver(appClass.getClassLoader());
		String pluginBasePath = ClassUtils.classPackageAsResourcePath(appClass);
		//扫描controller包
		Resource[] resources = pathMatchingResourcePatternResolver.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + pluginBasePath + "/controller/**/*.class");

		for (Resource resource : resources) {
			if(resource.isReadable()) {
				MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
				Class clazz = ClassUtils.getDefaultClassLoader().loadClass(metadataReader.getAnnotationMetadata().getClassName());
				controllerClassList.add(clazz);
			}
		}
	}

	void initSystemPermissions() {

		for (Class clazz : controllerClassList) {
			if(clazz.getAnnotation(Controller.class) != null || clazz.getAnnotation(RestController.class) != null) {
				initAdminPermission(FastcmsConstants.SYSTEM_MODULE_ID, clazz);
				initUCenterPermission(FastcmsConstants.SYSTEM_MODULE_ID, clazz);
				for (Method method : clazz.getMethods()) {
					initAdminPermission(FastcmsConstants.SYSTEM_MODULE_ID, clazz, method);
					initUCenterPermission(FastcmsConstants.SYSTEM_MODULE_ID, clazz, method);
				}
			}
		}

	}

	void initAdminPermission(String moduleId, Class clazz, Method method) {
		AdminMenu adminMenu = AnnotationUtils.getAnnotation(clazz, AdminMenu.class);
		if(adminMenu == null) return;

		if(StrUtils.isBlank(adminMenu.name())) {
			throw new RuntimeException(String.format("admin menu name is null ? please check it !!! className {%s}", clazz.getName()));
		}

		Permission permission = new Permission();
		permission.setName(adminMenu.name());
		permission.setIcon(adminMenu.icon());
		permission.setType(adminMenu.type());
		permission.setSortNum(adminMenu.sort());
		permission.setClassName(clazz.getName());
		permission.setCategory(Permission.CATEGORY_ADMIN);
		permission.setModuleId(moduleId);

		if(method == null) {
			if(adminPermissionMaps.putIfAbsent(adminMenu.name(), permission) != null)
				throw new RuntimeException(String.format("menu name is exist ? please check it !!! menuName {%s}", adminMenu.name()));
		}else {
			AdminMenu annotation = AnnotationUtils.getAnnotation(method, AdminMenu.class);
			if(annotation != null) {
				String menuUrl = getPermissionUrl(clazz, method);
				if(StringUtils.isBlank(menuUrl))
					throw new RuntimeException(String.format("menu url is null ? please check it !!! menuName {%s}", annotation.name()));

				if(StrUtils.isBlank(annotation.name())) {
					throw new RuntimeException(String.format("menu name is null ? please check it !!! methodName {%s}", method.getName()));
				}

				permission.setName(annotation.name());
				permission.setSortNum(annotation.sort());
				permission.setIcon(annotation.icon());
				permission.setType(annotation.type());
				permission.setUrl(menuUrl);
				permission.setPermInfo(getPermissionPermInfo(method));
				if(adminPermissionMaps.putIfAbsent(menuUrl, permission) != null)
					throw new RuntimeException(String.format("menu url is exist ? please check it !!! menuUrl {%s}", menuUrl));
			}
		}

	}

	void initAdminPermission(String moduleId, Class clazz) {
		initAdminPermission(moduleId, clazz, null);
	}

	void initUCenterPermission(String moduleId, Class clazz, Method method) {
		UcenterMenu ucenterMenu = AnnotationUtils.getAnnotation(clazz, UcenterMenu.class);
		if(ucenterMenu == null) return;
		Permission permission = new Permission();
		permission.setName(ucenterMenu.name());
		permission.setIcon(ucenterMenu.icon());
		permission.setType(ucenterMenu.type());
		permission.setSortNum(ucenterMenu.sort());
		permission.setClassName(clazz.getName());
		permission.setCategory(Permission.CATEGORY_CENTER);
		permission.setModuleId(moduleId);
		if(method == null) {
			if(uCenterPermissionMaps.putIfAbsent(ucenterMenu.name(), permission) != null)
				throw new RuntimeException(String.format("menu name is exist ? please check it !!! menuName {%s}", ucenterMenu.name()));
		}else {
			UcenterMenu annotation = AnnotationUtils.getAnnotation(method, UcenterMenu.class);
			if(annotation != null) {
				String menuUrl = getPermissionUrl(clazz, method);
				if(StringUtils.isBlank(menuUrl))
					throw new RuntimeException(String.format("menu url is null ? please check it !!! menuName {%s}", annotation.name()));
				permission.setName(annotation.name());
				permission.setSortNum(annotation.sort());
				permission.setType(annotation.type());
				permission.setUrl(menuUrl);
				permission.setPermInfo(getPermissionPermInfo(method));
				if(uCenterPermissionMaps.putIfAbsent(menuUrl, permission) != null)
					throw new RuntimeException(String.format("menu url is exist ? please check it !!! menuUrl {%s}", menuUrl));
			}
		}
	}

	void initUCenterPermission(String moduleId, Class clazz) {
		initUCenterPermission(moduleId, clazz, null);
	}

	String getPermissionUrl(Class clazz, Method method) {

		RequestMapping requestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
		if(requestMapping == null || requestMapping.value() == null || requestMapping.value().length<=0) return null;

		String methodUrl = "";
		RequestMapping methodRequestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		if(methodRequestMapping != null && methodRequestMapping.value() != null && methodRequestMapping.value().length>0) {
			methodUrl = methodRequestMapping.value()[0];
		}

		GetMapping getMapping = AnnotationUtils.getAnnotation(method, GetMapping.class);
		if(getMapping != null && getMapping.value() != null && getMapping.value().length>0) {
			methodUrl = getMapping.value()[0];
		}

		PostMapping postMapping = AnnotationUtils.getAnnotation(method, PostMapping.class);
		if(postMapping != null && postMapping.value() != null && postMapping.value().length>0) {
			methodUrl = postMapping.value()[0];
		}

		if(StringUtils.isBlank(methodUrl)) return null;

		return "/" + requestMapping.value()[0] + "/" + methodUrl;
	}

	String getPermissionPermInfo(Method method) {
//		RequiresPermissions annotation = AnnotationUtils.getAnnotation(method, RequiresPermissions.class);
//		return annotation == null ? null : Arrays.stream(annotation.value()).collect(Collectors.joining(","));
		return null;
	}

	void deletePermissions(List<Permission> permissionList) {
		List<Long> allPermissionIds = permissionList.stream().map(item -> item.getId()).collect(Collectors.toList());
		permissionService.removeByIds(allPermissionIds);
		permissionService.deleteRolePermissionByPermission(permissionList);
	}

	void processNeedAddPermissions(Permission modulePermission) {

		List<Permission> needCenterAddList = new ArrayList<>();
		uCenterPermissionMaps.values().forEach(permission -> {
			if(StringUtils.isBlank(permission.getUrl())) {
				permission.setParentId(modulePermission.getId());
				permissionService.save(permission);
				List<Permission> collect = uCenterPermissionMaps.values().stream()
						.filter(item -> item.getClassName().equals(permission.getClassName()) && StringUtils.isNotBlank(item.getUrl()))
						.map(item -> {
							item.setParentId(permission.getId());
							return item;
						})
						.collect(Collectors.toList());
				needCenterAddList.addAll(collect);
			}
		});

		List<Permission> needAdminAddList = new ArrayList<>();
		adminPermissionMaps.values().forEach(permission -> {
			if(StringUtils.isBlank(permission.getUrl())) {
				permission.setParentId(modulePermission.getId());
				permissionService.save(permission);
				needAdminAddList.add(permission);
				List<Permission> collect = adminPermissionMaps.values().stream()
						.filter(item -> item.getClassName().equals(permission.getClassName()) && StringUtils.isNotBlank(item.getUrl()))
						.map(item -> {
							item.setParentId(permission.getId());
							return item;
						})
						.collect(Collectors.toList());
				needAddAdminPermissionList.addAll(collect);
			}
		});

		if(!needCenterAddList.isEmpty()) {

			try {
				permissionService.saveOrUpdateBatch(needCenterAddList);
			} finally {
				needCenterAddList.clear();
			}
		}

		if(!needAddAdminPermissionList.isEmpty()) {
			try {
				permissionService.saveOrUpdateBatch(needAddAdminPermissionList);
				//保存权限到系统管理员角色
				if(!FastcmsConstants.SYSTEM_MODULE_ID.equalsIgnoreCase(modulePermission.getModuleId())
				&& !FastcmsConstants.PLUGIN_MODULE_ID.equalsIgnoreCase(modulePermission.getModuleId())) {
					needAddAdminPermissionList.add(modulePermission);
				}
				needAddAdminPermissionList.addAll(needAdminAddList);
				roleService.saveRolePermissionOfPlugin(FastcmsConstants.ADMIN_ROLE_ID, needAddAdminPermissionList.stream().map(item -> item.getId()).collect(Collectors.toList()));
			}finally {
				needAddAdminPermissionList.clear();
			}
		}
	}

}
