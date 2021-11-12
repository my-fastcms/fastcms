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
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.web.security.JwtTokenManager;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.AccessException;
import java.util.HashMap;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenManager tokenManager;

    @Autowired
    private CacheManager cacheManager;

    private static final String WEB_LOGIN_CODE_CACHE_NAME = "web_login_code";

    /**
     * 登录接口
     * @param username  账号|admin
     * @param password  密码|1
     * @param code      验证码
     * @param codeKey   验证码key
     * @param request   请求
     * @param response  响应
     * @return
     * @throws AccessException
     */
    @PostMapping("login")
    public Object login(@RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String code,
                                    @RequestParam String codeKey,
                                    HttpServletRequest request, HttpServletResponse response) throws AccessException {

        Cache.ValueWrapper valueWrapper = cacheManager.getCache(WEB_LOGIN_CODE_CACHE_NAME).get(codeKey);
        String codeInMemory = StrUtils.isBlank (codeKey) ? "" : (valueWrapper == null) ? "" : (String) valueWrapper.get();
        if(StrUtils.isNotBlank(codeKey)) {
            cacheManager.getCache(WEB_LOGIN_CODE_CACHE_NAME).evict(codeKey);
        }

        if(StrUtils.isBlank(code) || !code.equalsIgnoreCase(codeInMemory)) {
            return RestResultUtils.failed("验证码错误");
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            String token = tokenManager.createToken(authenticate.getName());

            return RestResultUtils.success(token);

        } catch (AuthenticationException e) {
            return RestResultUtils.failed(e.getMessage());
        }

    }

    /**
     * 验证码接口
     * @return
     */
    @GetMapping("captcha")
    public Object captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        final String verCode = specCaptcha.text().toLowerCase();
        final String key = StrUtils.uuid();

        cacheManager.getCache(WEB_LOGIN_CODE_CACHE_NAME).put(key, verCode);

        Map<String, String> result = new HashMap<>();
        result.put("verCode", verCode);
        result.put("codeUuid", key);
        result.put("image", specCaptcha.toBase64());
        return RestResultUtils.success(result);
    }

}
