package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表
 * @author wjun_java@163.com
 * @since 2021-11-12
 */
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父节点id
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
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否超链接
     */
    private Boolean isLink;

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
     * 排序
     */
    private Integer sortNum;

    /**
     * 类别
     */
    private String category;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updated;

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
    public Boolean getIsLink() {
        return isLink;
    }

    public void setIsLink(Boolean isLink) {
        this.isLink = isLink;
    }
    public Boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }
    public Boolean getIsKeepAlive() {
        return isKeepAlive;
    }

    public void setIsKeepAlive(Boolean isKeepAlive) {
        this.isKeepAlive = isKeepAlive;
    }
    public Boolean getIsAffix() {
        return isAffix;
    }

    public void setIsAffix(Boolean isAffix) {
        this.isAffix = isAffix;
    }
    public Boolean getIsIframe() {
        return isIframe;
    }

    public void setIsIframe(Boolean isIframe) {
        this.isIframe = isIframe;
    }
    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Permission{" +
            "id=" + id +
            ", parentId=" + parentId +
            ", name=" + name +
            ", path=" + path +
            ", component=" + component +
            ", title=" + title +
            ", icon=" + icon +
            ", isLink=" + isLink +
            ", isHide=" + isHide +
            ", isKeepAlive=" + isKeepAlive +
            ", isAffix=" + isAffix +
            ", isIframe=" + isIframe +
            ", sortNum=" + sortNum +
            ", category=" + category +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }
}
