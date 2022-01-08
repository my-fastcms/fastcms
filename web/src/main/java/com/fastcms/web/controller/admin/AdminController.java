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
import com.fastcms.common.exception.AccessException;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.ActionTypes;
import com.fastcms.core.auth.AuthConstants;
import com.fastcms.core.auth.Secured;
import com.fastcms.core.captcha.FastcmsCaptcha;
import com.fastcms.core.captcha.FastcmsCaptchaService;
import com.fastcms.extension.IndexDataExtension;
import com.fastcms.core.utils.PluginUtils;
import com.fastcms.service.IUserService;
import com.fastcms.web.security.AuthManager;
import com.fastcms.web.security.FastcmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IUserService userService;

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
     * 注册
     * @param username
     * @param password
     * @param repeatPassword
     * @param code
     * @return
     */
    @PostMapping("register")
    public RestResult<Boolean> register(@RequestParam String username,
                                        @RequestParam String password,
                                        @RequestParam String repeatPassword,
                                        @RequestParam String code) {

        if(!fastcmsCaptchaService.checkCaptcha(code)) {
            return RestResultUtils.failed("验证码错误");
        }

        try {
            return RestResultUtils.success(userService.register(username, password, repeatPassword));
        } catch (FastcmsException e) {
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

    /**
     * 获取首页数据
     * @return
     */
    @GetMapping("index/data")
    @Secured(resource = AuthConstants.ADMIN_RESOURCE_NAME_PREFIX + "datas", action = ActionTypes.READ)
    public Object getIndexData() {

        final Map<String, Object> result = new HashMap<>();

        List<IndexDataExtension> extensions = PluginUtils.getExtensions(IndexDataExtension.class);
        for (IndexDataExtension extension : extensions) {
            result.putAll(extension.getData());
        }

        return RestResultUtils.success(result);
    }

}
