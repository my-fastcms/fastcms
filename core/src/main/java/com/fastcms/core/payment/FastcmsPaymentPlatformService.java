package com.fastcms.core.payment;

import com.fastcms.payment.PaymentPlatform;
import com.fastcms.payment.PaymentPlatformService;
import com.fastcms.payment.PaymentPlatforms;
import com.fastcms.plugin.FastcmsPluginManager;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * wjun_java@163.com
 * 支付平台管理器
 */
@Component
public class FastcmsPaymentPlatformService implements PaymentPlatformService, ApplicationListener<ApplicationStartedEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public PaymentPlatform getPlatform(String platform) {
        PaymentPlatform paymentPlatform = PaymentPlatforms.getPaymentPlatform(platform);
        if(paymentPlatform == null) {
            //从插件中查找
            getExtPaymentPlatform();
            paymentPlatform = PaymentPlatforms.getPaymentPlatform(platform);
        }
        return paymentPlatform;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
//        //扫描支付平台实现类并注册
        Map<String, PaymentPlatform> paymentPlatformMap = applicationContext.getBeansOfType(PaymentPlatform.class);
        paymentPlatformMap.values().forEach(item -> PaymentPlatforms.loadPaymentPlatform(item));

        getExtPaymentPlatform();
    }

    private void getExtPaymentPlatform() {
        //添加插件扩展
        List<PaymentPlatform> extensions = applicationContext.getBean(FastcmsPluginManager.class).getExtensions(PaymentPlatform.class);
        extensions.forEach(item -> PaymentPlatforms.loadPaymentPlatform(item));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
