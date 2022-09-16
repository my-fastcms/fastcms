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

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/9
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractCodeGen {

    final static String url = "jdbc:mysql://localhost:3306/fastcms?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    final static String username = "root";
    final static String password = "root";

    abstract String getOutputDir();
    abstract String getModelName();
    abstract String[] getTableNames();

    protected void gen() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("wjun_java@163.com") // 设置作者
                            .disableOpenDir()
                            .outputDir(System.getProperty("user.dir") + getOutputDir() + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.fastcms") // 设置父包名
                            .moduleName(getModelName())
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + getOutputDir() + "/src/main/resources/mapper/")); // 设置mapperXml生成路径
                })
                .templateConfig(builder -> builder.disable(TemplateType.CONTROLLER))
                .strategyConfig(builder -> {
                    builder.addInclude(getTableNames()); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
