package com.fastcms.core.attach;

import com.fastcms.entity.Attachment;
import org.springframework.core.Ordered;

/**
 * 附件水印处理器
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface WaterMarkProcessor extends Ordered {

    /**
     * 对附件进行水印处理
     * @param attachment
     */
    void process(Attachment attachment);

    /**
     * 是否匹配
     * @param attachment
     * @return
     */
    boolean isMatch(Attachment attachment);

}
