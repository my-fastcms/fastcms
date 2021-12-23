package com.fastcms.plugin.extension;

import com.fastcms.plugin.FastcmsPluginManager;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 插件扩展注册器
 * wjun_java@163.com
 */
public class ExtensionsInjector {

    private static final Logger log = LoggerFactory.getLogger(ExtensionsInjector.class);

    protected final FastcmsPluginManager fastcmsPluginManager;
    protected final AbstractAutowireCapableBeanFactory beanFactory;

    public ExtensionsInjector(FastcmsPluginManager fastcmsPluginManager) {
        this.fastcmsPluginManager = fastcmsPluginManager;
        this.beanFactory = (AbstractAutowireCapableBeanFactory) this.fastcmsPluginManager.getApplicationContext().getAutowireCapableBeanFactory();
    }

    public void injectExtensions() {
        // add extensions from classpath (non plugin)
        Set<String> extensionClassNames = fastcmsPluginManager.getExtensionClassNames(null);
        for (String extensionClassName : extensionClassNames) {
            try {
                log.debug("Register extension '{}' as bean", extensionClassName);
                Class<?> extensionClass = getClass().getClassLoader().loadClass(extensionClassName);
                registerExtension(extensionClass);
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }

        // add extensions for each started plugin
        List<PluginWrapper> startedPlugins = fastcmsPluginManager.getStartedPlugins();
        for (PluginWrapper plugin : startedPlugins) {

            log.debug("Registering extensions of the plugin '{}' as beans", plugin.getPluginId());
            extensionClassNames = fastcmsPluginManager.getExtensionClassNames(plugin.getPluginId());
            for (String extensionClassName : extensionClassNames) {
                try {
                    log.debug("Register extension '{}' as bean", extensionClassName);
                    Class<?> extensionClass = plugin.getPluginClassLoader().loadClass(extensionClassName);
                    registerExtension(extensionClass);
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Register an extension as bean.
     * Current implementation register extension as singleton using {@code beanFactory.registerSingleton()}.
     * The extension instance is created using {@code pluginManager.getExtensionFactory().create(extensionClass)}.
     * The bean name is the extension class name.
     * Override this method if you wish other register strategy.
     */
    protected void registerExtension(Class<?> extensionClass) {
        Map<String, ?> extensionBeanMap = fastcmsPluginManager.getApplicationContext().getBeansOfType(extensionClass);
        if (extensionBeanMap.isEmpty()) {
            Object extension = fastcmsPluginManager.getExtensionFactory().create(extensionClass);
            beanFactory.registerSingleton(extensionClass.getName(), extension);
        } else {
            log.debug("Bean registeration aborted! Extension '{}' already existed as bean!", extensionClass.getName());
        }
    }

}
