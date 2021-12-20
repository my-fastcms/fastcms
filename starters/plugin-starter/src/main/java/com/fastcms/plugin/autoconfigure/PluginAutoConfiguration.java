package com.fastcms.plugin.autoconfigure;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.plugin.FastcmsPluginManager;
import org.pf4j.AbstractPluginManager;
import org.pf4j.DefaultPluginManager;
import org.pf4j.RuntimeMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.nio.file.Path;
import java.nio.file.Paths;

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

    @Autowired
    Environment environment;

    private final PluginProperties properties;

    public PluginAutoConfiguration(PluginProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(FastcmsPluginManager.class)
    public FastcmsPluginManager pluginManager() {

        if(FastcmsConstants.DEV_MODE.equals(getProfile())) {
            System.setProperty(AbstractPluginManager.MODE_PROPERTY_NAME, RuntimeMode.DEVELOPMENT.toString());
        }

        Path pluginPath = Paths.get(properties.getPath());
        FastcmsPluginManager fastcmsPluginManager = new FastcmsPluginManager(pluginPath);
        return fastcmsPluginManager;
    }

    String getProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        return activeProfiles == null || activeProfiles.length <=0 ? FastcmsConstants.DEV_MODE : activeProfiles[0];
    }

}
