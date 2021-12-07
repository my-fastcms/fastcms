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
import com.fastcms.core.template.Template;
import com.fastcms.core.template.TemplateService;
import com.fastcms.core.utils.AttachUtils;
import com.fastcms.core.utils.DirUtils;
import com.fastcms.service.IConfigService;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
public class FastcmsConfig implements WebMvcConfigurer, ApplicationListener<WebServerInitializedEvent> {

    @Autowired
    private IConfigService configService;

    @Autowired
    private TemplateService templateService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        final String uploadDir = DirUtils.getUploadDir();
        final String templateDir = DirUtils.getTemplateDir();

        List<String> locations = new ArrayList<>();
        locations.add("classpath:/static/");
        locations.add(ResourceUtils.FILE_URL_PREFIX + uploadDir);
        for (Template template : templateService.getTemplateList()) {
            locations.add(ResourceUtils.FILE_URL_PREFIX + templateDir + template.getPathName() + "/static/");
        }

        registry.addResourceHandler("/**").addResourceLocations(locations.toArray(new String[]{}));
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
        if(StringUtils.isBlank(configService.getValue(FastcmsConstants.SERVER_IP))) {
            configService.saveConfig(FastcmsConstants.SERVER_IP, AttachUtils.getInternetIp());
        }
        if(StringUtils.isBlank(configService.getValue(FastcmsConstants.SERVER_PORT))) {
            configService.saveConfig(FastcmsConstants.SERVER_PORT, String.valueOf(event.getWebServer().getPort()));
        }
        if(StringUtils.isBlank(configService.getValue(FastcmsConstants.WEBSITE_DOMAIN))) {
            configService.saveConfig(FastcmsConstants.WEBSITE_DOMAIN, "http://" + configService.getValue(FastcmsConstants.SERVER_IP) + ":" + configService.getValue(FastcmsConstants.SERVER_PORT));
        }
    }

}
