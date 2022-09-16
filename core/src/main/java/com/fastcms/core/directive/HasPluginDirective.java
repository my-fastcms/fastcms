<<<<<<< HEAD
package com.fastcms.core.directive;

import com.fastcms.plugin.FastcmsPluginManager;
import freemarker.core.Environment;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 是否安装某个插件
 * @author： wjun_java@163.com
 * @date： 2021/5/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("hasPlugin")
public class HasPluginDirective extends BaseDirective {

    private static final String PLUGIN_ID = "pluginId";

    @Autowired
    private FastcmsPluginManager pluginManager;

    @Override
    public Object doExecute(Environment env, Map params) {
        final String pluginId = getStr(PLUGIN_ID, params);
        PluginWrapper plugin = pluginManager.getPlugin(pluginId);
        return plugin != null;
    }

}
=======
package com.fastcms.core.directive;

import com.fastcms.plugin.FastcmsPluginManager;
import freemarker.core.Environment;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 是否安装某个插件
 * @author： wjun_java@163.com
 * @date： 2021/5/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("hasPlugin")
public class HasPluginDirective extends BaseDirective {

    private static final String PLUGIN_ID = "pluginId";

    @Autowired
    private FastcmsPluginManager pluginManager;

    @Override
    public Object doExecute(Environment env, Map params) {
        final String pluginId = getStr(PLUGIN_ID, params);
        PluginWrapper plugin = pluginManager.getPlugin(pluginId);
        return plugin != null;
    }

}
>>>>>>> 9b22e8ee2077deb1d0ca28d39702bca483d28388
