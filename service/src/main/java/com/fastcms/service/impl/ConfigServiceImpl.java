package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.Config;
import com.fastcms.mapper.ConfigMapper;
import com.fastcms.service.IConfigService;
import org.springframework.stereotype.Service;

/**
 * 配置服务实现类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

	@Override
	public Config findByKey(String key) {
		LambdaQueryWrapper<Config> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(Config::getKey, key);
		return getOne(queryWrapper);
	}

	@Override
	public String getValue(String key) {
		Config config = findByKey(key);
		return config == null ? null : config.getValue();
	}

	@Override
	public Config saveConfig(String key, String value) {
		Config config = findByKey(key);
		if(config == null) {
			config = new Config();
			config.setKey(key);
		}
		config.setValue(value);
		saveOrUpdate(config);
		return config;
	}

	@Override
	public void saveOrUpdateConfig(Config config) {
		Config myConfig = findByKey(config.getKey());
		if(myConfig == null) {
			myConfig = config;
		}else {
			myConfig.setValue(config.getValue());
		}
		saveOrUpdate(myConfig);
	}

}
