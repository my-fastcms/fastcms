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
package com.fastcms.oauth2.userinfo;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

/**
 * 重写 DefaultOAuth2UserService
 * 支持在插件中动态注册请求转换类
 * @author： wjun_java@163.com
 * @date： 2022/02/1
 * @description：
 * @modifiedBy：
 * @version: 1.0
 * @see org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
 */
public class FastcmsOAuth2UserService <R extends OAuth2UserRequest, U extends OAuth2User> implements OAuth2UserService<R, U> {

    @Override
    public U loadUser(R userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");

        Map<String, OAuth2UserService> userServicesMap = OAuth2UserServiceManager.getUserServicesMap();

        OAuth2UserService<R, U> oAuth2UserService = userServicesMap.get(userRequest.getClientRegistration().getRegistrationId());
        if (oAuth2UserService == null) {
            oAuth2UserService = (OAuth2UserService<R, U>) new DefaultOAuth2UserService();
        }

        U u = oAuth2UserService.loadUser(userRequest);

        return u != null ? u : (U) userServicesMap.values().stream()
                .map((userService) -> userService.loadUser(userRequest))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

}
