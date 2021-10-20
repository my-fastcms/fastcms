package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Plugin;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-07-08
 */
public interface IPluginService extends IService<Plugin> {

	Plugin insertPlugin(String pluginId, String pluginName);

	void removePlugin(String pluginId);

	Plugin getPlugin(String pluginId);

	List<Plugin> getPluginList();

	Map<String, Plugin> getPluginMap();

}
