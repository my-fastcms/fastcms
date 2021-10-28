package com.fastcms.web.security;

import com.fastcms.entity.Permission;
import com.fastcms.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 *  @author： wjun_java@163.com
 *  * @date： 2021/10/24
 *  * @description：
 *  * @modifiedBy：
 *  * @version: 1.0
 */
public interface AuthManager {

    /**
     * 发起授权请求，从request中获取username，password
     * 或者直接获取token
     * @param request
     * @return 返回认证用户
     */
    User login(HttpServletRequest request);

    /**
     * 通过授权用户以及用户访问资源，检查当前用户是否有授权
     * @param permission
     * @param user
     * @throws AccessException
     */
    void auth(Permission permission, User user) throws AccessException;

}
