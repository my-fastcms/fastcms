package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.model.RouterNode;
import com.fastcms.entity.Permission;

import java.io.Serializable;
import java.util.List;

/**
 * 权限服务类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 获取系统权限列表
     * @return
     */
    List<RouterNode> getPermissions();

    /**
     * 获取角色对应的权限
     * @param roleId
     * @return
     */
    List<RouterNode> getPermissionByRoleId(Long roleId);

    /**
     * 获取用户已授权权限
     * @param userId
     * @return
     */
    List<RouterNode> getPermissionByUserId(Long userId);

    /**
     * 删除掉权限
     * @param permissionList
     */
    void deleteRolePermissionByPermission(List<Permission> permissionList);

    class RolePermission extends Permission implements Serializable {
        private Long roleId;

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }
    }

}
