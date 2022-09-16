package com.fastcms.core.auth;

import com.fastcms.common.auth.model.Permission;
import com.fastcms.common.auth.model.User;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.entity.Role;
import com.fastcms.service.IResourceService;
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
    
    @Autowired
    private IResourceService resourceService;

    /**
     * 判断用户是否有资源访问权限
     * @param user
     * @param permission
     * @return
     */
    public Boolean hasPermission(User user, Permission permission) {

        //超级用户拥有最大权限
        if (Objects.equals(user.getUserId(), FastcmsConstants.ADMIN_USER_ID)) {
            return true;
        }

        if (AuthUtils.isAdmin()) {
            return true;
        }

        //加载用户角色权限
        List<Role> userRoleList = roleService.getUserRoleList(user.getUserId());

        //访问系统admin资源需要管理员角色
        if(permission.getResource().contains(FastcmsConstants.ADMIN_MAPPING) && CollectionUtils.isEmpty(userRoleList)) {
            return false;
        }

        if(CollectionUtils.isNotEmpty(userRoleList)) {

            //需要检查角色接口权限
            List<String> userResourceList = resourceService.getUserResourceList(user.getUserId());
            for (String res : userResourceList) {
                if (Objects.equals(res, permission.getResource())) {
                    return true;
                }
            }

            return false;

        }

        return false;
    }

}
