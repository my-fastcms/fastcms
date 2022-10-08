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
package com.fastcms.core.utils;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.core.template.Template;
import com.fastcms.core.template.TemplateService;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.ConfigUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.HashSet;
import java.util.Set;

/**
 * @author： wjun_java@163.com
 * @date： 2022/9/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class StaticUtils {

	/**
	 * 是否开启伪静态
	 */
	public static String ENABLE_FAKE_STATIC = "enableFakeStatic";

	/**
	 * 静态化后缀
	 */
	public static String FAKE_STATIC_SUFFIX = "fakeStaticSuffix";


	public static boolean isEnableFakeStatic() {
		return ConfigUtils.getBool(ENABLE_FAKE_STATIC);
	}

	public static String getFakeStaticSuffix() {
		return ConfigUtils.getConfig(FAKE_STATIC_SUFFIX, ".html");
	}

	public static void registerStaticResource(ResourceHandlerRegistry registry) {
		final String uploadDir = DirUtils.getUploadDir();
		final String templateDir = DirUtils.getTemplateDir();

		Set<String> locations = new HashSet<>();
		locations.add(ResourceUtils.CLASSPATH_URL_PREFIX.concat(FastcmsConstants.TEMPLATE_STATIC));
		locations.add(ResourceUtils.FILE_URL_PREFIX + uploadDir);
		registry.addResourceHandler("/**").addResourceLocations(locations.toArray(new String[]{}));

		TemplateService templateService = ApplicationUtils.getBean(TemplateService.class);
		for (Template template : templateService.getTemplateList()) {
			locations = new HashSet<>();
			locations.add(ResourceUtils.FILE_URL_PREFIX + templateDir + template.getPath() + FastcmsConstants.TEMPLATE_STATIC);
			registry.addResourceHandler(template.getPath().concat("**")).addResourceLocations(locations.toArray(new String[]{}));
		}
	}

}
