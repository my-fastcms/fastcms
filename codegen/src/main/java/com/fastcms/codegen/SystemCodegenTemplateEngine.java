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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class SystemCodegenTemplateEngine extends FreemarkerTemplateEngine {


    protected void genModel() throws Exception {
        List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
        for (TableInfo tableInfo : tableInfoList) {
            Map<String, Object> objectMap = getObjectMap(tableInfo);
            Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
            TemplateConfig template = getConfigBuilder().getTemplate();
            // 自定义内容
            InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
            if (null != injectionConfig) {
                injectionConfig.initTableMap(tableInfo);
                objectMap.put("cfg", injectionConfig.getMap());
                List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                if (CollectionUtils.isNotEmpty(focList)) {
                    for (FileOutConfig foc : focList) {
                        if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                            writerFile(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                        }
                    }
                }
            }
            // Mp.java
            String entityName = tableInfo.getEntityName();
            if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                if (isCreate(FileType.ENTITY, entityFile)) {
                    writerFile(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                }
            }
        }
    }

    protected void genService() throws Exception {
        List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
        for (TableInfo tableInfo : tableInfoList) {
            Map<String, Object> objectMap = getObjectMap(tableInfo);
            Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
            TemplateConfig template = getConfigBuilder().getTemplate();
            // 自定义内容
            InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
            if (null != injectionConfig) {
                injectionConfig.initTableMap(tableInfo);
                objectMap.put("cfg", injectionConfig.getMap());
                List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                if (CollectionUtils.isNotEmpty(focList)) {
                    for (FileOutConfig foc : focList) {
                        if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                            writerFile(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                        }
                    }
                }
            }
            String entityName = tableInfo.getEntityName();
            // MpMapper.java
            if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                if (isCreate(FileType.MAPPER, mapperFile)) {
                    writerFile(objectMap, templateFilePath(template.getMapper()), mapperFile);
                }
            }
            // MpMapper.xml
            if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                if (isCreate(FileType.XML, xmlFile)) {
                    writerFile(objectMap, templateFilePath(template.getXml()), xmlFile);
                }
            }
            // IMpService.java
            if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                String serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                if (isCreate(FileType.SERVICE, serviceFile)) {
                    writerFile(objectMap, templateFilePath(template.getService()), serviceFile);
                }
            }
            // MpServiceImpl.java
            if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                String implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                    writerFile(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                }
            }
        }
    }

}
