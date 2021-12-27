package com.fastcms.core.listener;

import com.fastcms.common.utils.VersionUtils;
import com.fastcms.core.utils.AttachUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.springframework.boot.context.logging.LoggingApplicationListener.CONFIG_PROPERTY;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

/**
 * wjun_java@163.com
 */
public class FastcmsApplicationRunListener implements SpringApplicationRunListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastcmsApplicationRunListener.class);

    private static final String DEFAULT_NACOS_LOGBACK_LOCATION = CLASSPATH_URL_PREFIX + "META-INF/logback/fastcms.xml";

    private final SpringApplication application;

    private final String[] args;

    public FastcmsApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {

    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        System.setProperty("application.version", VersionUtils.getFullClientVersion());
        System.setProperty("fastcms.local.ip", AttachUtils.getInternetIp());

        if (!environment.containsProperty(CONFIG_PROPERTY)) {
            System.setProperty(CONFIG_PROPERTY, DEFAULT_NACOS_LOGBACK_LOCATION);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("There is no property named \"{}\" in Spring Boot Environment, "
                                + "and whose value is {} will be set into System's Properties", CONFIG_PROPERTY,
                        DEFAULT_NACOS_LOGBACK_LOCATION);
            }
        }

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }

}
