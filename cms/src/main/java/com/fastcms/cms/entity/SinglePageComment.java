package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 页面评论
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
public class SinglePageComment implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_NORMAL = "public"; //发布
    public static final String STATUS_UNAUDITED = "unaudited"; //待审核
    public static final String STATUS_HIDDEN = "hidden"; //隐藏

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 回复的评论ID
     */
    private Long parentId;

    /**
     * 评论的内容ID
     */
    private Long pageId;

    /**
     * 评论的用户ID
     */
    private Long userId;

    /**
     * 评论的内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String content;

    /**
     * 评论的回复数量
     */
    private Integer replyCount;

    /**
     * 排序编号，常用语置顶等
     */
    private Integer sortNum;

    /**
     * 评论的状态
     */
    private String status;

    /**
     * 评论的时间
     */
    private LocalDateTime created;

    /**
     * 修改时间
     */
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

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
