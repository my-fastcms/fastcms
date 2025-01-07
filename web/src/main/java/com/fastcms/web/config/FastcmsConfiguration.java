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

import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.directive.BaseDirective;
import com.fastcms.core.freemarker.FastcmsFreeMarkerViewResolver;
import com.fastcms.core.site.DefaultSiteManager;
import com.fastcms.core.site.SiteContextFilter;
import com.fastcms.core.template.FastcmsStaticHtmlManager;
import com.fastcms.core.template.FastcmsTemplateFreeMarkerConfig;
import com.fastcms.core.template.TemplateService;
import com.fastcms.core.utils.AttachUtils;
import com.fastcms.core.utils.StaticUtils;
import com.fastcms.core.websocket.FastcmsWebSocketHandler;
import com.fastcms.plugin.PluginInterceptor;
import com.fastcms.plugin.view.PluginFreeMarkerConfig;
import com.fastcms.service.IConfigService;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.ConfigUtils;
import com.fastcms.utils.RequestUtils;
import com.fastcms.web.filter.AuthInterceptor;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.fastcms.common.constants.FastcmsConstants.WECHAT_MINIAPP_APP_ID;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
@EnableWebSocket
public class FastcmsConfiguration implements WebMvcConfigurer, WebSocketConfigurer, ApplicationListener<WebServerInitializedEvent> {

    @Autowired
    private IConfigService configService;

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private FastcmsTemplateFreeMarkerConfig fastcmsTemplateFreeMarkerConfig;

    @Autowired
    private PluginFreeMarkerConfig pluginFreeMarkerConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        StaticUtils.registerStaticResource(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PluginInterceptor()).addPathPatterns("/fastcms/plugin/**");
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/fastcms/api/**");
        registry.addInterceptor(new FastcmsLocaleChangeInterceptor(FastcmsLocaleChangeInterceptor.DEFAULT_PARAM_NAME)).addPathPatterns("/**");
    }

    @Bean
    public MessageSource messageSource(TemplateService templateService) {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasenames(templateService.getI18nNames());
        messageBundle.setCacheSeconds(300);
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName("fastcms.lang.LOCALE");
        return cookieLocaleResolver;
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

    @Bean
    public FilterRegistrationBean<SiteContextFilter> filterRegistrationBean(DefaultSiteManager siteManager) {
        FilterRegistrationBean<SiteContextFilter> frBean = new FilterRegistrationBean<>();
        frBean.setFilter(new SiteContextFilter(siteManager));
        frBean.addUrlPatterns("/*");
        return frBean;
    }

    @Bean
    public FilterRegistrationBean<WechatMiniAppFilter> wechatMiniAppFilterRegistrationBean() {
        FilterRegistrationBean<WechatMiniAppFilter> frBean = new FilterRegistrationBean<>();
        frBean.setFilter(new WechatMiniAppFilter());
        frBean.addUrlPatterns("/fastcms/api/*");
        frBean.addUrlPatterns("/fastcms/plugin/*");
        return frBean;
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

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        return new FastcmsFreeMarkerViewResolver();
    }

    void registerFreemarkerDirective(ApplicationStartedEvent event) {
        Map<String, BaseDirective> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(event.getApplicationContext(), BaseDirective.class, true, false);
        matchingBeans.keySet().forEach(item -> {
            configuration.setSharedVariable(item, matchingBeans.get(item));
            fastcmsTemplateFreeMarkerConfig.getConfiguration().setSharedVariable(item, matchingBeans.get(item));
            pluginFreeMarkerConfig.getConfiguration().setSharedVariable(item, matchingBeans.get(item));
        });
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new FastcmsWebSocketHandler(), "/fastcms/websocket").setAllowedOriginPatterns("*").withSockJS();
    }

    @Component
    class FreeMarkerDirectiveRegister implements ApplicationListener<ApplicationStartedEvent> {

        @Override
        public void onApplicationEvent(ApplicationStartedEvent event) {
            registerFreemarkerDirective(event);
        }
    }

    @Configuration
    public static class FastcmsUrlRewriteFilter extends UrlRewriteFilter {

        private static final String URL_REWRITE = "classpath:/urlrewrite.xml";

        // Inject the Resource from the given location
        @Value(URL_REWRITE)
        private Resource resource;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            if (ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).isFakeStaticEnable()) {
                super.doFilter(request, response, chain);
            } else {
                chain.doFilter(request, response);
            }

        }

        // Override the loadUrlRewriter method, and write your own implementation
        protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
            try {
                // Create a UrlRewrite Conf object with the injected resource
                Conf conf = new Conf(filterConfig.getServletContext(), resource.getInputStream(), resource.getFilename(), "@@fastcms@@");
                checkConf(conf);
            } catch (IOException ex) {
                throw new ServletException("Unable to load URL rewrite configuration file from " + URL_REWRITE, ex);
            }
        }
    }

    public static class WechatMiniAppFilter extends OncePerRequestFilter {

        final String APP_ID = "appId";

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            if (RequestUtils.isWechatBrowser(request)) {
                String appId = ConfigUtils.getConfig(WECHAT_MINIAPP_APP_ID);

                if (request.getHeader(APP_ID) != null) {
                    appId = request.getHeader(APP_ID);
                } else {
                    if (request.getParameter(APP_ID) != null) {
                        appId = request.getParameter(APP_ID);
                    }
                }

                WxMaConfigHolder.set(appId);
            }

            try {
                filterChain.doFilter(request, response);
            } finally {
                WxMaConfigHolder.remove();
            }

        }

    }

    public static class FastcmsLocaleChangeInterceptor extends LocaleChangeInterceptor {

        public FastcmsLocaleChangeInterceptor(String paramName) {
            setParamName(paramName);
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws ServletException {

            String newLocale = request.getParameter(getParamName());

            if (StrUtils.isBlank(newLocale)) {
                newLocale = request.getHeader("FastcmsLang");
            }

            if (newLocale != null) {
                if (checkHttpMethod(request.getMethod())) {
                    LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                    if (localeResolver == null) {
                        throw new IllegalStateException(
                                "No LocaleResolver found: not in a DispatcherServlet request?");
                    }
                    try {
                        localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
                    }
                    catch (IllegalArgumentException ex) {
                        if (isIgnoreInvalidLocale()) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + ex.getMessage());
                            }
                        }
                        else {
                            throw ex;
                        }
                    }
                }
            }
            // Proceed in any case.
            return true;
        }

        private boolean checkHttpMethod(String currentMethod) {
            String[] configuredMethods = getHttpMethods();
            if (ObjectUtils.isEmpty(configuredMethods)) {
                return true;
            }
            for (String configuredMethod : configuredMethods) {
                if (configuredMethod.equalsIgnoreCase(currentMethod)) {
                    return true;
                }
            }
            return false;
        }

    }

}
