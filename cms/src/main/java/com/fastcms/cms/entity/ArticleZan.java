package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2023-05-01
 */
@TableName("article_zan")
public class ArticleZan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @NotNull
    private Long articleId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

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
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ArticleZan{" +
            "id=" + id +
            ", createUserId=" + createUserId +
            ", articleId=" + articleId +
            ", created=" + created +
        "}";
    }
}
