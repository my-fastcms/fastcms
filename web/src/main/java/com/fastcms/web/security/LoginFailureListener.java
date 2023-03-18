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

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.entity.User;
import com.fastcms.service.IUserService;
import com.fastcms.utils.ConfigUtils;
import com.fastcms.utils.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import static com.fastcms.service.IUserService.UserI18n.USER_PASSWORD_ERROR_COUNT_LIMT;

/**
 * @author： wjun_java@163.com
 * @date： 2023/3/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class LoginFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	private IUserService userService;

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {

		if (event.getAuthentication().getPrincipal() != null) {
			String username = event.getAuthentication().getPrincipal().toString();
			User user = userService.getByUsername(username);
			if (user != null) {

				if (user.getErrorCount() >= getLimitErrorCount()) {
					user.setStatus(FastcmsConstants.STATUS_DEL);
				}

				user.setErrorCount(user.getErrorCount() + 1);
				userService.updateById(user);

				if (user.getErrorCount() > getLimitErrorCount() / 2) {
					String message = I18nUtils.getMessage(USER_PASSWORD_ERROR_COUNT_LIMT);
					throw new LockedException(String.format(message, username, getLimitErrorCount() - user.getErrorCount()));
				}

			}
		}
	}

	Integer getLimitErrorCount() {
		return ConfigUtils.getInt(FastcmsConstants.ALLOW_PWD_ERROR_COUNT, 10);
	}

}
