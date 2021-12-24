package com.fastcms.core.attach;

import com.fastcms.entity.Attachment;
import org.springframework.stereotype.Service;

/**
 * pdf水印处理器
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class PDFWaterMarkProcessor extends AbstractWaterMarkProcessor {

    @Override
    protected void doProcess(Attachment attachment) {

    }

    @Override
    public boolean isMatch(Attachment attachment) {
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
