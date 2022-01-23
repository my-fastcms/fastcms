package com.fastcms.core.payment;

import com.fastcms.payment.PaymentPlatform;
import com.fastcms.payment.PaymentPlatformService;
import com.fastcms.payment.PaymentPlatforms;
import com.fastcms.plugin.FastcmsPluginManager;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * wjun_java@163.com
 * 支付平台管理器
 */
@Component
public class FastcmsPaymentPlatformService implements PaymentPlatformService, ApplicationListener<ApplicationStartedEvent> {

    @Override
    public PaymentPlatform getPlatform(String platform) {
        return PaymentPlatforms.getPaymentPlatform(platform);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
//        //扫描支付平台实现类并注册
//        Map<String, PaymentPlatform> waterMarkProcessorMap = applicationContext.getBeansOfType(PaymentPlatform.class);
//        waterMarkProcessorMap.values().forEach(item -> PaymentPlatforms.loadPaymentPlatform(item));

        //添加插件扩展
        List<PaymentPlatform> extensions = applicationContext.getBean(FastcmsPluginManager.class).getExtensions(PaymentPlatform.class);
        extensions.forEach(item -> PaymentPlatforms.loadPaymentPlatform(item));
    }

}
