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

package com.fastcms.oauth2.endpoint;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2022/01/30
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public final class OAuth2AuthorizationRequestPostProcessorManager implements ApplicationContextAware, ApplicationListener<ApplicationReadyEvent> {

    private static ApplicationContext applicationContext;

    private static final Map<String, OAuth2AuthorizationRequestPostProcessor> postProcessorMap = Collections.synchronizedMap(new HashMap<>());

    public static final void addPostProcessor(String registrationId, OAuth2AuthorizationRequestPostProcessor oAuth2AuthorizationRequestPostProcessor) {

        if (hasPostProcessor(registrationId)) {
            removePostProcessor(registrationId);
        }

        postProcessorMap.put(registrationId, oAuth2AuthorizationRequestPostProcessor);
    }

    public static final void removePostProcessor(String registrationId) {
        postProcessorMap.remove(registrationId);
    }

    public static final OAuth2AuthorizationRequestPostProcessor getPostProcessor(String registrationId) {
        if (hasPostProcessor(registrationId)) {
            return postProcessorMap.get(registrationId);
        }
        initOAuth2AuthorizationRequestPostProcessorMap();
        return postProcessorMap.get(registrationId);
    }

    public static final Boolean hasPostProcessor(String registrationId) {
        return postProcessorMap.containsKey(registrationId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initOAuth2AuthorizationRequestPostProcessorMap();
    }

    static void initOAuth2AuthorizationRequestPostProcessorMap() {
        Map<String, OAuth2AuthorizationRequestPostProcessor> oAuth2AuthorizationRequestPostProcessorMap = applicationContext.getBeansOfType(OAuth2AuthorizationRequestPostProcessor.class);
        oAuth2AuthorizationRequestPostProcessorMap.values().forEach(item -> addPostProcessor(item.getRegistrationId(), item));
    }

}
