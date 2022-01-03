package com.fastcms.web.security;

import com.fastcms.web.filter.AuthFilter;
import com.fastcms.web.filter.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FastcmsAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenManager tokenManager;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/fastcms/api/admin/login", "/fastcms/api/admin/captcha", "/payback");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/index.html");
        http.authorizeRequests().antMatchers("/fastcms/**").authenticated();
        http.csrf().disable().cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        http.headers().cacheControl();
        http.headers().frameOptions().disable();

        http.addFilterBefore(new JwtAuthTokenFilter(tokenManager), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authFilter());
        registration.addUrlPatterns("/*");
        registration.setName("authFilter");
        registration.setOrder(6);

        return registration;
    }

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

}
