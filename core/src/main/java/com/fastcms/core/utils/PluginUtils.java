package com.fastcms.core.utils;

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.utils.SpringContextHolder;

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
        return SpringContextHolder.getBean(FastcmsPluginManager.class).getExtensions(type);
    }

}
