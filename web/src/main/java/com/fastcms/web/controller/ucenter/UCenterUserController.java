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
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.permission.UcenterMenu;
import com.fastcms.entity.User;
import com.fastcms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@UcenterMenu(name = "我的资料", sort = 1)
@RequestMapping(FastcmsConstants.UCENTER_MAPPING + "/user")
public class UCenterUserController extends UCenterBaseController {

	@Autowired
	private IUserService userService;

	@PostMapping("doSave")
	public Object doSave(@Validated User user) {
		userService.updateById(user);
		return RestResultUtils.success();
	}

	@PostMapping("doEditPwd")
	public Object doEditPwd(User user) {
		try {
			userService.updateUserPassword(user);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

	@PostMapping("doSaveAvatar")
	public Object doSaveAvatar(String path, int x, int y, int w, int h) {
		return RestResultUtils.success();
	}

}
