package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  权限服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 获取角色对应的权限
     * @param roleId
     * @return
     */
    List<MenuNode> getPermissionByRoleId(Long roleId);

    /**
     * 获取用户已授权权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionByUserId(Long userId);

    /**
     * 获取用户菜单权限
     * @param userId
     * @return
     */
    List<MenuNode> getUserMenuPermission(Long userId);

    /**
     * 获取用户中心菜单
     * @return
     */
    List<MenuNode> getUCenterMenuList();

    /**
     * 删除掉权限
     * @param permissionList
     */
    void deleteRolePermissionByPermission(List<Permission> permissionList);

    enum PermissionType {
        MENU("menu"), TAB("tab"), OPTION("option"), MODULE("module");
        PermissionType(String type){
            this.type = type;
        }
        public final String type;
        public String getType() {
            return this.type;
        }
    }

    @Data
    class MenuNode implements Serializable {
        private String name;
        private String path;
        private String component;
        private String isLink = "";
        private Integer menuSort;
        private Meta meta;
        private List<MenuNode> children;

        public MenuNode(String name, String path, String component, String isLink, Integer menuSort,
                        String title, String icon, Boolean isHide, Boolean isKeepAlive, Boolean isAffix, Boolean isIframe, List<String> auth, List<MenuNode> children) {
            this.name = name;
            this.path = path;
            this.component = component;
            this.isLink = isLink;
            this.menuSort = menuSort;
            Meta meta = new Meta(title, icon, isHide, isKeepAlive, isAffix, isIframe, auth);
            this.meta = meta;
            this.children = children;
        }

        @Data
        @AllArgsConstructor
        public static class Meta implements Serializable {
            private String title;
            private String icon;
            private Boolean isHide = false;
            private Boolean isKeepAlive = true;
            private Boolean isAffix = false;
            private Boolean isIframe = false;
            private List<String> auth;
        }
    }

}
