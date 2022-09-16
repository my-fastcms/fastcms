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

import com.fastcms.mybatis.DataPermissionSqlHandlerManager;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class PluginBase extends Plugin {

    /**
     * 插件配置界面地址
     * 插件管理列表中，点击配置按钮会访问该地址，弹出插件配置界面
     * @return
     */
    public abstract String getConfigUrl();

    private ApplicationContext applicationContext;

    public PluginBase(PluginWrapper wrapper) {
        super(wrapper);
    }

    public final ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = createApplicationContext();
            if(applicationContext != null) {
                ((GenericApplicationContext) applicationContext).refresh();
                PluginApplicationUtils.put(wrapper.getPluginId(), applicationContext);
            }
        }
        return applicationContext;
    }

    @Override
    public void start() {
        getApplicationContext();
        // 刷新数据权限SQL处理器
        refresh();
    }

    @Override
    public void stop() {
        // close applicationContext
        if ((applicationContext != null) && (applicationContext instanceof ConfigurableApplicationContext)) {
            ((ConfigurableApplicationContext) applicationContext).close();
        }
        refresh();
    }

    /**
     * 由插件子类覆盖，用来创建插件自己的spring容器，独立与主程序Spring容器之外
     * 特殊需求情况下需要构建插件的Spring容器
     * 一般情况下直接返回空，使用主程序的Spring容器
     * @return
     */
    protected ApplicationContext createApplicationContext() {
        return null;
    }

    /**
     * 插件中直接执行sql文件
     * @param sqlFile
     */
    protected void runSqlFile(String sqlFile) {
        try {
            FastcmsPluginManager pluginManager = (FastcmsPluginManager) wrapper.getPluginManager();
            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) pluginManager.getApplicationContext().getBean("sqlSessionFactory");
            ScriptRunner scriptRunner = new ScriptRunner(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection());
            scriptRunner.setLogWriter(null);
            scriptRunner.runScript(Resources.getResourceAsReader(wrapper.getPluginClassLoader(), sqlFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected FastcmsPluginManager getPluginManager() {
        return (FastcmsPluginManager) wrapper.getPluginManager();
    }

    protected ApplicationContext getPluginApplicationContext() {
        return getPluginManager().getApplicationContext();
    }

    protected void refresh() {
        // 刷新数据权限SQL处理器
        getPluginApplicationContext().getBean(DataPermissionSqlHandlerManager.class).getHandler();
    }

}
