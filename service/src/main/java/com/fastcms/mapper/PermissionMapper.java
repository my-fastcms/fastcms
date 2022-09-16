package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.Permission;
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
     * 查询用户已授权权限
     * 多个角色合并权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionByUserId(@Param("userId") Long userId);

}
