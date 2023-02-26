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
package com.fastcms.oauth2.authentication;

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
 * @date： 2022/02/02
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public final class FastcmsOAuth2LoginSuccessUrlManager implements ApplicationContextAware, ApplicationListener<ApplicationReadyEvent> {

    private static ApplicationContext applicationContext;

    private static final Map<String, FastcmsOAuth2LoginSuccessHandler> oAuth2LoginSuccessUrlMap = Collections.synchronizedMap(new HashMap<>());

    public static final void addOAuth2LoginSuccessUrl(String registrationId, FastcmsOAuth2LoginSuccessHandler fastcmsOAuth2LoginSuccessUrl) {
        if (hasOAuth2LoginSuccessUrl(registrationId)) {
            removeOAuth2LoginSuccessUrl(registrationId);
        }
        oAuth2LoginSuccessUrlMap.put(registrationId, fastcmsOAuth2LoginSuccessUrl);
    }

    public static final void removeOAuth2LoginSuccessUrl(String registrationId) {
        oAuth2LoginSuccessUrlMap.remove(registrationId);
    }

    public static final FastcmsOAuth2LoginSuccessHandler getOAuth2LoginSuccessUrl(String registrationId) {
        if (hasOAuth2LoginSuccessUrl(registrationId)) {
            return oAuth2LoginSuccessUrlMap.get(registrationId);
        }

        initFastcmsOAuth2LoginSuccessHandlerMap();

        return oAuth2LoginSuccessUrlMap.get(registrationId);
    }

    public static final Boolean hasOAuth2LoginSuccessUrl(String registrationId) {
        return oAuth2LoginSuccessUrlMap.containsKey(registrationId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initFastcmsOAuth2LoginSuccessHandlerMap();
    }

    static void initFastcmsOAuth2LoginSuccessHandlerMap() {
        Map<String, FastcmsOAuth2LoginSuccessHandler> fastcmsOAuth2LoginSuccessHandlerMap = applicationContext.getBeansOfType(FastcmsOAuth2LoginSuccessHandler.class);
        fastcmsOAuth2LoginSuccessHandlerMap.values().forEach(item -> addOAuth2LoginSuccessUrl(item.getRegistrationId(), item));
    }

}
