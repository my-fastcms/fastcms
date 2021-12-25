package com.fastcms.plugin.extension;

import org.pf4j.PluginManager;

import java.util.*;

/**
 * wjun_java@163.com
 */
public class FastcmsSpringExtensionFactory extends SpringExtensionFactory implements FastcmsExtensionFactory {

    private final List<String> extensionClassNames;

    private Map<String, Object> cache;

    public FastcmsSpringExtensionFactory(PluginManager pluginManager) {
        this(pluginManager, true);
    }

    public FastcmsSpringExtensionFactory(PluginManager pluginManager, String... extensionClassNames) {
        this(pluginManager, true, extensionClassNames);
    }

    public FastcmsSpringExtensionFactory(PluginManager pluginManager, boolean autowire, String... extensionClassNames) {
        super(pluginManager, autowire);

        this.extensionClassNames = Arrays.asList(extensionClassNames);

        cache = Collections.synchronizedMap(new HashMap<>()); // simple cache implementation
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> extensionClass) {
        String extensionClassName = extensionClass.getName();
        if (cache.containsKey(extensionClassName)) {
            return (T) cache.get(extensionClassName);
        }

        T extension = super.create(extensionClass);
        if (extensionClassNames.isEmpty() || extensionClassNames.contains(extensionClassName)) {
            cache.put(extensionClassName, extension);
        }

        return extension;
    }

    @Override
    public void destroy(Class<?> extensionClass) {
        String extensionClassName = extensionClass.getName();
        if(cache.containsKey(extensionClassName)) {
            cache.remove(extensionClassName);
        }
    }
}
