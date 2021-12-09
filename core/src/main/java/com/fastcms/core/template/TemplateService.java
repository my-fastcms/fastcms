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

import com.fastcms.common.model.TreeNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface TemplateService {

    /**
     * 初始化模板
     */
    void initialize();

    /**
     * 根据id获取模板
     * @param id
     * @return
     */
    Template getTemplate(String id);

    /**
     * 获取当前使用的模板
     * @return
     */
    Template getCurrTemplate();

    /**
     * 获取模板列表
     * @return
     */
    List<Template> getTemplateList();

    /**
     * 安装模板
     * @param file
     * @throws Exception
     */
    void install(File file) throws Exception;

    /**
     * 卸载模板
     * @param templateId
     * @throws Exception
     */
    void unInstall(String templateId) throws Exception;

    /**
     * 获取模板对应的文件目录树
     * @return
     */
    List<FileTreeNode> getTemplateTreeFiles() throws IOException;

    class FileTreeNode extends TreeNode {

        @JsonIgnore
        private String parent;

        @JsonIgnore
        private String path;

        public FileTreeNode(Path path) {
            super(path.getFileName().toString(), Files.isDirectory(path) ? 0 : 1);
            this.parent = path.getParent().toString();
            this.path = path.toString();
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }

}
