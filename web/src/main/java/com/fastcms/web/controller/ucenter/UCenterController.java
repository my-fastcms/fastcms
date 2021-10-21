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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.response.Response;
import com.fastcms.core.utils.CaptchaUtils;
import com.fastcms.entity.User;
import com.fastcms.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Controller
@RequestMapping(FastcmsConstants.UCENTER_MAPPING)
public class UCenterController extends UCenterBaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping({"", "index"})
    public String index(){
        return "ucenter/index";
    }

    @RequestMapping("login")
    public String login() {
        return "ucenter/login";
    }

    @RequestMapping("register")
    public String register() {
        return "ucenter/register";
    }

    @PostMapping("doRegister")
    public ResponseEntity doRegister(String loginAccount, String nickName, String email, String password, String captcha) {

        if(!CaptchaUtils.checkCaptcha(captcha)) {
            return Response.fail("验证码错误");
        }

        if(StringUtils.isBlank(loginAccount)) {
            return Response.fail("请输入账号");
        }

        if(StringUtils.isBlank(nickName)) {
            return Response.fail("请输入昵称");
        }

        if(StringUtils.isBlank(email)) {
            return Response.fail("请输入邮箱地址");
        }

        if(StringUtils.isBlank(password)) {
            return Response.fail("请输入密码");
        }

        User userInDb = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getLoginAccount, loginAccount));
        if(userInDb != null) {
            return Response.fail("登录账号不可重复");
        }

        final String salt = System.currentTimeMillis() + "";
//        final String md5password = PasswordUtils.getMd5Password(salt, password);
        User user = new User();
        user.setLoginAccount(loginAccount);
        user.setNickName(nickName);
//        user.setPassword(md5password);
        user.setEmail(email);
        user.setSalt(salt);
        user.setSource(User.SourceType.WEB_REGISTER.name().toLowerCase());
        userService.saveOrUpdate(user);

        return Response.success();
    }

}
