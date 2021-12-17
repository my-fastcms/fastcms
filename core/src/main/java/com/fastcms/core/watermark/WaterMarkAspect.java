package com.fastcms.core.watermark;

import com.fastcms.core.utils.AttachUtils;
import com.fastcms.core.utils.PluginUtils;
import com.fastcms.entity.Attachment;
import com.fastcms.utils.SpringContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;

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
public class WaterMarkAspect implements ApplicationListener<ApplicationStartedEvent> {

    List<WaterMarkProcessor> waterMarkProcessorList;

    @Around("execution(* com.fastcms.service.IAttachmentService.saveBatch(..))")
    public Boolean addWaterMark(ProceedingJoinPoint joinPoint) {

        if(AttachUtils.enableWaterMark()) {
            if(waterMarkProcessorList !=null && waterMarkProcessorList.size() > 0) {
                Object[] args = joinPoint.getArgs();
                if(args != null && args.length ==1) {
                    List<Attachment> attachmentList = (List<Attachment>) args[0];
                    attachmentList.forEach(item -> waterMarkProcessorList.stream().sorted(Comparator.comparing(WaterMarkProcessor::getOrder)).forEach(processor -> {
                        if(processor.isMatch(item)) {
                            processor.process(item);
                            return;
                        }
                    }));
                }
            }
        }

        try {
            return (Boolean) joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Map<String, WaterMarkProcessor> waterMarkProcessorMap = SpringContextHolder.getApplicationContext().getBeansOfType(WaterMarkProcessor.class);
        waterMarkProcessorList = Collections.synchronizedList(new ArrayList<>());
        waterMarkProcessorList.addAll(waterMarkProcessorMap.values());

        //添加插件扩展
        List<WaterMarkProcessor> extensions = PluginUtils.getExtensions(WaterMarkProcessor.class);
        waterMarkProcessorList.addAll(extensions);

    }

}
