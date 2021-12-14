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
package com.fastcms.plugin.register;

import com.fastcms.plugin.PluginBase;
import com.fastcms.plugin.UnLoad;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/12
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class ResourceLoaderPluginRegister extends AbstractPluginRegister implements PluginRegister {

	@Override
	public void initialize() throws Exception {

	}

	@Override
	public void registry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver(pluginRegistryWrapper.getPluginWrapper().getPluginClassLoader());
		String pluginBasePath = ClassUtils.classPackageAsResourcePath(pluginRegistryWrapper.getPluginWrapper().getPlugin().getClass());
		//扫描plugin所有的类文件
		Resource[] resources = pathMatchingResourcePatternResolver.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + pluginBasePath + "/**/*.class");
		List<Class<?>> classList = new ArrayList<>();
		for (Resource resource : resources) {
			if(resource.isReadable()) {
				MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
				Class clazz = pluginRegistryWrapper.getPluginWrapper().getPluginClassLoader().loadClass(metadataReader.getAnnotationMetadata().getClassName());
				if(clazz.getAnnotation(UnLoad.class) != null) continue;
				if(!PluginBase.class.isAssignableFrom(clazz))
					classList.add(clazz);
			}
		}
		pluginRegistryWrapper.setClassList(classList);
		//扫描mybatis mapper.xml文件
		Resource[] mapperXmlResources = pathMatchingResourcePatternResolver.getResources("classpath:/mapper/*Mapper.xml");
		List<Resource> resourceList = new ArrayList<>();
		resourceList.addAll(Arrays.asList(resources));
		resourceList.addAll(Arrays.asList(mapperXmlResources));
		pluginRegistryWrapper.setResourceList(resourceList);
	}

	@Override
	public void unRegistry(PluginRegistryWrapper pluginRegistryWrapper) throws Exception {

	}

	@Override
	public int getOrder() {
		return 1;
	}
}
