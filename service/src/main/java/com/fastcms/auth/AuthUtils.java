package com.fastcms.auth;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * wjun_java@163.com
 */
public final class AuthUtils {

    private AuthUtils() {}

    public static Long getUserId() {
        return getUser().getUserId();
    }

    public static String getUsername() {
        return getUser().getUsername();
    }

    public static FastcmsUserDetails getUser() {
        return (FastcmsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Boolean isAdmin() {
        return getUser().isAdmin();
    }

}
