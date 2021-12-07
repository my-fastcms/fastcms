package com.fastcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * wjun_java@163.com
 * 对应element ui router
 */
public class RouterNode implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 上级id
     */
    private Long parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 组件
     */
    private String component;
    /**
     * 是否链接
     */
    private Boolean isLink;
    /**
     * 排序
     */
    private Integer menuSort;
    /**
     * 元数据
     */
    private Meta meta;
    /**
     * 子节点集合
     */
    private List<RouterNode> children;

    public RouterNode(Long id, Long parentId, String name, String path, String component, Boolean isLink, Integer menuSort,
                          String title, String icon, Boolean isHide, Boolean isKeepAlive, Boolean isAffix, Boolean isIframe, List<String> auth, List<RouterNode> children) {
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
        /**
         * 标题
         */
        private String title;
        /**
         * 图标
         */
        private String icon;
        /**
         * 是否隐藏
         */
        private Boolean isHide;
        /**
         * 是否保持
         */
        private Boolean isKeepAlive;
        /**
         * 是否固定
         */
        private Boolean isAffix;
        /**
         * 是否iframe
         */
        private Boolean isIframe;
        /**
         * 权限集合
         */
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

        public Boolean getIsHide() {
            return isHide;
        }

        public void setIsHide(Boolean hide) {
            isHide = hide;
        }

        public Boolean getIsKeepAlive() {
            return isKeepAlive;
        }

        public void setIsKeepAlive(Boolean keepAlive) {
            isKeepAlive = keepAlive;
        }

        public Boolean getIsAffix() {
            return isAffix;
        }

        public void setIsAffix(Boolean affix) {
            isAffix = affix;
        }

        public Boolean getIsIframe() {
            return isIframe;
        }

        public void setIsIframe(Boolean iframe) {
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

    public List<RouterNode> getChildren() {
        return children;
    }

    public void setChildren(List<RouterNode> children) {
        this.children = children;
    }

}
