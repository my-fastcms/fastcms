package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cache.CacheConfig;
import com.fastcms.entity.Permission;
import com.fastcms.mapper.PermissionMapper;
import com.fastcms.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public List<PermissionNode> getPermissions() {
        List<Permission> permissionList = list();
        List<PermissionNode> permissionNodeList = new ArrayList<>();

        permissionList.forEach(item -> permissionNodeList.add(getPermissionNode(item)));
        List<PermissionNode> parents = permissionNodeList.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
        parents.forEach(item -> getChildren(item, permissionNodeList));
        return parents.stream().sorted(Comparator.comparing(PermissionNode::getMenuSort).reversed()).collect(Collectors.toList());
    }

    void getChildren(PermissionNode permissionNode, List<PermissionNode> menuNodeList) {
        List<PermissionNode> childrenNodeList = menuNodeList.stream().filter(item -> Objects.equals(item.getParentId(), permissionNode.getId())).collect(Collectors.toList());
        if(childrenNodeList != null && !childrenNodeList.isEmpty()) {
            permissionNode.setChildren(childrenNodeList);
            childrenNodeList.forEach(item -> getChildren(item, menuNodeList));
        }
    }

    private PermissionNode getPermissionNode(Permission permission) {
        PermissionNode permissionNode = new PermissionNode(permission.getId(), permission.getParentId(), permission.getName(), permission.getPath(), permission.getComponent(),
                permission.getIsLink(), permission.getSortNum(), permission.getTitle(), permission.getIcon(),
                permission.getIsHide(), permission.getIsKeepAlive(), permission.getIsAffix(),
                permission.getIsIframe(), Arrays.asList("admin"), null);
        return permissionNode;
    }

    @Override
    @Cacheable(value = CacheConfig.ROLE_PERMISSION_CACHE_NAME, key = "#roleId")
    public List<PermissionNode> getPermissionByRoleId(Long roleId) {
        List<RolePermission> rolePermissionList = getBaseMapper().getPermissionByRoleId(roleId);
        return null;
    }

    @Override
    public List<Permission> getPermissionByUserId(Long userId) {
        return getBaseMapper().getPermissionByUserId(userId);
    }

    @Override
//    @Cacheable(value = CacheConfig.USER_MENU_PERMISSION_CACHE_NAME, key = "#userId")
    public List<PermissionNode> getUserMenuPermission(Long userId) {
        List<Permission> userPermissionList = getPermissionByUserId(userId);

        return null;
    }

    @Override
    public void deleteRolePermissionByPermission(List<Permission> permissionList) {
        for (Permission permission : permissionList) {
            getBaseMapper().deleteByPermissionId(permission.getId());
        }
        cacheManager.getCache(CacheConfig.USER_MENU_PERMISSION_CACHE_NAME).clear();
    }

}
