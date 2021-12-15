package com.fastcms.hello;

import com.fastcms.plugin.PluginBase;
import org.pf4j.PluginWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * hello world plugin
 * wjun_java@163.com
 */
public class HelloPlugin extends PluginBase {

    public HelloPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setClassLoader(getWrapper().getPluginClassLoader());
        applicationContext.register(HelloPluginConfiguration.class);
        applicationContext.refresh();
        return applicationContext;
    }

}
