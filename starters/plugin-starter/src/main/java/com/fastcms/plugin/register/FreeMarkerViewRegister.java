/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * @author： wjun_java@163.com
 * @date： 2021/10/24
 * @description：
 * @modifiedBy：
 * @version: 1.0
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

        PluginFreeMarkerConfig pluginFreeMarkerConfig = beanFactory.getBean(PluginFreeMarkerConfig.class);

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
