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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.plugin.FastcmsPluginManager;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 把mybatis mapper注册到 Spring容器中
 * @author： wjun_java@163.com
 * @date： 2022/1/9
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class MyBatisMapperRegister extends AbstractPluginRegister {

	public MyBatisMapperRegister(FastcmsPluginManager pluginManger) {
		super(pluginManger);
	}

	@Override
	public void registry(String pluginId) throws Exception {

		//注册mapper
		for (Class<?> mapperClass : getMapperClassList(pluginId)) {
			GenericBeanDefinition definition = new GenericBeanDefinition();
			definition.getConstructorArgumentValues().addGenericArgumentValue(mapperClass);
			definition.setBeanClass(MapperFactoryBean.class);
			definition.getPropertyValues().add("addToConfig", true);
			definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
			((GenericWebApplicationContext) this.pluginManger.getApplicationContext()).registerBeanDefinition(mapperClass.getName(), definition);
		}

		PluginWrapper pluginWrapper = pluginManger.getPlugin(pluginId);
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver(pluginWrapper.getPluginClassLoader());
		Resource[] mapperXmlResources = pathMatchingResourcePatternResolver.getResources("classpath:/mapper/*Mapper.xml");

		//注册mapper.xml
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) beanFactory.getBean("sqlSessionFactory");
		Configuration configuration = sqlSessionFactory.getConfiguration();
		try {
			Resources.setDefaultClassLoader(pluginWrapper.getPluginClassLoader());
			for (Resource mapperXmlResource : Arrays.asList(mapperXmlResources)) {
				if(mapperXmlResource != null && mapperXmlResource.getFilename().endsWith("Mapper.xml")) {
					try {
						XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperXmlResource.getInputStream(), configuration, mapperXmlResource.toString(), configuration.getSqlFragments());
						xmlMapperBuilder.parse();
					} catch (Exception e) {
						throw new NestedIOException("Failed to parse mapping resource: '" + mapperXmlResource + "'", e);
					} finally {
						ErrorContext.instance().reset();
					}
				}
			}
		} finally {
			Resources.setDefaultClassLoader(ClassUtils.getDefaultClassLoader());
		}

	}

	@Override
	public void unRegistry(String pluginId) throws Exception {
		//注册mapper
		for (Class<?> mapperClass : getMapperClassList(pluginId)) {
			destroyBean(mapperClass);
		}
	}


	List<Class<?>> getMapperClassList(String pluginId) throws Exception {
		return getPluginClasses(pluginId).stream().filter(clazz -> BaseMapper.class.isAssignableFrom(clazz)).collect(Collectors.toList());
	}

}
