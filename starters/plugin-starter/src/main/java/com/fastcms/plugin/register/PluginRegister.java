package com.fastcms.plugin.register;

/**
 * wjun_java@163.com
 * 插件组件注册器
 * 比如注册拦截器
 */
public interface PluginRegister {

    /**
     * 安装时注册插件组件
     * @param pluginId
     */
    void registry(String pluginId);

    /**
     * 卸载时取消插件组件注册
     * @param pluginId
     */
    void unRegistry(String pluginId);

}
