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
package com.fastcms.codegen;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/9
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class PluginCodeGen extends AbstractCodeGen {

    protected static final String PLUGIN_DIR = "./plugins/";

    @Override
    void setSystemGlobalConfig() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + PLUGIN_DIR + getPluginPath() + "/src/main/java");
        gc.setAuthor("wjun_java@163.com");
        gc.setOpen(false);
        setGlobalConfig(gc);
    }

    @Override
    void setSystemTemplateConfig() {
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setXml(null);
        setTemplate(templateConfig);
    }

    abstract String getPluginPath();

    void genCode() throws Exception {
        genModel();
        genService();
    }

}
