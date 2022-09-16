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
package com.fastcms.web.controller.api;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.entity.User;
import com.fastcms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 * @author： wjun_java@163.com
 * @date： 2021/7/24
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/user")
public class UserApi {

    @Autowired
    private IUserService userService;

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("save")
    public RestResult<Boolean> save(@RequestBody IUserService.UpdateUserParam user) {

        User userInfo = userService.getById(AuthUtils.getUserId());
        if(userInfo == null) {
            return RestResultUtils.failed("user is null");
        }

        userInfo.setRealName(user.getRealName());
        userInfo.setNickName(user.getNickName());
        userInfo.setEmail(user.getEmail());
        userInfo.setMobile(user.getMobile());
        userInfo.setSex(user.getSex());
        userInfo.setAutograph(user.getAutograph());

        return RestResultUtils.success(userService.updateById(userInfo));
    }

    /**
     * 用户详情
     * @return
     */
    @GetMapping("get")
    public RestResult<User> getUserInfo(@RequestParam(value = "userId", required = false) Long userId) {
        return RestResultUtils.success(userService.getById(userId == null ? AuthUtils.getUserId() : userId));
    }

    /**
     * 修改密码
     * @param updatePasswordParam
     * @return
     */
    @PostMapping("password/update")
    public Object updatePwd(@Validated IUserService.UpdatePasswordParam updatePasswordParam) throws FastcmsException {
        updatePasswordParam.setId(AuthUtils.getUserId());
        userService.updateUserPassword(updatePasswordParam);
        return RestResultUtils.success();
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

}
