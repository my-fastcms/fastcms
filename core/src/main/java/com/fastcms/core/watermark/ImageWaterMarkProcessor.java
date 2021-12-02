package com.fastcms.core.watermark;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.ImageUtils;
import com.fastcms.core.utils.AttachUtils;
import com.fastcms.core.utils.DirUtils;
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
        String waterFile = ConfigUtils.getConfig(FastcmsConstants.ATTACH_WATERMARK_FILE);
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
    public Boolean isMatch(Attachment attachment) {
        return ImageUtils.isImageExtName(attachment.getFileName());
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
