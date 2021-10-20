package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.Plugin;
import com.fastcms.mapper.PluginMapper;
import com.fastcms.service.IPluginService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-07-08
 */
@Service
public class PluginServiceImpl extends ServiceImpl<PluginMapper, Plugin> implements IPluginService, InitializingBean {

	private Map<String, Plugin> maps = Collections.synchronizedMap(new HashMap<>());

	@Override
	public Plugin insertPlugin(String pluginId, String pluginName) {
		Plugin plugin = getPlugin(pluginId);
		if(plugin == null) {
			plugin = new Plugin();
			plugin.setPluginId(pluginId);
			plugin.setPluginName(pluginName);
			save(plugin);
			maps.put(plugin.getPluginId(), plugin);
		}
		return plugin;
	}

	@Override
	public void removePlugin(String pluginId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("plugin_id", pluginId);
		remove(queryWrapper);
		maps.remove(pluginId);
	}

	@Override
	public Plugin getPlugin(String pluginId) {
		return maps.get(pluginId);
	}

	@Override
	public List<Plugin> getPluginList() {
		return list();
	}

	@Override
	public Map<String, Plugin> getPluginMap() {
		return maps;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for (Plugin plugin : getPluginList()) {
			maps.put(plugin.getPluginId(), plugin);
		}
	}

}
