package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限表
 * </p>
 *
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

    private String name;

    private String path;

    private String component;

    private String title;

    private String icon;

    private Boolean isLink;

    private Boolean isHide;

    private Boolean isKeepAlive;

    private Boolean isAffix;

    private Boolean isIframe;

    private Integer sortNum;

    private String category;

    private LocalDateTime created;

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
