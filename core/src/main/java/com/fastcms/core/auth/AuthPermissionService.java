package com.fastcms.core.auth;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.auth.model.Permission;
import com.fastcms.core.auth.model.User;
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

        //没有角色授权，不可访问管理后台api
        List<Role> userRoleList = roleService.getUserRoleList(user.getUserId());
        if(permission.getResource().contains(FastcmsConstants.ADMIN_MAPPING) && CollectionUtils.isEmpty(userRoleList)) {
            return false;
        }

        if(CollectionUtils.isNotEmpty(userRoleList)) {
            //需要检查角色资源权限
            return true;
        }

        return false;
    }

}
