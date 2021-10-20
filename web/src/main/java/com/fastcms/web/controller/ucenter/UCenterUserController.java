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
import com.fastcms.core.permission.UcenterMenu;
import com.fastcms.core.response.Response;
import com.fastcms.core.utils.FileUtils;
import com.fastcms.core.utils.ImageUtils;
import com.fastcms.entity.User;
import com.fastcms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Controller
@UcenterMenu(name = "我的资料", sort = 1)
@RequestMapping(FastcmsConstants.UCENTER_MAPPING + "/user")
public class UCenterUserController extends UCenterBaseController {

	@Autowired
	private IUserService userService;

	@UcenterMenu(name = "基本信息", sort = 1)
	@RequestMapping("info")
	public String info(Model model) {
		model.addAttribute("user", userService.getById(getLoginUser().getId()));
		return "ucenter/user/info";
	}

	@PostMapping("doSave")
	public ResponseEntity doSave(@Validated User user) {
		user.setLoginAccount(getLoginUser().getLoginAccount());
		userService.updateById(user);
		return Response.success();
	}

	@UcenterMenu(name = "头像设置", sort = 2)
	@RequestMapping("avatar")
	public String avatar() {
		return "ucenter/user/avatar";
	}

	@UcenterMenu(name = "账号密码", sort = 3)
	@RequestMapping("pwd")
	public String pwd(Model model) {
		model.addAttribute("user", userService.getById(getLoginUser().getId()));
		return "ucenter/user/pwd";
	}

	@PostMapping("doEditPwd")
	public ResponseEntity doEditPwd(User user) {
		try {
			userService.updateUserPassword(user);
			return Response.success();
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}

	@PostMapping("doSaveAvatar")
	public ResponseEntity doSaveAvatar(String path, int x, int y, int w, int h) {

		User user = userService.getById(getLoginUser().getId());
		if(user == null) {
			return Response.fail("该用户不存在");
		}

		String attachmentRoot = FileUtils.getUploadDir();

		String oldPath = attachmentRoot + path;

		String zoomPath = FileUtils.newFile(path).getAbsolutePath();
		//500的值必须和 html图片的max-width值一样
		ImageUtils.zoom(500, oldPath, zoomPath);

		String newAvatarPath = FileUtils.newFile(path).getAbsolutePath();
		ImageUtils.crop(zoomPath, newAvatarPath, x, y, w, h);

		String newPath = FileUtils.removePrefix(newAvatarPath, attachmentRoot).replace("\\", "/");

		//刷新session
		getLoginUser().setHeadImg(newPath);
		user.setHeadImg(newPath);
		userService.saveOrUpdate(user);
		return Response.success();
	}

}
