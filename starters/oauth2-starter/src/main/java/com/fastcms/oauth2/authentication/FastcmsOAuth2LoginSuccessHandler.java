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

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * OAuth2 授权登录成功处理逻辑
 * 具体逻辑由插件实现
 * @author： wjun_java@163.com
 * @date： 2022/02/02
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface FastcmsOAuth2LoginSuccessHandler {

    /**
     * 获取登录成功后跳转地址
     * @return
     */
    String getLoginSuccessUrl(HttpServletRequest request, Authentication authentication);

    /**
     * 获取OAuth2 RegistrationId
     * @return
     */
    String getRegistrationId();

}
