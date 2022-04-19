package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RouterNode;
import com.fastcms.common.model.TreeNode;
import com.fastcms.common.model.TreeNodeConvert;
import com.fastcms.entity.Permission;
import com.fastcms.mapper.PermissionMapper;
import com.fastcms.service.IPermissionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.fastcms.cache.CacheConfig.USER_MENU_PERMISSION_CACHE_NAME;

/**
 *  权限服务实现类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class PermissionServiceImpl<T extends TreeNode> extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService, TreeNodeConvert<T> {

    @Override
    @Cacheable(value = USER_MENU_PERMISSION_CACHE_NAME, key = "#userId")
    public List<RouterNode> getPermissions(Long userId) {

        List<Permission> permissionList;
        if(Objects.equals(FastcmsConstants.ADMIN_USER_ID, userId)) {
            permissionList = list();
        } else {
            permissionList = getBaseMapper().getPermissionByUserId(userId);
        }

        return (List<RouterNode>) getPermissionNodeList(permissionList);
    }

    List<T> getPermissionNodeList(List<Permission> permissionList) {
        return getTreeNodeList(permissionList);
    }

    @Override
    public T convert2Node(Object object) {
        Permission permission = (Permission) object;
        RouterNode permissionNode = new RouterNode(
                permission.getId(),
                permission.getParentId(),
                permission.getName(),
                permission.getPath(),
                permission.getComponent(),
                permission.getIsLink(),
                permission.getSortNum(),
                permission.getTitle(),
                permission.getIcon(),
                permission.getIsHide(),
                permission.getIsKeepAlive(),
                permission.getIsAffix(),
                permission.getIsIframe());
        return (T) permissionNode;
    }

}
