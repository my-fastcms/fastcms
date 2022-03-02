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

import com.fastcms.common.utils.DirUtils;
import com.fastcms.common.utils.ImageUtils;
import com.fastcms.core.utils.AttachUtils;
import com.fastcms.entity.Attachment;
import com.fastcms.utils.ConfigUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * 图片水印处理器
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class ImageWaterMarkProcessor extends AbstractWaterMarkProcessor {

    @Override
    protected void doProcess(Attachment attachment) {
        String waterFile = ConfigUtils.getConfig(AttachUtils.ATTACH_WATERMARK_FILE);
        try {
            //水印图片存储路径会带上域名
            waterFile = waterFile.substring(waterFile.indexOf("/attachment"));
            String waterFilePath = AttachUtils.addFileWaterMark(new File(DirUtils.getUploadDir(), attachment.getFilePath().replace("/", "\\")),
                    new File(DirUtils.getUploadDir(), waterFile.replace("/", "\\")));
            attachment.setFilePath(waterFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isMatch(Attachment attachment) {
        return ImageUtils.isImageExtName(attachment.getFileName());
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
