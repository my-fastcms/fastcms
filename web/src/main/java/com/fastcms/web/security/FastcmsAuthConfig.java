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

import com.fastcms.core.auth.ControllerMethodsCache;
import com.fastcms.web.filter.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 *  @author： wjun_java@163.com
 *  * @date： 2021/10/24
 *  * @description：
 *  * @modifiedBy：
 *  * @version: 1.0
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FastcmsAuthConfig extends WebSecurityConfigurerAdapter implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private JwtTokenManager tokenManager;

    @Autowired
    private AuthConfigs authConfigs;

    @Autowired
    private ControllerMethodsCache controllerMethodsCache;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(authConfigs.getIgnoreUrls().toArray(new String[]{}));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/index.html");
        http.authorizeRequests().antMatchers("/fastcms/**").authenticated();
        http.csrf().disable().cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        http.headers().cacheControl();
        http.headers().frameOptions().disable();

        http.addFilterBefore(new JwtAuthTokenFilter(tokenManager, controllerMethodsCache), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        controllerMethodsCache.initClassMethod("com.fastcms.web.controller.admin");
        controllerMethodsCache.initClassMethod("com.fastcms.web.controller.api");
        controllerMethodsCache.initClassMethod("com.fastcms.cms.controller.admin");
        controllerMethodsCache.initClassMethod("com.fastcms.cms.controller.api");
    }

}
