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

import com.fastcms.common.exception.FastcmsException;
import com.fastcms.plugin.extension.FastcmsSpringExtensionFactory;
import com.fastcms.plugin.register.CompoundPluginRegister;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionFactory;
import org.pf4j.PluginWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * fastcms插件管理器
 * @author： wjun_java@163.com
 * @date： 2021/4/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsPluginManager extends DefaultPluginManager implements PluginManagerService, ApplicationContextAware, InitializingBean, ApplicationListener<ApplicationStartedEvent> {

    private ApplicationContext applicationContext;

    private PluginRegister pluginRegister;

    public FastcmsPluginManager(Path... pluginsRoots) {
        super(pluginsRoots);
    }

//    @Override
//    protected ExtensionFinder createExtensionFinder() {
//        FastcmsExtensionFinder extensionFinder = new FastcmsExtensionFinder(this);
//        addPluginStateListener(extensionFinder);
//
//        return extensionFinder;
//    }

    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new FastcmsSpringExtensionFactory(this);
    }

    @Override
    public void installPlugin(Path path) throws Exception {
        if(isDevelopment()) {
            throw new FastcmsException(FastcmsException.NO_RIGHT, "开发环境不允许安装");
        }
        final String pluginId = loadPlugin(path);
        startPlugin(pluginId);
        pluginRegister.registry(pluginId);
    }

    @Override
    public void unInstallPlugin(String pluginId) throws Exception {
        if(isDevelopment()) {
            throw new FastcmsException(FastcmsException.NO_RIGHT, "开发环境不允许卸载");
        }
        try {
            pluginRegister.unRegistry(pluginId);
            deletePlugin(pluginId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void initPlugins() throws Exception {
        loadPlugins();
        startPlugins();

        for (PluginWrapper item : getStartedPlugins()) {
            pluginRegister.registry(item.getPluginId());
        }

    }

    @Override
    public PluginResult getPluginList(int pageNum, int pageSize, String pluginId, String provider) {
        List<PluginWrapper> plugins = getPlugins();
        if(plugins == null || plugins.size() <=0) {
            return new PluginResult(0, new ArrayList<>());
        }

        // 记录总数
        final int count = plugins.size();
        // 总页数
        int pageCount;
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        // 开始索引
        int fromIndex;
        // 结束索引
        int toIndex;

        if (pageNum < pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List<PluginWrapper> wrapperList = plugins.subList(fromIndex, toIndex);

        List<PluginVo> pluginVoList = wrapperList.stream().map(item -> new PluginVo(item)).collect(Collectors.toList());
        return new PluginResult(count, pluginVoList);
    }

    @Override
    public FastcmsPluginManager getPluginManager() {
        return this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pluginRegister = new CompoundPluginRegister(this);
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
            throw new RuntimeException(e.getMessage());
        }
    }

}
