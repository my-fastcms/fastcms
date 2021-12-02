package com.fastcms.core.watermark;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.utils.AttachUtils;
import com.fastcms.utils.ConfigUtils;
import com.fastcms.entity.Attachment;
import org.apache.commons.lang.StringUtils;

/**
 * 水印处理逻辑父类
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractWaterMarkProcessor implements WaterMarkProcessor {

    @Override
    public void process(Attachment attachment) {

        if(!AttachUtils.enableWaterMark()) {
            return;
        }

        String watermark = ConfigUtils.getConfig(FastcmsConstants.ATTACH_WATERMARK_FILE);
        if(StringUtils.isBlank(watermark)) {
            return;
        }

        doProcess(attachment);
    }

    protected abstract void doProcess(Attachment attachment);

}
