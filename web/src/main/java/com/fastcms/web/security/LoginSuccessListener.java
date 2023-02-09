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

import com.fastcms.core.auth.FastcmsAuthUserInfo;
import com.fastcms.core.auth.FastcmsUserDetails;
import com.fastcms.entity.User;
import com.fastcms.service.IUserService;
import com.fastcms.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * wjun_java@163.com
 */
@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private IUserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if(event.getSource() != null && event.getAuthentication().getPrincipal() != null
                && event.getAuthentication().getPrincipal() instanceof FastcmsUserDetails) {
            FastcmsAuthUserInfo principal = (FastcmsAuthUserInfo) event.getAuthentication().getPrincipal();
            User user = principal.getUser();
            if(user != null) {
                user.setAccessIp(RequestUtils.getIpAddress(RequestUtils.getRequest()));
                user.setLoginTime(LocalDateTime.now());
                userService.updateById(user);
            }
        }
    }

}
