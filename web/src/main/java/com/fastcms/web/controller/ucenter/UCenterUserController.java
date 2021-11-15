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
package com.fastcms.web.controller.ucenter;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.entity.User;
import com.fastcms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 * @author： wjun_java@163.com
 * @date： 2021/6/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.UCENTER_MAPPING + "/user")
public class UCenterUserController {

	@Autowired
	private IUserService userService;

	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	@PostMapping("save")
	public RestResult<Boolean> save(@Validated User user) {
		return RestResultUtils.success(userService.updateById(user));
	}

	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	@PostMapping("password/update")
	public Object updatePwd(User user) {
		try {
			userService.updateUserPassword(user);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

	/**
	 * 保存头像
	 * @param path
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	@PostMapping("avatar/save")
	public Object saveAvatar(String path, int x, int y, int w, int h) {
		return RestResultUtils.success();
	}

	/**
	 * 用户注册
	 * @param username	账号
	 * @param nickName	昵称
	 * @param email		邮箱
	 * @param password	密码
	 * @param captcha	验证码
	 * @return
	 */
	@PostMapping("register")
	public Object register(String username, String nickName, String email, String password, String captcha) {

		final String salt = System.currentTimeMillis() + "";
//        final String md5password = PasswordUtils.getMd5Password(salt, password);
		User user = new User();
		user.setUserName(username);
		user.setNickName(nickName);
//        user.setPassword(md5password);
		user.setEmail(email);
		user.setSalt(salt);
		user.setSource(User.SourceType.WEB_REGISTER.name().toLowerCase());
		userService.saveOrUpdate(user);

		return RestResultUtils.success();
	}

}
