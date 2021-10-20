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
package com.fastcms.web.security;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
public class SecurityConfig {

    @Bean
    public Realm realm(CredentialsMatcher credentialsMatcher) {
        UserRealm realm = new UserRealm();
        realm.setCachingEnabled(true);
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/static/**", "anon");

        chainDefinition.addPathDefinition("/admin/login", "anon");
        chainDefinition.addPathDefinition("/admin/doLogin", "anon");
        chainDefinition.addPathDefinition("/admin/unauthor", "anon");
        chainDefinition.addPathDefinition("/admin/captcha", "anon");
        chainDefinition.addPathDefinition("/admin/logout", "alogout");

        chainDefinition.addPathDefinition("/ucenter/register", "anon");
        chainDefinition.addPathDefinition("/ucenter/doRegister", "anon");
        chainDefinition.addPathDefinition("/ucenter/logout", "logout");

        chainDefinition.addPathDefinition("/api/**", "anon");

        chainDefinition.addPathDefinition("/admin/**", "authc");
        chainDefinition.addPathDefinition("/ucenter/**", "uauthc");

        return chainDefinition;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(16);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public FormAuthenticationFilter uauthc() {
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        formAuthenticationFilter.setLoginUrl("/ucenter/login");
        formAuthenticationFilter.setSuccessUrl("/");
        return formAuthenticationFilter;
    }

    @Bean
    public LogoutFilter alogout() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/admin/index");
        return logoutFilter;
    }

    @Bean
    public FilterRegistrationBean<LogoutFilter> shiroLogoutFilterRegistration(LogoutFilter filter) {
        FilterRegistrationBean<LogoutFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<FormAuthenticationFilter> shiroFormAuthenticationFilterRegistration(FormAuthenticationFilter filter) {
        FilterRegistrationBean<FormAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.image.width", "150");
        properties.put("kaptcha.image.height", "40");
        properties.put("kaptcha.textproducer.font.size", "30");
        properties.put("kaptcha.session.key", "verifyCode");
        properties.put("kaptcha.textproducer.char.space", "5");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
