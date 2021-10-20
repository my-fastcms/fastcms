package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cache.CacheConfig;
import com.fastcms.entity.Permission;
import com.fastcms.mapper.PermissionMapper;
import com.fastcms.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Cacheable(value = CacheConfig.ROLE_PERMISSION_CACHE_NAME, key = "#roleId")
    public List<PermissionTreeNode> getPermissionByRoleId(Long roleId) {
        List<PermissionMapper.RolePermission> rolePermissionList = getBaseMapper().getPermissionByRoleId(roleId);
        List<PermissionTreeNode> treeNodeList = new PermissionTreeNodeConvert<PermissionTreeNode>().convert2NodeList(rolePermissionList);
        return treeNodeList;
    }

    @Override
    public List<Permission> getPermissionByUserId(Long userId) {
        return getBaseMapper().getPermissionByUserId(userId);
    }

    @Override
//    @Cacheable(value = CacheConfig.USER_MENU_PERMISSION_CACHE_NAME, key = "#userId")
    public List<MenuNode> getUserMenuPermission(Long userId) {
        List<Permission> userPermissionList = getPermissionByUserId(userId);
        List<MenuNode> menuNodeList = new MenuNodeConvert<MenuNode>().convert2NodeList(userPermissionList);
        return menuNodeList;
    }

    @Override
    public List<MenuNode> getUCenterMenuList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category", Permission.CATEGORY_CENTER);
        queryWrapper.or();
        queryWrapper.isNull("parent_id");
        List<Permission> permissionList = list(queryWrapper);
        List<MenuNode> menuNodeList = new MenuNodeConvert<MenuNode>().convert2NodeList(permissionList);
        return menuNodeList;
    }

    @Override
    public void deleteRolePermissionByPermission(List<Permission> permissionList) {
        for (Permission permission : permissionList) {
            getBaseMapper().deleteByPermissionId(permission.getId());
        }
        cacheManager.getCache(CacheConfig.USER_MENU_PERMISSION_CACHE_NAME).clear();
    }

}
