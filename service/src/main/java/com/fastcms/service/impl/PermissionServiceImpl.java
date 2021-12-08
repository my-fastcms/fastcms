package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.auth.AuthUtils;
import com.fastcms.cache.CacheConfig;
import com.fastcms.common.model.RouterNode;
import com.fastcms.entity.Permission;
import com.fastcms.mapper.PermissionMapper;
import com.fastcms.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  权限服务实现类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public List<RouterNode> getPermissions() {
        List<Permission> permissionList = list();
        if(!AuthUtils.isAdmin()) {
            permissionList = getBaseMapper().getPermissionByUserId(AuthUtils.getUserId());
        }
        return getPermissionNodeList(permissionList);
    }

    List<RouterNode> getPermissionNodeList(List<Permission> permissionList) {
        List<RouterNode> permissionNodeList = permissionList.stream().map(item -> getPermissionNode(item)).collect(Collectors.toList());
        List<RouterNode> parents = permissionNodeList.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
        parents.forEach(item -> getChildren(item, permissionNodeList));
        return parents.stream().sorted(Comparator.comparing(RouterNode::getMenuSort).reversed()).collect(Collectors.toList());
    }

    void getChildren(RouterNode permissionNode, List<RouterNode> menuNodeList) {
        List<RouterNode> childrenNodeList = menuNodeList.stream().filter(item -> Objects.equals(item.getParentId(), permissionNode.getId())).collect(Collectors.toList());
        if(childrenNodeList != null && !childrenNodeList.isEmpty()) {
            permissionNode.setChildren(childrenNodeList);
            childrenNodeList.forEach(item -> getChildren(item, menuNodeList));
        }
    }

    private RouterNode getPermissionNode(Permission permission) {
        RouterNode permissionNode = new RouterNode(permission.getId(), permission.getParentId(), permission.getName(), permission.getPath(), permission.getComponent(),
                permission.getIsLink(), permission.getSortNum(), permission.getTitle(), permission.getIcon(),
                permission.getIsHide(), permission.getIsKeepAlive(), permission.getIsAffix(),
                permission.getIsIframe(), Arrays.asList("admin"), null);
        return permissionNode;
    }

    @Override
    @Cacheable(value = CacheConfig.ROLE_PERMISSION_CACHE_NAME, key = "#roleId")
    public List<RouterNode> getPermissionByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<RouterNode> getPermissionByUserId(Long userId) {
        List<Permission> permissionList = getBaseMapper().getPermissionByUserId(userId);
        return getPermissionNodeList(permissionList);
    }

    @Override
    public void deleteRolePermissionByPermission(List<Permission> permissionList) {
        for (Permission permission : permissionList) {
            getBaseMapper().deleteByPermissionId(permission.getId());
        }
        cacheManager.getCache(CacheConfig.USER_MENU_PERMISSION_CACHE_NAME).clear();
    }

}
