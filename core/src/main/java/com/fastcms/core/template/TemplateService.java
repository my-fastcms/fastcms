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
    void initialize() throws IOException;

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
     * 设置默认使用的模板
     */
    void setDefaultTemplate();

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

    /**
     * 获取模板i18n加载文件
     * @return
     */
    String[] getI18nNames();

    class FileTreeNode extends TreeNode {

        @JsonIgnore
        private String parent;

        @JsonIgnore
        private String path;

        /**
         * 文件相对路径，去掉盘符路径
         */
        private String filePath;

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

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

    }

    interface TemplateI18n {
        String CMS_TEMPLATE_DEV_NOT_ALLOW_INSTALL = "fastcms.cms.template.dev.not.allow.install";
        String CMS_TEMPLATE_DEV_NOT_ALLOW_UNINSTALL = "fastcms.cms.template.dev.not.allow.uninstall";
        String CMS_TEMPLATE_FILE_ZIP_INSTALL = "fastcms.cms.template.file.zip.install";
        String CMS_TEMPLATE_USING_IS_NOT_ALLOW_UNINSTALL = "fastcms.cms.template.using.is.not.allow.uninstall";
        String CMS_TEMPLATE_FILE_NOT_EXIST = "fastcms.cms.template.file.not.exist";
        String CMS_TEMPLATE_NOT_EXIST = "fastcms.cms.template.not.exist";
        String CMS_TEMPLATE_DEFAULT_NOT_EXIST = "fastcms.cms.template.default.not.exist";
        String CMS_TEMPLATE_FILE_SUFFIX_NOT_NULL = "fastcms.cms.template.file.suffix.not.null";
        String CMS_TEMPLATE_FILE_NOT_SUPPORT_EDIT = "fastcms.cms.template.file.not.support.edit";
        String CMS_TEMPLATE_FILE_SURE_ONE = "fastcms.cms.template.file.sure.one";
        String CMS_TEMPLATE_FILE_CONTENT_NOT_NULL = "fastcms.cms.template.file.content.not.null";
        String CMS_TEMPLATE_FILE_NOT_WRITE_AUTH = "fastcms.cms.template.file.not.write.auth";
        String CMS_TEMPLATE_FILE_UPLOAD_DIR_NOT_NULL = "fastcms.cms.template.file.upload.dir.not.null";
        String CMS_TEMPLATE_FILE_UPLOAD_EMPTY = "fastcms.cms.template.file.upload.empty";
        String CMS_TEMPLATE_FILE_UPLOAD_DIR_NOT_EXIST = "fastcms.cms.template.file.upload.dir.not.exist";
        String CMS_TEMPLATE_FILE_PATH_IS_EMPTY = "fastcms.cms.template.file.path.is.empty";
        String CMS_TEMPLATE_FILE_PATH_IS_ERROR = "fastcms.cms.template.file.path.is.error";
        String CMS_TEMPLATE_FILE_PATH_IS_NOT_ALLOW_DELETE = "fastcms.cms.template.file.path.is.not.allow.delete";
        String CMS_TEMPLATE_MENU_CHILDREN_IS_NOT_DELETE = "fastcms.cms.template.menu.children.is.not.delete";
        String CMS_TEMPLATE_PATH_IS_EXIST = "fastcms.cms.template.path.is.exist";
        String CMS_TEMPLATE_PATH_MISSING_REQUIRED_ATTR = "fastcms.cms.template.path.missing.required.attr";
    }

}
