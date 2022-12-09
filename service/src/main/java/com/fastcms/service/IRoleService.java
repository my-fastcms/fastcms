package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.model.TreeNode;
import com.fastcms.entity.Permission;
import com.fastcms.entity.Resource;
import com.fastcms.entity.Role;

import java.io.Serializable;
import java.util.List;

/**
 *  角色服务类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface IRoleService extends IService<Role> {

    /**
     * 保存角色权限
     * @param roleId
     * @param permissionIdList
     * @param resourcePathList
     */
    void saveRolePermission(Long roleId, List<Long> permissionIdList, List<String> resourcePathList);

    /**
     * 获取角色已分配路由权限
     * @param roleId
     * @return
     */
    RolePermissions getRolePermission(Long roleId);

    /**
     * 保存用户角色
     * @param userId
     * @param roleIds
     */
    void saveUserRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户已分配角色集合
     * @param userId
     * @return
     */
    List<Role> getUserRoleList(Long userId);

    class UserRole extends Role {

        /**
         * 用户id
         */
        private Long userId;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }

    class RolePermission extends Permission implements Serializable {
        private Long roleId;

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }
    }

    class RoleResource extends Resource implements Serializable {

        private Long roleId;

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }
    }

    /**
     * 角色权限
     */
    class RolePermissions implements Serializable {
        /**
         * 菜单权限
         */
        List<TreeNode> permissions;
        /**
         * 接口权限
         */
        List<RoleResource> roleResources;

        public RolePermissions(List<TreeNode> permissions, List<RoleResource> roleResources) {
            this.permissions = permissions;
            this.roleResources = roleResources;
        }

        public List<TreeNode> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<TreeNode> permissions) {
            this.permissions = permissions;
        }

        public List<RoleResource> getRoleResources() {
            return roleResources;
        }

        public void setRoleResources(List<RoleResource> roleResources) {
            this.roleResources = roleResources;
        }
    }
    
    interface RoleI18n {
        String ROLE_NOT_ALLOW_DELETE = "fastcms.role.not.allow.delete";
        String ROLE_NOT_ALLOW_MODIFY = "fastcms.role.not.allow.modify";
        String ROLE_NOT_ALLOW_MODIFY_AUTH = "fastcms.role.not.allow.modify.auth";
    }

}
