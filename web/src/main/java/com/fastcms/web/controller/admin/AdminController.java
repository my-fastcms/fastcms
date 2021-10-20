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
package com.fastcms.web.controller.admin;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.response.Response;
import com.fastcms.core.utils.CaptchaUtils;
import com.fastcms.service.IUserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RequestMapping(FastcmsConstants.ADMIN_MAPPING)
@Controller
public class AdminController {

    @Autowired
    private IUserService userService;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @RequestMapping({"", "/", "index"})
    public String index() {
        return "admin/index";
    }

    @RequestMapping("login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("doLogin")
    public ResponseEntity doLogin(String loginAccount, String password, String captcha) {

        if(!CaptchaUtils.checkCaptcha(captcha)) {
            return Response.fail("验证码错误");
        }

//        UsernamePasswordToken token = new UsernamePasswordToken(loginAccount, password);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//            userService.updateUserLoginTime(loginAccount);
//            return Response.success();
//        } catch (UnknownAccountException e) {
//            return Response.fail("账号不存在");
//        } catch (AuthenticationException e) {
//            return Response.fail("密码不正确");
//        }
        return null;
    }

    @RequestMapping("unauthor")
    public String unauthor() {
        return "admin/unauthor";
    }

    @RequestMapping("captcha")
    public void captcha(HttpServletResponse response, HttpSession session) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        out.flush();
        out.close();
    }

}
