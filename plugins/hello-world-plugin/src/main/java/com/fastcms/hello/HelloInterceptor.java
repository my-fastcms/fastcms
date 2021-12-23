package com.fastcms.hello;

import com.fastcms.plugin.InterceptPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * wjun_java@163.com
 */
@InterceptPath({ "/hello/**" })
public class HelloInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HelloInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("=============>>>HelloInterceptor.preHandle");
        return true;
    }

}
