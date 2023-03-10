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

import com.fastcms.core.utils.AttachUtils;
import com.fastcms.entity.Attachment;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.PluginUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.event.ApplicationReadyEvent;
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
public class WaterMarkAspect implements ApplicationListener<ApplicationReadyEvent> {

    List<WaterMarkProcessor> waterMarkProcessorList;

    @Around("execution(* com.fastcms.service.IAttachmentService.saveBatch(..))")
    public Boolean addWaterMark(ProceedingJoinPoint joinPoint) throws Throwable {

        if(AttachUtils.enableWaterMark()) {
            if(waterMarkProcessorList !=null && waterMarkProcessorList.size() > 0) {
                Object[] args = joinPoint.getArgs();
                if(args != null && args.length ==1) {

                    //添加插件扩展
                    List<WaterMarkProcessor> extensions = PluginUtils.getExtensions(WaterMarkProcessor.class);
                    waterMarkProcessorList.addAll(extensions);

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

        return (Boolean) joinPoint.proceed();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, WaterMarkProcessor> waterMarkProcessorMap = ApplicationUtils.getApplicationContext().getBeansOfType(WaterMarkProcessor.class);
        waterMarkProcessorList = Collections.synchronizedList(new ArrayList<>());
        waterMarkProcessorList.addAll(waterMarkProcessorMap.values());
    }

}
