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
import com.fastcms.core.captcha.FastcmsCaptcha;
import com.fastcms.core.captcha.FastcmsCaptchaService;
import com.fastcms.extension.IndexDataExtension;
import com.fastcms.service.IUserService;
import com.fastcms.utils.I18nUtils;
import com.fastcms.utils.PluginUtils;
import com.fastcms.web.security.AuthManager;
import com.fastcms.web.security.FastcmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fastcms.cache.CacheConfig.ADMIN_INDEX_DATA_CACHE_NAME;

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
    public RestResult<FastcmsUser> login(@RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    @RequestParam(name = "code") String code) throws AccessException {
        return RestResultUtils.success(authManager.login(username, password, code));
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
    public RestResult<Boolean> register(@RequestParam("username") String username,
                                        @RequestParam("nickName") String nickName,
                                        @RequestParam("password") String password,
                                        @RequestParam("repeatPassword") String repeatPassword,
                                        @RequestParam("code") String code) throws FastcmsException {

        if(!fastcmsCaptchaService.checkCaptcha(code)) {
            return RestResultUtils.failed(I18nUtils.getMessage(IUserService.UserI18n.USER_LOGIN_CAPTCHA_ERROR));
        }

        return RestResultUtils.success(userService.register(username, nickName, password, repeatPassword));
    }

    /**
     * 重置密码
     * @param username
     * @param code
     * @return
     * @throws FastcmsException
     */
    @PostMapping("reset/password")
    public RestResult resetPassword(@RequestParam("username") String username,
                                    @RequestParam("email") String email,
                                    @RequestParam("code") String code) throws FastcmsException {

        if(!fastcmsCaptchaService.checkCaptcha(code)) {
            return RestResultUtils.failed(I18nUtils.getMessage(IUserService.UserI18n.USER_LOGIN_CAPTCHA_ERROR));
        }
        userService.resetPassword(username, email);
        return RestResultUtils.success();
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
     * 首页数据
     * @return
     */
    @GetMapping("index/data")
	@Cacheable(value = ADMIN_INDEX_DATA_CACHE_NAME)
    public Object getIndexData() {

        final Map<String, Object> result = new HashMap<>();

        List<IndexDataExtension> extensions = PluginUtils.getExtensions(IndexDataExtension.class);
        for (IndexDataExtension extension : extensions) {
            result.putAll(extension.getData());
        }

        return RestResultUtils.success(result);
    }

}
