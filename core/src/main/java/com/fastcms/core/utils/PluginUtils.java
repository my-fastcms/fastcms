package com.fastcms.core.utils;

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.utils.ApplicationUtils;

import java.util.List;

/**
 * wjun_java@163.com
 */
public final class PluginUtils {

    private PluginUtils() {}

    /**
     * 获取插件扩展
     * @param type
     * @param <T>
     * @return
     */
    public static  <T>List<T> getExtensions(Class<T> type) {
        return ApplicationUtils.getBean(FastcmsPluginManager.class).getExtensions(type);
    }

}
