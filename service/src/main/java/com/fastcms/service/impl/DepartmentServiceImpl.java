package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.model.TreeNode;
import com.fastcms.common.model.TreeNodeConvert;
import com.fastcms.entity.Department;
import com.fastcms.mapper.DepartmentMapper;
import com.fastcms.service.IDepartmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-03-23
 */
@Service
public class DepartmentServiceImpl<T extends TreeNode> extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService, TreeNodeConvert<T> {

    @Override
    public T convert2Node(Object object) {
        return null;
    }

}
