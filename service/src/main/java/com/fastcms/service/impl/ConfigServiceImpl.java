package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.Config;
import com.fastcms.mapper.ConfigMapper;
import com.fastcms.service.IConfigService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 配置服务实现类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService, InitializingBean {

	/**
	 * 缓存系统配置
	 */
	final Map<String, Config> configMap = new ConcurrentHashMap<>();

	@Override
	public Config findByKey(String key) {
		Config config = configMap.get(key);
		if(config == null) {
			config = getOne(Wrappers.<Config>lambdaQuery().eq(Config::getKey, key));
		}
		return config;
	}

	@Override
	public String getValue(String key) {
		Config config = findByKey(key);
		return config == null ? null : config.getValue();
	}

	@Override
	public Config saveConfig(String key, String value) {
		Config config = getOne(Wrappers.<Config>lambdaQuery().eq(Config::getKey, key));
		if(config == null) {
			config = new Config();
			config.setKey(key);
		}
		config.setValue(value);
		saveOrUpdate(config);
		configMap.put(key, config);
		return config;
	}

	@Override
	public List<Config> getConfigs(List<String> configKeys) {
		return configMap.values().stream().filter(item -> configKeys.contains(item.getKey())).collect(Collectors.toList());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		list().forEach(item -> configMap.put(item.getKey(), item));
	}

}
