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

import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.response.Response;
import com.fastcms.service.IUserService;
import com.fastcms.web.security.JwtTokenManager;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
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
 * @author： wjun_java@163.com
 * @date： 2021/2/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping("fastcms")
public class AdminController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenManager tokenManager;

    @Autowired
    private CacheManager cacheManager;

    @PostMapping("login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password, @RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws AccessException {

        String codeInMemory = (String) cacheManager.getCache("web_login_code").get(request.getHeader("code-uuid")).get();

        if(StrUtils.isBlank(code) || !code.equalsIgnoreCase(codeInMemory)) {
            return ResponseEntity.status(403).build();
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            String token = tokenManager.createToken(authenticate.getName());

            return Response.success(token);

        } catch (AuthenticationException e) {
            throw new AccessException("unknown user!");
        }

    }

    @GetMapping("captcha")
    public ResponseEntity captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = StrUtils.uuid();

        cacheManager.getCache("web_login_code").put(key, verCode);

        Map<String, String> result = new HashMap<>();
        result.put("code-uuid", key);
        result.put("image", specCaptcha.toBase64());
        return Response.success(result);
    }

}
