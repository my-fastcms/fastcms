package com.fastcms.web.security;

import com.fastcms.auth.FastcmsUserDetails;
import com.fastcms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * wjun_java@163.com
 */
@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private IUserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if(event.getSource() != null && event.getAuthentication().getPrincipal() != null) {
            FastcmsUserDetails principal = (FastcmsUserDetails) event.getAuthentication().getPrincipal();
            userService.updateUserLoginTime(principal.getUserId());
        }
    }

}
