package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fastcms.language.Language;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 接口资源表
 * @author wjun_java@163.com
 * @since 2022-05-01
 */
public class Resource implements Serializable, Language {

    private static final long serialVersionUID = 1L;

    public Resource() {

    }

    public Resource(String resourceName, String resourcePath, String actionType) {
        this.resourceName = resourceName;
        this.resourcePath = resourcePath;
        this.actionType = actionType;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源路径
     */
    private String resourcePath;

    /**
     * 读写类型
     */
    private String actionType;

    /**
     * 语言
     */
    private String language;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
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

    public String getLanguage() {
        return language == null ? getLang() : language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + id +
            ", resourceName=" + resourceName +
            ", resourcePath=" + resourcePath +
            ", language=" + language +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(resourcePath, resource.resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourcePath);
    }
}
