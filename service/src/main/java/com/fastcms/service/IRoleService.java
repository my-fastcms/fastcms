package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.model.TreeNode;
import com.fastcms.entity.Role;

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
     */
    void saveRolePermission(Long roleId, List<Long> permissionIdList);

    /**
     * 获取角色已分配路由权限
     * @param roleId
     * @return
     */
    List<TreeNode> getRolePermission(Long roleId);

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

}
