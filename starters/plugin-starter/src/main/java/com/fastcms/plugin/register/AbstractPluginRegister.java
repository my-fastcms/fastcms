package com.fastcms.plugin.register;

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.PluginBase;
import com.fastcms.plugin.PluginRegister;
import com.fastcms.plugin.UnLoad;
import com.fastcms.plugin.extension.FastcmsSpringExtensionFactory;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 插件抽象注册器
 * @author： wjun_java@163.com
 * @date： 2021/4/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractPluginRegister implements PluginRegister {

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

    protected void destroyBean(Class<?> aClass) {
        FastcmsSpringExtensionFactory extensionFactory = (FastcmsSpringExtensionFactory) pluginManger.getExtensionFactory();
        beanFactory.destroyBean(extensionFactory.create(aClass));
        extensionFactory.destroy(aClass);
    }

}
