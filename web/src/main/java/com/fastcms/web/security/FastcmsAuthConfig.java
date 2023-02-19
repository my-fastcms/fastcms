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

import com.fastcms.oauth2.authentication.FastcmsSavedRequestAwareAuthenticationSuccessHandler;
import com.fastcms.oauth2.endpoint.FastcmsAuthorizationCodeTokenResponseClient;
import com.fastcms.oauth2.endpoint.FastcmsOAuth2AuthorizationRequestResolver;
import com.fastcms.oauth2.userinfo.FastcmsOAuth2UserService;
import com.fastcms.utils.RequestUtils;
import com.fastcms.web.filter.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 *  @author： wjun_java@163.com
 *  * @date： 2021/10/24
 *  * @description：
 *  * @modifiedBy：
 *  * @version: 1.0
 */
@Configuration
@EnableWebSecurity
public class FastcmsAuthConfig {

    @Autowired
    private DelegatingTokenManager tokenManager;

    @Autowired
    private AuthConfigs authConfigs;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(authConfigs.getIgnoreUrls().toArray(new String[] {}))
                .and().ignoring().antMatchers(RequestUtils.getIgnoreUrls().toArray(new String[] {}));
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/fastcms.html");
        http.authorizeRequests().antMatchers("/fastcms/**").authenticated();
        http.csrf().disable().cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        http.oauth2Login(oAuth2LoginConfigurer
                -> oAuth2LoginConfigurer.authorizationEndpoint(
                authorizationEndpointConfig -> authorizationEndpointConfig.authorizationRequestResolver(
                        new FastcmsOAuth2AuthorizationRequestResolver(http.getSharedObject(ApplicationContext.class).getBean(ClientRegistrationRepository.class),
                                OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI)
                ))
                .tokenEndpoint(tokenEndpointConfig -> tokenEndpointConfig.accessTokenResponseClient(new FastcmsAuthorizationCodeTokenResponseClient()))
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(new FastcmsOAuth2UserService()))
                .successHandler(new FastcmsSavedRequestAwareAuthenticationSuccessHandler())
        );
        http.headers().cacheControl();
        http.headers().frameOptions().disable();
        http.addFilterBefore(new JwtAuthTokenFilter(tokenManager), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
