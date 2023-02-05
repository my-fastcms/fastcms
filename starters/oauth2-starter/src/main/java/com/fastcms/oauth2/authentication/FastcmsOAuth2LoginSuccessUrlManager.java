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

import org.springframework.util.Assert;

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
public final class FastcmsOAuth2LoginSuccessUrlManager {

    private static final Map<String, FastcmsOAuth2LoginSuccessHandler> oAuth2LoginSuccessUrlMap = Collections.synchronizedMap(new HashMap<>());

    public static final void addOAuth2LoginSuccessUrl(String registrationId, FastcmsOAuth2LoginSuccessHandler fastcmsOAuth2LoginSuccessUrl) {
        Assert.state(!hasOAuth2LoginSuccessUrl(registrationId), () -> String.format("Duplicate key %s", registrationId));
        oAuth2LoginSuccessUrlMap.put(registrationId, fastcmsOAuth2LoginSuccessUrl);
    }

    public static final void removeOAuth2LoginSuccessUrl(String registrationId) {
        oAuth2LoginSuccessUrlMap.remove(registrationId);
    }

    public static final FastcmsOAuth2LoginSuccessHandler getOAuth2LoginSuccessUrl(String registrationId) {
        return oAuth2LoginSuccessUrlMap.get(registrationId);
    }

    public static final Boolean hasOAuth2LoginSuccessUrl(String registrationId) {
        return oAuth2LoginSuccessUrlMap.containsKey(registrationId);
    }

}
