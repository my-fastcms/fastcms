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
package com.fastcms.hello;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.PluginBase;
import com.fastcms.utils.ConfigUtils;
import org.pf4j.PluginWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(HelloPluginConfiguration.class);
        applicationContext.setParent(((FastcmsPluginManager) wrapper.getPluginManager()).getApplicationContext());
        applicationContext.refresh();
        return applicationContext;
    }

    @Override
    public void start() {

//        try {
//            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ApplicationUtils.getBean("sqlSessionFactory");
//            ScriptRunner scriptRunner = new ScriptRunner(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection());
//            scriptRunner.runScript(Resources.getResourceAsReader(wrapper.getPluginClassLoader(),"hello-plugin.sql"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        getApplicationContext();
    }
}
