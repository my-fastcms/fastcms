package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Config;

/**
 * <p>
 *  配置服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface IConfigService extends IService<Config> {

	Config findByKey(String key);

	String getValue(String key);

	Config saveConfig(String key, String value);

	void saveOrUpdateConfig(Config config);

}
