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

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2022/01/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class OAuth2AuthorizationGrantRequestEntityConverterManager {

    public static final Map<String, AbstractOAuth2AuthorizationGrantRequestEntityConverter> requestEntityConverterMap = Collections.synchronizedMap(new HashMap<>());

    public static final void addRequestEntityConverter(String registrationId, AbstractOAuth2AuthorizationGrantRequestEntityConverter oAuth2AuthorizationGrantRequestEntityConverter) {
        Assert.state(!hasRequestEntityConverter(registrationId), () -> String.format("Duplicate key %s", registrationId));
        requestEntityConverterMap.put(registrationId, oAuth2AuthorizationGrantRequestEntityConverter);
    }

    public static final void removeRequestEntityConverter(String registrationId) {
        requestEntityConverterMap.remove(registrationId);
    }

    public static final AbstractOAuth2AuthorizationGrantRequestEntityConverter getRequestEntityConverter(String registrationId) {
        return requestEntityConverterMap.get(registrationId);
    }

    public static final Boolean hasRequestEntityConverter(String registrationId) {
        return requestEntityConverterMap.containsKey(registrationId);
    }

}
