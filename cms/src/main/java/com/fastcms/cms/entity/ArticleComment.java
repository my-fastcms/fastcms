package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章评论
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_NORMAL = "public"; //发布
    public static final String STATUS_UNAUDITED = "unaudited"; //待审核
    public static final String STATUS_HIDDEN = "hidden"; //隐藏

    public enum ArticleCommentStatus {

        NORMAL(STATUS_NORMAL, "发布"),
        UNAUDITED(STATUS_UNAUDITED, "待审核"),
        HIDDEN(STATUS_HIDDEN, "隐藏");

        ArticleCommentStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private final String key;
        private final String value;

        public static String getValue(String key) {
            for (ArticleCommentStatus s: values()) {
                if (s.key.equals(key)) {
                    return s.value;
                }
            }
            return "";
        }

    }

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 上级评论
     */
    private Long parentId;

    /**
     * 评论文章
     */
    private Long articleId;

    /**
     * 评论内容
     */
    @NotBlank(message = "{fastcms.cms.article.comment.content.is.not.allow.null}")
    private String content;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 回复次数
     */
    private Integer replyCount;

    /**
     * 点赞次数
     */
    private Integer upCount;

    /**
     * 踩赞次数
     */
    private Integer downCount;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 修改时间
     */
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getUpCount() {
        return upCount;
    }

    public void setUpCount(Integer upCount) {
        this.upCount = upCount;
    }

    public Integer getDownCount() {
        return downCount;
    }

    public void setDownCount(Integer downCount) {
        this.downCount = downCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusStr() {
        return ArticleCommentStatus.getValue(getStatus());
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
}
