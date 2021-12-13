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
package com.fastcms.plugin;

import com.fastcms.plugin.register.CompoundPluginRegister;
import com.fastcms.plugin.register.PluginRegistryWrapper;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import java.nio.file.Path;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsPluginManager extends DefaultPluginManager implements PluginManagerService, ApplicationContextAware, InitializingBean, ApplicationListener<ApplicationStartedEvent> {

    private ApplicationContext applicationContext;

    @Autowired
    CompoundPluginRegister compoundPluginRegister;

    public FastcmsPluginManager(Path... pluginsRoots) {
        super(pluginsRoots);
    }

    @Override
    public void installPlugin(Path path) throws Exception {
        String pluginId = loadPlugin(path);
        startPlugin(pluginId);
        compoundPluginRegister.registry(new PluginRegistryWrapper(getPlugin(pluginId), applicationContext));
    }

    @Override
    public void unInstallPlugin(String pluginId) throws Exception {
        PluginWrapper pluginWrapper = getPlugin(pluginId);
        compoundPluginRegister.unRegistry(new PluginRegistryWrapper(pluginWrapper, applicationContext));
        stopPlugin(pluginId);
        unloadPlugin(pluginId, false);
        //Files.deleteIfExists(pluginWrapper.getPluginPath());
        //FileUtils.forceDelete(pluginWrapper.getPluginPath().toFile());
    }

    @Override
    public void initPlugins() throws Exception {
        loadPlugins();
        startPlugins();
        compoundPluginRegister.initialize();
        for (PluginWrapper startedPlugin : getPlugins()) {
            compoundPluginRegister.registry(new PluginRegistryWrapper(startedPlugin, applicationContext));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
            initPlugins();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
