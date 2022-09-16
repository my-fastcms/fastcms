package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RouterNode;
import com.fastcms.common.model.TreeNode;
import com.fastcms.common.model.TreeNodeConvert;
import com.fastcms.entity.Permission;
import com.fastcms.entity.Role;
import com.fastcms.mapper.PermissionMapper;
import com.fastcms.service.IPermissionService;
import com.fastcms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.fastcms.cache.CacheConfig.USER_MENU_PERMISSION_CACHE_NAME;

/**
 *  权限服务实现类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class PermissionServiceImpl<T extends TreeNode> extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService, TreeNodeConvert<T> {

    @Autowired
    private IRoleService roleService;

    @Override
    public List<Permission> getUserPermissionList(Long userId) {
        return getBaseMapper().getPermissionByUserId(userId);
    }

    @Override
    @Cacheable(value = USER_MENU_PERMISSION_CACHE_NAME, key = "#userId")
    public List<RouterNode> getUserPermissionsMenu(Long userId) {

        List<Role> userRoleList = roleService.getUserRoleList(userId);

        boolean isAdmin = false;
        for (Role role : userRoleList) {
            //检查是否拥有超级管理员权限
            if(Objects.equals(FastcmsConstants.ADMIN_ROLE_ID, role.getId())) {
                isAdmin = true;
                break;
            }
        }

        List<Permission> permissionList;
        if(isAdmin || Objects.equals(FastcmsConstants.ADMIN_USER_ID, userId)) {
            permissionList = list();
        } else {
            permissionList = getUserPermissionList(userId);
        }

        return (List<RouterNode>) getPermissionNodeList(permissionList);
    }

    @Override
    public List<RouterNode> getPermissions() {
        List<Permission> list = list();
        return (List<RouterNode>) getPermissionNodeList(list);
    }

    List<T> getPermissionNodeList(List<Permission> permissionList) {
        return getTreeNodeList(permissionList);
    }

    @Override
    public T convert2Node(Object object) {
        Permission permission = (Permission) object;
        RouterNode permissionNode = new RouterNode(
                permission.getId(),
                permission.getParentId(),
                permission.getName(),
                permission.getPath(),
                permission.getComponent(),
                permission.getIsLink(),
                permission.getSortNum(),
                permission.getTitle(),
                permission.getIcon(),
                permission.getIsHide(),
                permission.getIsKeepAlive(),
                permission.getIsAffix(),
                permission.getIsIframe());
        return (T) permissionNode;
    }

}
