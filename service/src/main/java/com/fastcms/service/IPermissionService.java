package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Permission;

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
     * 获取系统权限列表
     * @return
     */
    List<PermissionNode> getPermissions();

    /**
     * 获取角色对应的权限
     * @param roleId
     * @return
     */
    List<PermissionNode> getPermissionByRoleId(Long roleId);

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
    List<PermissionNode> getUserMenuPermission(Long userId);

    /**
     * 删除掉权限
     * @param permissionList
     */
    void deleteRolePermissionByPermission(List<Permission> permissionList);

    class PermissionNode implements Serializable {
        private Long id;
        private Long parentId;
        private String name;
        private String path;
        private String component;
        private Boolean isLink;
        private Integer menuSort;
        private Meta meta;
        private List<PermissionNode> children;

        public PermissionNode(Long id, Long parentId, String name, String path, String component, Boolean isLink, Integer menuSort,
                        String title, String icon, Boolean isHide, Boolean isKeepAlive, Boolean isAffix, Boolean isIframe, List<String> auth, List<PermissionNode> children) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
            this.path = path;
            this.component = component;
            this.isLink = isLink;
            this.menuSort = menuSort;
            Meta meta = new Meta(title, icon, isHide, isKeepAlive, isAffix, isIframe, auth);
            this.meta = meta;
            this.children = children;
        }

        public static class Meta implements Serializable {
            private String title;
            private String icon;
            private Boolean isHide;
            private Boolean isKeepAlive;
            private Boolean isAffix;
            private Boolean isIframe;
            private List<String> auth;

            public Meta(String title, String icon, Boolean isHide, Boolean isKeepAlive, Boolean isAffix, Boolean isIframe, List<String> auth) {
                this.title = title;
                this.icon = icon;
                this.isHide = isHide;
                this.isKeepAlive = isKeepAlive;
                this.isAffix = isAffix;
                this.isIframe = isIframe;
                this.auth = auth;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public Boolean getHide() {
                return isHide;
            }

            public void setHide(Boolean hide) {
                isHide = hide;
            }

            public Boolean getKeepAlive() {
                return isKeepAlive;
            }

            public void setKeepAlive(Boolean keepAlive) {
                isKeepAlive = keepAlive;
            }

            public Boolean getAffix() {
                return isAffix;
            }

            public void setAffix(Boolean affix) {
                isAffix = affix;
            }

            public Boolean getIframe() {
                return isIframe;
            }

            public void setIframe(Boolean iframe) {
                isIframe = iframe;
            }

            public List<String> getAuth() {
                return auth;
            }

            public void setAuth(List<String> auth) {
                this.auth = auth;
            }
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public Boolean getLink() {
            return isLink;
        }

        public void setLink(Boolean link) {
            isLink = link;
        }

        public Integer getMenuSort() {
            return menuSort;
        }

        public void setMenuSort(Integer menuSort) {
            this.menuSort = menuSort;
        }

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public List<PermissionNode> getChildren() {
            return children;
        }

        public void setChildren(List<PermissionNode> children) {
            this.children = children;
        }
    }

    class RolePermission extends Permission implements Serializable {
        private Long roleId;

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }
    }

}
