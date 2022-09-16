package com.fastcms.common.model;

import java.io.Serializable;

/**
 * wjun_java@163.com
 * 对应element ui router
 */
public class RouterNode extends TreeNode implements Serializable {

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
     * 元数据
     */
    private Meta meta;

    public RouterNode(Long id, Long parentId, String name, String path, String component, Boolean isLink, Integer sortNum,
                          String title, String icon, Boolean isHide, Boolean isKeepAlive, Boolean isAffix, Boolean isIframe) {
        super(id, parentId);
        this.name = name;
        this.path = path;
        this.component = component;
        this.isLink = isLink;
        this.sortNum = sortNum;
        Meta meta = new Meta(title, icon, isHide, isKeepAlive, isAffix, isIframe);
        this.meta = meta;
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

        public Meta(String title, String icon, Boolean isHide, Boolean isKeepAlive, Boolean isAffix, Boolean isIframe) {
            this.title = title;
            this.icon = icon;
            this.isHide = isHide;
            this.isKeepAlive = isKeepAlive;
            this.isAffix = isAffix;
            this.isIframe = isIframe;
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

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
