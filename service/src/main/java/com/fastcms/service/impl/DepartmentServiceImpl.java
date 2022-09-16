package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.model.TreeNode;
import com.fastcms.common.model.TreeNodeConvert;
import com.fastcms.entity.Department;
import com.fastcms.mapper.DepartmentMapper;
import com.fastcms.service.IDepartmentService;
import com.fastcms.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门服务实现类
 * @author wjun_java@163.com
 * @since 2022-03-23
 */
@Service
public class DepartmentServiceImpl<T extends TreeNode> extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService, TreeNodeConvert<T> {

    @Override
    public List<DepartmentNode> getDepartmentList(Integer status) {
        List<Department> departmentList = list(Wrappers.<Department>lambdaQuery().eq(status != null, Department::getStatus, status));
        return (List<DepartmentNode>) getTreeNodeList(departmentList);
    }

    @Override
    public List<DepartmentNode> getDepartmentList() {
        return getDepartmentList(null);
    }

    @Override
    public void saveUserDepartments(Long userId, List<Long> deptIdList) {
        getBaseMapper().deleteDepartmentByUserId(userId);
        if (CollectionUtils.isNotEmpty(deptIdList)) {
            getBaseMapper().saveUserDepartment(userId, deptIdList);
        }
    }

    @Override
    public List<Long> getDepartmentUserIdList(Long deptId) {
        return getBaseMapper().getDepartmentUserIdList(deptId);
    }

    @Override
    public List<Department> getUserDepartment(Long userId) {
        return getBaseMapper().getUserDepartment(userId);
    }

    @Override
    public T convert2Node(Object object) {
        Department department = (Department) object;
        DepartmentNode departmentNode = new DepartmentNode(department.getId(),
                department.getParentId(),
                department.getDeptName(),
                department.getDeptDesc(),
                department.getStatus(),
                department.getDeptLeader(),
                department.getSortNum(),
                department.getDeptPhone(),
                department.getCreated()
        );
        return (T) departmentNode;
    }

}
