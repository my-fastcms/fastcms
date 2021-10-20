package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cache.CacheConfig;
import com.fastcms.entity.Role;
import com.fastcms.mapper.RoleMapper;
import com.fastcms.service.IRoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    @Transactional
    @CacheEvict(value = {CacheConfig.ROLE_PERMISSION_CACHE_NAME, CacheConfig.USER_MENU_PERMISSION_CACHE_NAME}, key = "#roleId")
    public void saveRolePermission(Long roleId, List<Long> permissionIdList) {
        getBaseMapper().deleteByRoleId(roleId);
        getBaseMapper().saveRolePermission(roleId, permissionIdList);
    }

    @Override
    @CacheEvict(value = {CacheConfig.ROLE_PERMISSION_CACHE_NAME, CacheConfig.USER_MENU_PERMISSION_CACHE_NAME}, key = "#roleId")
    public void saveRolePermissionOfPlugin(Long roleId, List<Long> permissionIdList) {
        getBaseMapper().saveRolePermission(roleId, permissionIdList);
    }

    @Override
    @Transactional
    public void saveUserRole(Long userId, List<Long> roleIds) {
        getBaseMapper().deleteRoleByUserId(userId);
        getBaseMapper().saveUserRole(userId, roleIds);
    }

    @Override
    public List<RoleMapper.UserRole> getRoleListByUserId(Long userId) {
        return getBaseMapper().getRoleListByUserId(userId);
    }

    @Override
    public List<Role> getUserRoleList(Long userId) {
        return getBaseMapper().getUserRoleList(userId);
    }

}
