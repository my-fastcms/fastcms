package com.fastcms.core.auth;

import com.fastcms.common.auth.model.Permission;
import com.fastcms.common.auth.model.User;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.entity.Role;
import com.fastcms.service.IRoleService;
import com.fastcms.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AuthPermissionService {

    @Autowired
    private IRoleService roleService;

    /**
     * 判断用户是否有资源访问权限
     * @param user
     * @param permission
     * @return
     */
    public Boolean hasPermission(User user, Permission permission) {

        //超级用户拥有最大权限
        if(Objects.equals(user.getUserId(), FastcmsConstants.ADMIN_USER_ID)) {
            return true;
        }

        //加载用户角色权限
        List<Role> userRoleList = roleService.getUserRoleList(user.getUserId());

        //访问系统admin资源需要管理员角色
        if(permission.getResource().contains(FastcmsConstants.ADMIN_MAPPING) && CollectionUtils.isEmpty(userRoleList)) {
            return false;
        }

        if(CollectionUtils.isNotEmpty(userRoleList)) {

            for (Role role : userRoleList) {
                //检查是否拥有超级管理员权限
                if(Objects.equals(FastcmsConstants.ADMIN_ROLE_ID, role.getId())) {
                    return true;
                }
            }

            //需要检查角色资源权限
            for (Role role : userRoleList) {

            }
            return false;

        }

        return false;
    }

}
