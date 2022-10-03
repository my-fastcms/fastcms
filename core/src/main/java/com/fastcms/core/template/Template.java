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
package com.fastcms.core.template;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Template implements Serializable {

    /**
     * 模板id
     * 示例值
     * www.xjd2020.com
     */
    String id;

    /**
     * 模板名称
     */
    String name;

    /**
     * 模板路径
     * path示范值              /fastcms/
     * 根路径示范值             /htmls/
     * 那么模板文件路径          /htmls/fastcms
     */
    String path;

    /**
     * i18n properties存储目录
     */
    String i18n;

    /**
     * 模板路径名称
     * 去掉模板路径前后斜杠
     * 示例值                  fastcms
     */
    String pathName;

    /**
     * 模板版本
     */
    String version;

    /**
     * 模板提供者
     */
    String provider;

    /**
     * 模板描述
     */
    String description;

    /**
     * 模板根路径绝对路径
     * 示范值: D:\\htmls\\fastcms
     */
    @JsonIgnore
    Path templatePath;

    private Boolean active = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Path getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(Path templatePath) {
        this.templatePath = templatePath;
    }

    public String getPathName() {
        return this.getPath().replaceAll("/", "");
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
