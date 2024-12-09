package com.fastcms.web.security;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.FastcmsAuthUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangjun
 **/
public class FastcmsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    HttpMessageConverter<Object> httpMessageConverter = new FastJsonHttpMessageConverter();

    @Autowired
    private TokenManager tokenManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        FastcmsUser token = tokenManager.createTokenUser((FastcmsAuthUserInfo) authentication.getPrincipal());
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpMessageConverter.write(RestResultUtils.success(token), MediaType.APPLICATION_JSON, httpResponse);
    }

}
