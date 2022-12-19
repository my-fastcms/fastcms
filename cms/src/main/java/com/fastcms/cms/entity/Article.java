package com.fastcms.cms.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import com.fastcms.cms.utils.ArticleUtils;
import com.fastcms.common.utils.JsoupUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.template.StaticPathHelper;
import com.fastcms.core.utils.AttachUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文章
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
public class Article implements Serializable, StaticPathHelper {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_PUBLISH = "publish";
    public static final String STATUS_DRAFT = "draft";
    public static final String STATUS_AUDIT = "audit";
    public static final String STATUS_DELETE = "delete";

    public enum ArticleStatus {

        PUBLISH(STATUS_PUBLISH, "发布"),
        DRAFT(STATUS_DRAFT, "草稿"),
        AUDIT(STATUS_AUDIT, "审核"),
        DELETE(STATUS_DELETE, "删除");

        ArticleStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private final String key;
        private final String value;

        public static String getValue(String key) {
            for (ArticleStatus s: values()) {
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
     * 创建用户
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 文章标题
     */
    @NotBlank(message = "{fastcms.cms.article.comment.title.is.not.allow.null}")
    private String title;

    /**
     * 文章详情
     */
    @NotBlank(message = "{fastcms.cms.article.comment.content.is.not.allow.null}")
    private String contentHtml;

    /**
     * 文章摘要
     */
    @NotBlank(message = "{fastcms.cms.article.comment.summary.is.not.allow.null}")
    private String summary;

    /**
     * seo关键字
     */
    private String seoKeywords;

    /**
     * seo描述
     */
    private String seoDescription;

    /**
     * 文章外链
     */
    private String outLink;

    /**
     * 文章排序，值越大越靠前
     */
    private Integer sortNum;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 是否开启评论
     */
    private Boolean commentEnable = true;

    /**
     * 文章缩略图
     */
    @NotBlank(message = "{fastcms.cms.article.comment.thumbnail.is.not.allow.null}")
    private String thumbnail;

    /**
     * 状态
     */
    private String status;

    /**
     * 前缀
     */
    private String suffix;

    /**
     * 附件
     */
    private Long attachId;

    /**
     * 附件名称
     */
    @TableField(exist = false)
    private String attachTitle;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @Version
    private Integer version;

    /**
     * json格式扩展字段
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String jsonExt;

    /**
     * 分类id集合
     */
    @TableField(exist = false)
    Set<Long> articleCategory;

    /**
     * 标签名称集合
     */
    @TableField(exist = false)
    List<String> articleTag;

    /**
     * 搜索高亮标题
     */
    @TableField(exist = false)
    private String highlightTitle;

    /**
     * 搜索高亮内容
     */
    @TableField(exist = false)
    private String highlightContent;

    /**
     * 价格服务
     */
    @TableField(exist = false)
    private String priceClass;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getCommentEnable() {
        return commentEnable;
    }

    public void setCommentEnable(Boolean commentEnable) {
        this.commentEnable = commentEnable;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusStr() {
        return ArticleStatus.getValue(getStatus());
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getAttachId() {
        return attachId;
    }

    public void setAttachId(Long attachId) {
        this.attachId = attachId;
    }

    public String getAttachTitle() {
        return attachTitle;
    }

    public void setAttachTitle(String attachTitle) {
        this.attachTitle = attachTitle;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getJsonExt() {
        return jsonExt;
    }

    public void setJsonExt(String jsonExt) {
        this.jsonExt = jsonExt;
    }

    public Set<Long> getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(Set<Long> articleCategory) {
        this.articleCategory = articleCategory;
    }

    public List<String> getArticleTag() {
        return articleTag;
    }

    public void setArticleTag(List<String> articleTag) {
        this.articleTag = articleTag;
    }

    @Override
    public String getUrl() {
        return ArticleUtils.getUrl(this);
    }

    public String getThumbnailUrl() {
        return AttachUtils.getAttachFileDomain() == null ? getThumbnail() : AttachUtils.getAttachFileDomain().concat(getThumbnail() == null ? "" : getThumbnail());
    }

    public String getHighlightTitle() {
        return highlightTitle;
    }

    public void setHighlightTitle(String highlightTitle) {
        this.highlightTitle = highlightTitle;
    }

    public String getHighlightContent() {
        return highlightContent;
    }

    public void setHighlightContent(String highlightContent) {
        this.highlightContent = highlightContent;
    }

    public Map<String, Object> getExtFields() {
        if(StrUtils.isNotBlank(getJsonExt())) {
            try {
                return JSON.toJavaObject(JSON.parseObject(getJsonExt()), Map.class);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @JsonIgnore
    public String getText() {
        return JsoupUtils.getText(getContent());
    }

    @JsonIgnore
    public String getContent() {
        return JsoupUtils.clean(getContentHtml());
    }

    public String getPriceClass() {
        return priceClass;
    }

    public void setPriceClass(String priceClass) {
        this.priceClass = priceClass;
    }
}
