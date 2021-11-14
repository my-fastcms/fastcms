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
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.captcha.FastcmsCaptcha;
import com.fastcms.core.captcha.FastcmsCaptchaService;
import com.fastcms.web.security.AccessException;
import com.fastcms.web.security.AuthManager;
import com.fastcms.web.security.FastcmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录授权
 * @author： wjun_java@163.com
 * @date： 2021/2/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING)
public class AdminController {

    @Autowired
    private AuthManager authManager;

    @Autowired
    private FastcmsCaptchaService fastcmsCaptchaService;

    /**
     * 登录
     * @param username  账号|admin
     * @param password  密码|1
     * @param code      验证码
     * @return FastcmsUser
     */
    @PostMapping("login")
    public RestResult<FastcmsUser> login(@RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String code) {
        try {
            FastcmsUser user = authManager.login(username, password, code);
            return RestResultUtils.success(user);
        } catch (AccessException e) {
            return RestResultUtils.failed(e.getMessage());
        }

    }

    /**
     * 验证码
     * @return
     */
    @GetMapping("captcha")
    public RestResult<FastcmsCaptcha> captcha() {
        return RestResultUtils.success(fastcmsCaptchaService.getCaptcha());
    }

}
