package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.Role;
import com.fastcms.service.IRoleService;

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

    void saveRolePermission(Long roleId, List<Long> permissionIdList);

    void deleteByRoleId(Long roleId);

    void saveUserRole(Long userId, List<Long> roleIds);

    void deleteRoleByUserId(Long userId);

    List<IRoleService.UserRole> getRoleListByUserId(Long userId);

    List<Role> getUserRoleList(Long userId);

}
