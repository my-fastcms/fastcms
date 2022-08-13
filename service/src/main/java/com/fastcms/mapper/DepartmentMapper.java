package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-03-23
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    void deleteDepartmentByUserId(@Param("userId") Long userId);

    void saveUserDepartment(@Param("userId") Long userId, @Param("deptIds") List<Long> deptIds);

    List<Department> getUserDepartment(@Param("userId") Long userId);

    List<Long> getDepartmentUserIdList(@Param("deptId") Long deptId);

}
