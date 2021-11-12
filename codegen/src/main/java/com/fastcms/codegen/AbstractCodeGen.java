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

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/9
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractCodeGen extends AutoGenerator {

    protected ConfigBuilder config;

    abstract void setSystemGlobalConfig();
    abstract void setSystemTemplateConfig();
    abstract String getMapperXmlOutputDir();
    abstract String getModelName();

    protected StrategyConfig setStrategyConfig() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("user", "role", "permission" , "config", "attachment", "user_tag", "user_openid", "payment_record");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        setStrategy(strategy);
        return strategy;
    }

    protected AbstractCodeGen() {
        setTemplateEngine(new SystemCodegenTemplateEngine());

        // 全局配置
        setSystemGlobalConfig();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/fastcms?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();

        if(getModelName() != null) {
            pc.setModuleName(getModelName());
        }

//        pc.setXml(null);
        pc.setParent("com.fastcms");
        setPackageInfo(pc);

        if(getMapperXmlOutputDir() != null) {
            String templatePath = "/templates/mapper.xml.ftl";
            injectionConfig = new InjectionConfig() {
                @Override
                public void initMap() {}
            };
            List<FileOutConfig> focList = new ArrayList<>();

            // 自定义配置会被优先输出
            focList.add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return getMapperXmlOutputDir() + "/src/main/resources/mapper/"+ tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });
            injectionConfig.setFileOutConfigList(focList);
            setCfg(injectionConfig);
        }

        setSystemTemplateConfig();

        StrategyConfig strategyConfig = setStrategyConfig();

        config = new ConfigBuilder(getPackageInfo(), getDataSource(), strategyConfig, getTemplate(), getGlobalConfig());
        if (null != injectionConfig) {
            injectionConfig.setConfig(config);
        }
        setConfig(config);

        getTemplateEngine().init(this.pretreatmentConfigBuilder(config)).mkdirs();
    }

    public void genModel() throws Exception {
        SystemCodegenTemplateEngine systemCodegenTemplateEngine = (SystemCodegenTemplateEngine) getTemplateEngine();
        systemCodegenTemplateEngine.genModel();
    }

    public void genService() throws Exception {
        SystemCodegenTemplateEngine systemCodegenTemplateEngine = (SystemCodegenTemplateEngine) getTemplateEngine();
        systemCodegenTemplateEngine.genService();
    }

}
