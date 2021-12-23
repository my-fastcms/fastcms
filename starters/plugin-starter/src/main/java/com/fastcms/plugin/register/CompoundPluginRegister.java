package com.fastcms.plugin.register;

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.PluginRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * wjun_java@163.com
 * 插件主键注册管理器
 */
public class CompoundPluginRegister extends AbstractPluginRegister implements PluginRegister {

    List<PluginRegister> registerList = Collections.synchronizedList(new ArrayList<>());

    public CompoundPluginRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
        addRegister(new ExtensionsRegister(pluginManger));
        addRegister(new ControllerRegister(pluginManger));
        addRegister(new InterceptorRegister(pluginManger));
        addRegister(new FreeMarkerViewRegister(pluginManger));
    }

    @Override
    public void registry(String pluginId) throws Exception {
        for (PluginRegister pluginRegister : registerList) {
            pluginRegister.registry(pluginId);
        }
    }

    @Override
    public void unRegistry(String pluginId) throws Exception {
        for (PluginRegister pluginRegister : registerList) {
            pluginRegister.unRegistry(pluginId);
        }
    }

    void addRegister(PluginRegister pluginRegister) {
        registerList.add(pluginRegister);
    }

}
