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
package com.fastcms.core.attach;

import com.fastcms.entity.Attachment;
import org.pf4j.ExtensionPoint;

import java.io.File;

/**
 * wjun_java@163.com
 * 文件服务器管理接口
 * 阿里oss，Minio等文件服务器扩展接口
 */
public interface FileServerManager extends ExtensionPoint {

    /**
     * 上传文件到文件服务器
     * @param attachment
     */
    void uploadFile(Attachment attachment) throws Exception;

    /**
     * 删除文件服务器上面的文件
     * @param attachment  文件相对路径
     */
    void deleteFile(Attachment attachment) throws Exception;

    /**
     * 从文件服务器下载文件
     * @param attachment
     * @return
     */
    File downloadFile(Attachment attachment) throws Exception;

    /**
     * 是否启用文件服务器
     * @return
     */
    boolean isEnable();

}
