package com.fastcms.core.utils;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.ConfigUtils;

import java.util.List;

/**
 * wjun_java@163.com
 */
public final class PluginUtils {

    public static final String PAGE_DATA_ATTR = "pageData";

    private PluginUtils() {}

    /**
     * 获取插件扩展
     * @param type
     * @param <T>
     * @return
     */
    public static <T>List<T> getExtensions(Class<T> type) {
        return getPluginManager().getExtensions(type);
    }

    /**
     * 获取系统插件管理器
     * @return
     */
    public static FastcmsPluginManager getPluginManager() {
        return ApplicationUtils.getBean(FastcmsPluginManager.class);
    }

    /**
     * 获取插件配置界面url host
     * @return
     */
    public static String getConfigUrlHost() {
        return ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN) + FastcmsConstants.PLUGIN_MAPPING;
    }

}
