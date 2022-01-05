package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cache.CacheConfig;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RouterNode;
import com.fastcms.common.model.TreeNode;
import com.fastcms.common.model.TreeNodeConvert;
import com.fastcms.entity.Permission;
import com.fastcms.mapper.PermissionMapper;
import com.fastcms.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *  权限服务实现类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class PermissionServiceImpl<T extends TreeNode> extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService, TreeNodeConvert<T> {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public List<RouterNode> getPermissions(Long userId) {

        List<Permission> permissionList;
        if(Objects.equals(FastcmsConstants.ADMIN_USER_ID, userId)) {
            permissionList = list();
        } else {
            permissionList = getBaseMapper().getPermissionByUserId(userId);
        }

        return (List<RouterNode>) getPermissionNodeList(permissionList);
    }

    List<T> getPermissionNodeList(List<Permission> permissionList) {
        return getTreeNodeList(permissionList);
    }

    @Override
    @Cacheable(value = CacheConfig.ROLE_PERMISSION_CACHE_NAME, key = "#roleId")
    public List<RouterNode> getPermissionByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<RouterNode> getPermissionByUserId(Long userId) {
        List<Permission> permissionList = getBaseMapper().getPermissionByUserId(userId);
        return (List<RouterNode>) getPermissionNodeList(permissionList);
    }

    @Override
    public void deleteRolePermissionByPermission(List<Permission> permissionList) {
        for (Permission permission : permissionList) {
            getBaseMapper().deleteByPermissionId(permission.getId());
        }
        cacheManager.getCache(CacheConfig.USER_MENU_PERMISSION_CACHE_NAME).clear();
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
                permission.getIsIframe(),
                Arrays.asList("admin"));
        return (T) permissionNode;
    }

}
