package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Role;
import com.fastcms.mapper.RoleMapper;

import java.util.List;

/**
 * <p>
 *  角色服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface IRoleService extends IService<Role> {

    /**
     * 保存角色权限
     * @param roleId
     * @param permissionIdList
     */
    void saveRolePermission(Long roleId, List<Long> permissionIdList);

    /**
     * 为指定角色保存插件菜单权限
     * @param roleId
     * @param permissionIdList
     */
    void saveRolePermissionOfPlugin(Long roleId, List<Long> permissionIdList);

    /**
     * 保存用户角色
     * @param userId
     * @param roleIds
     */
    void saveUserRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    List<UserRole> getRoleListByUserId(Long userId);

    /**
     * 获取用户分配角色集合
     * @param userId
     * @return
     */
    List<Role> getUserRoleList(Long userId);

    class UserRole extends Role {
        private Long userId;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }

}
