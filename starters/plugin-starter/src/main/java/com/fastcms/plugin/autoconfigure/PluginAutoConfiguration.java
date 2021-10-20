package com.fastcms.plugin.autoconfigure;

import org.pf4j.DefaultPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author： wjun_java@163.com
 * @date： 2021/9/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
@ConditionalOnClass({DefaultPluginManager.class})
@EnableConfigurationProperties(PluginProperties.class)
public class PluginAutoConfiguration {

    private final PluginProperties properties;

    public PluginAutoConfiguration(PluginProperties properties) {
        this.properties = properties;
    }

    @Autowired
    Environment environment;

}
