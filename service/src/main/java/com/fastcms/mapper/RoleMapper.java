package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.Role;
import com.fastcms.service.IRoleService;
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
public interface RoleMapper extends BaseMapper<Role> {

    void saveRolePermission(@Param("roleId") Long roleId, @Param("permissionIdList") List<Long> permissionIdList);

    List<IRoleService.RolePermission> getRolePermission(@Param("roleId") Long roleId);

    void deletePermissionByRoleId(Long roleId);

    void saveRoleResource(@Param("roleId") Long roleId, @Param("resourcePathList") List<String> resourcePathList);

    List<IRoleService.RoleResource> getRoleResource(@Param("roleId") Long roleId);

    void deleteResourceByRoleId(Long roleId);

    void saveUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    void deleteRoleByUserId(@Param("userId") Long userId);

    List<IRoleService.UserRole> getRoleListByUserId(Long userId);

    List<Role> getUserRoleList(Long userId);

}
