package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fastcms.cms.utils.ArticleUtils;
import com.fastcms.core.template.StaticPathHelper;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章标签
 * @author wjun_java@163.com
 * @since 2021-12-16
 */
public class ArticleTag implements Serializable, StaticPathHelper {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    private String tagName;

    private String type;

    /**
     * 标签图标
     */
    private String icon;

    /**
     * 访问标识，模板名称，结合网站模板使用
     */
    private String suffix;

    private Integer sortNum;

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
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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
        return "ArticleTag{" +
            "id=" + id +
            ", createUserId=" + createUserId +
            ", tagName=" + tagName +
            ", type=" + type +
            ", sortNum=" + sortNum +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }

    @Override
    public String getUrl() {
        return ArticleUtils.getArticleTagUrl(this);
    }

}
