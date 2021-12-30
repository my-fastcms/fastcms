package com.fastcms.hello;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.plugin.PluginBase;
import com.fastcms.utils.ConfigUtils;
import org.pf4j.PluginWrapper;
import org.springframework.context.ApplicationContext;

/**
 * hello world plugin
 * wjun_java@163.com
 */
public class HelloPlugin extends PluginBase {

    @Override
    public String getConfigUrl() {
        return ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN) + FastcmsConstants.PLUGIN_MAPPING + "/hello";
    }

    public HelloPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        return null;
    }

}
