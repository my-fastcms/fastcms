package com.fastcms.plugin.autoconfigure;

import org.pf4j.RuntimeMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author： wjun_java@163.com
 * @date： 2021/9/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@ConfigurationProperties(prefix = PluginProperties.PLUGIN_PREFIX)
public class PluginProperties {

    public static final String PLUGIN_PREFIX = "plugin";

    private String mode = RuntimeMode.DEVELOPMENT.name();

    private String path;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
