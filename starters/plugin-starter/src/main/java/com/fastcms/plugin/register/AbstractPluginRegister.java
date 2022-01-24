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

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.PluginBase;
import com.fastcms.plugin.PluginRegister;
import com.fastcms.plugin.UnLoad;
import com.fastcms.plugin.extension.FastcmsSpringExtensionFactory;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 插件抽象注册器
 * @author： wjun_java@163.com
 * @date： 2021/4/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractPluginRegister implements PluginRegister {

    private static final Logger log = LoggerFactory.getLogger(AbstractPluginRegister.class);

    protected final FastcmsPluginManager pluginManger;
    protected final AbstractAutowireCapableBeanFactory beanFactory;

    public AbstractPluginRegister(FastcmsPluginManager pluginManger) {
        this.pluginManger = pluginManger;
        this.beanFactory = (AbstractAutowireCapableBeanFactory) this.pluginManger.getApplicationContext().getAutowireCapableBeanFactory();
    }

    protected PluginWrapper getPlugin(String pluginId) {
        return pluginManger.getPlugin(pluginId);
    }

    protected List<Class<?>> getPluginClasses(String pluginId) throws Exception {
        PluginWrapper pluginWrapper = getPlugin(pluginId);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver(pluginWrapper.getPluginClassLoader());
        String pluginBasePath = ClassUtils.classPackageAsResourcePath(pluginWrapper.getPlugin().getClass());
        //扫描plugin所有的类文件
        Resource[] resources = pathMatchingResourcePatternResolver.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + pluginBasePath + "/**/*.class");
        List<Class<?>> classList = new ArrayList<>();
        for (Resource resource : resources) {
            if(resource.isReadable()) {
                MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
                Class clazz = pluginWrapper.getPluginClassLoader().loadClass(metadataReader.getAnnotationMetadata().getClassName());
                if(!PluginBase.class.isAssignableFrom(clazz)
                    && !Plugin.class.isAssignableFrom(clazz)
                    && clazz.getAnnotation(UnLoad.class) == null)
                    classList.add(clazz);
            }
        }
        return classList;
    }

    protected Object getBean(Class<?> aClass) {
        return beanFactory.getBean(aClass);
    }

    /**
     * 往spring容器注册bean
     * @param aClass
     */
    protected void registryBean(Class<?> aClass) {
        Map<String, ?> extensionBeanMap = pluginManger.getApplicationContext().getBeansOfType(aClass);
        if (extensionBeanMap.isEmpty()) {
            Object extension = pluginManger.getExtensionFactory().create(aClass);
            beanFactory.registerSingleton(aClass.getName(), extension);
        } else {
            log.info("Bean registeration aborted! Extension '{}' already existed as bean!", aClass.getName());
        }
    }

    /**
     * 销毁spring容器中bean实例
     * @param aClass
     */
    protected void destroyBean(Class<?> aClass) {
        FastcmsSpringExtensionFactory extensionFactory = (FastcmsSpringExtensionFactory) pluginManger.getExtensionFactory();
        extensionFactory.destroy(aClass);
        beanFactory.destroySingleton(aClass.getName());
    }

}