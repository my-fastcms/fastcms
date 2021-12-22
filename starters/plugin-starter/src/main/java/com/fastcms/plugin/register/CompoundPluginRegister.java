package com.fastcms.plugin.register;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * wjun_java@163.com
 * 插件主键注册管理器
 */
@Component
public class CompoundPluginRegister implements PluginRegister {

    List<PluginRegister> registerList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void registry(String pluginId) {

    }

    @Override
    public void unRegistry(String pluginId) {

    }

}
