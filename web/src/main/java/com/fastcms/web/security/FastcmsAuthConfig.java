package com.fastcms.web.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FastcmsAuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        String ignoringUrls = "/,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico";

        for (String s : ignoringUrls.trim().split(",")) {
            web.ignoring().antMatchers(s.trim());
        }
    }

}
