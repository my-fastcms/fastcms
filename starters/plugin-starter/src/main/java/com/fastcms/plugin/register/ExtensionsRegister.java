package com.fastcms.plugin.register;

import com.fastcms.plugin.FastcmsPluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * 扫描带有@Extension的插件扩展类
 * wjun_java@163.com
 */
public class ExtensionsRegister extends AbstractPluginRegister {

    private static final Logger log = LoggerFactory.getLogger(ExtensionsRegister.class);

    public ExtensionsRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
    }

    @Override
    public void registry(String pluginId) throws Exception {
        Set<String> extensionClassNames = pluginManger.getExtensionClassNames(pluginId);
        for (String extensionClassName : extensionClassNames) {
            try {
                Class<?> extensionClass = pluginManger.getPlugin(pluginId).getPluginClassLoader().loadClass(extensionClassName);
                registerExtension(extensionClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void unRegistry(String pluginId) throws Exception {

    }

    protected void registerExtension(Class<?> extensionClass) {
        Map<String, ?> extensionBeanMap = pluginManger.getApplicationContext().getBeansOfType(extensionClass);
        if (extensionBeanMap.isEmpty()) {
            Object extension = pluginManger.getExtensionFactory().create(extensionClass);
            beanFactory.registerSingleton(extensionClass.getName(), extension);
        } else {
            log.info("Bean registeration aborted! Extension '{}' already existed as bean!", extensionClass.getName());
        }
    }

}
