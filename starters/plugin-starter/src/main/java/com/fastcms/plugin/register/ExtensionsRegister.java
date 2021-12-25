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
        for (String extensionClassName : getExtensionClassNames(pluginId)) {
            try {
                registerExtension(getPluginExtensionClass(pluginId, extensionClassName));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void unRegistry(String pluginId) throws Exception {
        for (String extensionClassName : getExtensionClassNames(pluginId)) {
            try {
                unRegisterExtension(getPluginExtensionClass(pluginId, extensionClassName));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    Set<String> getExtensionClassNames(String pluginId) {
        return pluginManger.getExtensionClassNames(pluginId);
    }

    Class<?> getPluginExtensionClass(String pluginId, String extensionClassName) throws ClassNotFoundException {
        return pluginManger.getPlugin(pluginId).getPluginClassLoader().loadClass(extensionClassName);
    }

    private void registerExtension(Class<?> extensionClass) {
        Map<String, ?> extensionBeanMap = pluginManger.getApplicationContext().getBeansOfType(extensionClass);
        if (extensionBeanMap.isEmpty()) {
            Object extension = pluginManger.getExtensionFactory().create(extensionClass);
            beanFactory.registerSingleton(extensionClass.getName(), extension);
        } else {
            log.info("Bean registeration aborted! Extension '{}' already existed as bean!", extensionClass.getName());
        }
    }

    private void unRegisterExtension(Class<?> extensionClass) {
        destroyBean(extensionClass);
    }

}
