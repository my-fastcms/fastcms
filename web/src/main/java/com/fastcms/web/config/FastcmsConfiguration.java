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
package com.fastcms.web.config;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.core.directive.BaseDirective;
import com.fastcms.core.template.Template;
import com.fastcms.core.template.TemplateService;
import com.fastcms.core.utils.AttachUtils;
import com.fastcms.plugin.PluginInterceptor;
import com.fastcms.plugin.view.FastcmsTemplateFreeMarkerConfig;
import com.fastcms.plugin.view.PluginFreeMarkerConfig;
import com.fastcms.service.IConfigService;
import com.fastcms.web.filter.AuthInterceptor;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
public class FastcmsConfiguration implements WebMvcConfigurer, ApplicationListener<WebServerInitializedEvent> {

    @Autowired
    private IConfigService configService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private FastcmsTemplateFreeMarkerConfig fastcmsTemplateFreeMarkerConfig;

    @Autowired
    private PluginFreeMarkerConfig pluginFreeMarkerConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        final String uploadDir = DirUtils.getUploadDir();
        final String templateDir = DirUtils.getTemplateDir();

        Set<String> locations = new HashSet<>();
        locations.add("classpath:" + FastcmsConstants.TEMPLATE_STATIC);
        locations.add(ResourceUtils.FILE_URL_PREFIX + uploadDir);
        for (Template template : templateService.getTemplateList()) {
            locations.add(ResourceUtils.FILE_URL_PREFIX + templateDir + template.getPath() + FastcmsConstants.TEMPLATE_STATIC);
        }

        registry.addResourceHandler("/**").addResourceLocations(locations.toArray(new String[]{}));

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PluginInterceptor()).addPathPatterns("/fastcms/plugin/**");
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/fastcms/api/**");
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.setMaxAge(18000L);
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(ZoneId.systemDefault().toString());
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class,localDateTimeDeserializer());
            jacksonObjectMapperBuilder.simpleDateFormat(pattern);
        };
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {

        try {
            initServerInfo(event);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    void initServerInfo(WebServerInitializedEvent event) throws Exception {
        if(StringUtils.isBlank(configService.getValue(FastcmsConstants.SERVER_IP))) {
            configService.saveConfig(FastcmsConstants.SERVER_IP, AttachUtils.getInternetIp());
        }
        if(StringUtils.isBlank(configService.getValue(FastcmsConstants.SERVER_PORT))) {
            configService.saveConfig(FastcmsConstants.SERVER_PORT, String.valueOf(event.getWebServer().getPort()));
        }
        if(StringUtils.isBlank(configService.getValue(FastcmsConstants.WEBSITE_DOMAIN))) {
            configService.saveConfig(FastcmsConstants.WEBSITE_DOMAIN,
                    "http://" + configService.getValue(FastcmsConstants.SERVER_IP) + ":" + configService.getValue(FastcmsConstants.SERVER_PORT));
        }
    }

    void registerFreemarkerDirective(ApplicationStartedEvent event) {
        Map<String, BaseDirective> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(event.getApplicationContext(), BaseDirective.class, true, false);
        matchingBeans.keySet().forEach(item -> {
            configuration.setSharedVariable(item, matchingBeans.get(item));
            fastcmsTemplateFreeMarkerConfig.getConfiguration().setSharedVariable(item, matchingBeans.get(item));
            pluginFreeMarkerConfig.getConfiguration().setSharedVariable(item, matchingBeans.get(item));
        });
    }

    @Component
    class FreeMarkerDirectiveRegister implements ApplicationListener<ApplicationStartedEvent> {

        @Override
        public void onApplicationEvent(ApplicationStartedEvent event) {
            registerFreemarkerDirective(event);
        }
    }

    @Configuration
    public class UrlRewriteFilterConfig extends UrlRewriteFilter {

        private static final String URL_REWRITE = "classpath:/urlrewrite.xml";

        // Inject the Resource from the given location
        @Value(URL_REWRITE)
        private Resource resource;

        // Override the loadUrlRewriter method, and write your own implementation
        protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
            try {
                // Create a UrlRewrite Conf object with the injected resource
                Conf conf = new Conf(filterConfig.getServletContext(), resource.getInputStream(), resource.getFilename(), "@@traceability@@");
                checkConf(conf);
            } catch (IOException ex) {
                throw new ServletException("Unable to load URL rewrite configuration file from " + URL_REWRITE, ex);
            }
        }
    }

}
