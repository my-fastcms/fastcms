package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.model.RouterNode;
import com.fastcms.entity.Permission;

import java.util.List;

/**
 * 权限服务类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 获取角色授权
     * @param userId
     * @return
     */
    List<Permission> getUserPermissionList(Long userId);

    /**
     * 获取系统权限列表
     * @return userId
     */
    List<RouterNode> getUserPermissionsMenu(Long userId);

    /**
     * 获取所有路由数据
     * @return
     */
    List<RouterNode> getPermissions();

    interface PermissionI18n {
        String ROUTES_CHILDREN_NOT_DELETE = "fastcms.routes.children.not.delete";
    }

}
