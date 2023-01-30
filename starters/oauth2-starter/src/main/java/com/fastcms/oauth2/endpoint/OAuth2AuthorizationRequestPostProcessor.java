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

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

/**
 * @author： wjun_java@163.com
 * @date： 2022/01/30
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface OAuth2AuthorizationRequestPostProcessor {

    /**
     * 对OAuth2AuthorizationRequest属性进行包装处理
     * @param oAuth2AuthorizationRequest
     * @return
     */
    OAuth2AuthorizationRequest process(OAuth2AuthorizationRequest oAuth2AuthorizationRequest);

}
