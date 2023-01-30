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

package com.fastcms.oauth2.autoconfigure;

import com.fastcms.oauth2.registration.FastcmsInMemoryClientRegistrationRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

/**
 * spring security oauth2 重要的filter
 * 1，OAuth2AuthorizationRequestRedirectFilter
 *      负责拦截/oauth2/authorization授权请求，然后重定向到第三方确认授权页面
 * 2，OAuth2LoginAuthenticationFilter
 *      负责拦截用户在第三方确认授权之后的回调url地址：/login/oauth2/code/
 *
 * fastcms oauth2 config
 * @author： wjun_java@163.com
 * @date： 2022/03/02
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class FastcmsOauth2AutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ClientRegistrationRepository.class)
    FastcmsInMemoryClientRegistrationRepository clientRegistrationRepository() {
        return new FastcmsInMemoryClientRegistrationRepository();
    }

}
