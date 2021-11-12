package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 单页
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
public class SinglePage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_PUBLISH = "publish";
    public static final String STATUS_DRAFT = "draft";

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    /**
     * 标题
     */
    @NotBlank(message = "页面标题不能为空")
    private String title;

    @NotBlank(message = "页面path不能为空")
    private String path;

    /**
     * 内容
     */
    @NotBlank(message = "页面内容不能为空")
    private String contentHtml;

    /**
     * 链接
     */
    private String outLink;

    /**
     * SEO关键字
     */
    private String seoKeywords;

    /**
     * SEO描述信息
     */
    private String seoDescription;

    /**
     * 摘要
     */
    @NotBlank(message = "页面摘要不能为空")
    private String summary;

    /**
     * 缩略图
     */
    @NotBlank(message = "页面缩略图不能为空")
    private String thumbnail;

    /**
     * 样式
     */
    private String style;

    /**
     * 状态
     */
    private String status;

    /**
     * 页面后缀
     */
    private String suffix;

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 是否开启评论
     */
    private Integer commentEnable = 0;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 最后更新日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @TableField(exist = false)
    private String url;

    @TableField(exist = false)
    private String thumbUrl;

    public String getUrl() {
        return StringUtils.isBlank(getOutLink()) ? "/p/" + getId() : getOutLink();
    }

    public String getThumbUrl() {
        return "/static/" + getThumbnail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentEnable() {
        return commentEnable;
    }

    public void setCommentEnable(Integer commentEnable) {
        this.commentEnable = commentEnable;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
