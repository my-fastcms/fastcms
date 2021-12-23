package com.fastcms.plugin.register;

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.view.PluginFreeMarkerConfig;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * wjun_java@163.com
 */
public class FreeMarkerViewRegister extends AbstractPluginRegister {

    public FreeMarkerViewRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
    }

    @Override
    public void registry(String pluginId) throws Exception {
        ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(getPlugin(pluginId).getPlugin().getClass(), "");
        List<TemplateLoader> templateLoaders = new ArrayList<>();
        templateLoaders.add(classTemplateLoader);
        templateLoaders.add(new SpringTemplateLoader(new DefaultResourceLoader(getPlugin(pluginId).getPlugin().getClass().getClassLoader()), "classpath:/templates/"));

        PluginFreeMarkerConfig pluginFreeMarkerConfig = pluginManger.getApplicationContext().getBean(PluginFreeMarkerConfig.class);

        if(pluginFreeMarkerConfig.getConfiguration().getTemplateLoader() instanceof MultiTemplateLoader) {
            MultiTemplateLoader templateLoader = (MultiTemplateLoader) pluginFreeMarkerConfig.getConfiguration().getTemplateLoader();
            for(int i=0; i<templateLoader.getTemplateLoaderCount(); i++) {
                templateLoaders.add(templateLoader.getTemplateLoader(i));
            }
        }

        TemplateLoader[] loaders = templateLoaders.toArray(new TemplateLoader[0]);
        MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(loaders);

        pluginFreeMarkerConfig.getConfiguration().setTemplateLoader(multiTemplateLoader);
    }

    @Override
    public void unRegistry(String pluginId) throws Exception {

    }

}
