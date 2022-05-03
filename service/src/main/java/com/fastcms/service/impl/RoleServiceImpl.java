package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cache.CacheConfig;
import com.fastcms.common.model.TreeNode;
import com.fastcms.entity.Role;
import com.fastcms.mapper.RoleMapper;
import com.fastcms.service.IRoleService;
import com.fastcms.utils.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fastcms.cache.CacheConfig.USER_MENU_PERMISSION_CACHE_NAME;

/**
 * 角色服务实现类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    @Transactional
    @CacheEvict(value = {CacheConfig.ROLE_PERMISSION_CACHE_NAME, CacheConfig.USER_MENU_PERMISSION_CACHE_NAME}, key = "#roleId")
    public void saveRolePermission(Long roleId, List<Long> permissionIdList, List<String> resourcePathList) {
        getBaseMapper().deletePermissionByRoleId(roleId);
        getBaseMapper().deleteResourceByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(permissionIdList)) {
            getBaseMapper().saveRolePermission(roleId, permissionIdList);
        }
        if (CollectionUtils.isNotEmpty(resourcePathList)) {
            getBaseMapper().saveRoleResource(roleId, resourcePathList);
        }
    }

    @Override
    public RolePermissions getRolePermission(Long roleId) {
        List<IRoleService.RolePermission> permissionList = getBaseMapper().getRolePermission(roleId);

        List<TreeNode> treeNodeList = new ArrayList<>();
        permissionList.forEach(item -> {
            TreeNode treeNode = new TreeNode(item.getId(), item.getParentId(), item.getTitle(), item.getIsHide());
            treeNode.setChecked(item.getRoleId() != null);
            treeNodeList.add(treeNode);
        });

        //递归组装children
        List<TreeNode> parentNodeList = treeNodeList.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
        parentNodeList.forEach(item -> getChildren(item, treeNodeList));

        return new RolePermissions(parentNodeList, getBaseMapper().getRoleResource(roleId));
    }

    void getChildren(TreeNode treeNode, List<TreeNode> treeNodeList) {
        List<TreeNode> childrenNodeList = treeNodeList.stream().filter(item -> Objects.equals(item.getParentId(), treeNode.getId())).collect(Collectors.toList());
        if(childrenNodeList != null && !childrenNodeList.isEmpty()) {
            treeNode.setChildren(childrenNodeList);
            childrenNodeList.forEach(item -> getChildren(item, treeNodeList));
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = USER_MENU_PERMISSION_CACHE_NAME, key = "#userId")
    public void saveUserRole(Long userId, List<Long> roleIds) {
        getBaseMapper().deleteRoleByUserId(userId);
        if(roleIds != null && roleIds.size()>0) {
            getBaseMapper().saveUserRole(userId, roleIds);
        }
    }

    @Override
    public List<Role> getUserRoleList(Long userId) {
        return getBaseMapper().getUserRoleList(userId);
    }

}
