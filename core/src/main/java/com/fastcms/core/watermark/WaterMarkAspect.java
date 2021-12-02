package com.fastcms.core.watermark;

import com.fastcms.core.utils.AttachUtils;
import com.fastcms.utils.SpringContextHolder;
import com.fastcms.entity.Attachment;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 附件水印处理切面
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Aspect
@Component
public class WaterMarkAspect {

    @Around("execution(* com.fastcms.service.IAttachmentService.saveBatch(..))")
    public Object addWaterMark(ProceedingJoinPoint joinPoint) {

        if(AttachUtils.enableWaterMark()) {
            Map<String, WaterMarkProcessor> waterMarkProcessorMap = SpringContextHolder.getApplicationContext().getBeansOfType(WaterMarkProcessor.class);
            if(waterMarkProcessorMap.values().size() > 0) {
                Object[] args = joinPoint.getArgs();
                if(args != null && args.length ==1) {
                    List<Attachment> attachmentList = (List<Attachment>) args[0];
                    attachmentList.forEach(item -> waterMarkProcessorMap.values().stream().sorted(Comparator.comparing(WaterMarkProcessor::getOrder)).forEach(processor -> {
                        if(processor.isMatch(item)) {
                            processor.process(item);
                            return;
                        }
                    }));
                }
            }
        }

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

}
