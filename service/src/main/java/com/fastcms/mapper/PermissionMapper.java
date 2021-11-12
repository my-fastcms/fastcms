package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.Permission;
import com.fastcms.service.IPermissionService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询角色对应的权限
     * @param roleId
     * @return
     */
    List<IPermissionService.RolePermission> getPermissionByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询用户已授权权限
     * 多个角色合并权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionByUserId(@Param("userId") Long userId);

    /**
     * 删除权限
     * @param permissionId
     */
    void deleteByPermissionId(Long permissionId);

}
