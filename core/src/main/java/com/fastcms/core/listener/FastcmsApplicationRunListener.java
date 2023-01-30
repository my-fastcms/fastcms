package com.fastcms.core.listener;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.common.utils.VersionUtils;
import com.fastcms.core.utils.AttachUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static org.springframework.boot.context.logging.LoggingApplicationListener.CONFIG_PROPERTY;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

/**
 * wjun_java@163.com
 */
public class FastcmsApplicationRunListener implements SpringApplicationRunListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastcmsApplicationRunListener.class);

    private static final String DEFAULT_FASTCMS_LOGBACK_LOCATION = CLASSPATH_URL_PREFIX + "META-INF/logback/fastcms.xml";

    private final SpringApplication application;

    private final String[] args;

    private static File workDir;
    final static String [] dirNames = { "upload", "plugins", "htmls", "lucene" };

    private Boolean isDev = false;

    static {

        try {
            workDir = new File(ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if(!workDir.exists()) {
            workDir = new File(".");
        }

    }

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

        String[] activeProfiles = environment.getActiveProfiles();
        String profile = activeProfiles == null || activeProfiles.length <=0 ? FastcmsConstants.DEV_MODE : activeProfiles[0];

        if(FastcmsConstants.DEV_MODE.equals(profile)) {
            isDev = true;
        }

        if (!environment.containsProperty(CONFIG_PROPERTY)) {
            System.setProperty(CONFIG_PROPERTY, DEFAULT_FASTCMS_LOGBACK_LOCATION);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("There is no property named \"{}\" in Spring Boot Environment, "
                                + "and whose value is {} will be set into System's Properties", CONFIG_PROPERTY,
                        DEFAULT_FASTCMS_LOGBACK_LOCATION);
            }
        }

        for (String dirName : dirNames) {
            File dir = new File(workDir.getAbsolutePath(), dirName);
            if(!dir.exists()) dir.mkdirs();
        }

        DirUtils.injectUploadDir(getUploadDir());
        DirUtils.injectPluginDir(getPluginDir());
        DirUtils.injectTemplateDir(getTemplateDir());
        DirUtils.injectLuceneDir(getLuceneDir());
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

    String getUploadDir() {
        return workDir.getAbsolutePath() + File.separator + dirNames[0] + File.separator;
    }

    String getPluginDir() {
        return workDir.getAbsolutePath() + File.separator + dirNames[1] + File.separator;
    }

    String getTemplateDir() {
        if (isDev) {
            String osName = System.getProperty("os.name");
            if (osName.contains("Windows")) {
                String substring = workDir.getAbsolutePath().replace("\\target\\classes", "").replace("\\target\\test-classes", "");
                substring = substring.substring(0, substring.lastIndexOf("\\"));
                return substring.concat("\\templates\\target\\classes\\");
            }
            if (osName.contains("Mac")) {
                String substring = workDir.getAbsolutePath().replace("/target/classes", "").replace("/target/test-classes", "");
                substring = substring.substring(0, substring.lastIndexOf("/"));
                return substring.concat("/templates/target/classes/");
            }
        }
        return workDir.getAbsolutePath() + File.separator + dirNames[2] + File.separator;
    }

    String getLuceneDir() {
        return workDir.getAbsolutePath() + File.separator + dirNames[3] + File.separator;
    }

}
