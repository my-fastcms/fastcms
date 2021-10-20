package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_PUBLISH = "publish";
    public static final String STATUS_DRAFT = "draft";
    public static final String STATUS_AUDIT = "audit";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章详情不能为空")
    private String contentHtml;

    @NotBlank(message = "文章摘要不能为空")
    private String summary;

    private String seoKeywords;

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
    private Integer commentEnable = 0;

    /**
     * 文章缩略图
     */
    @NotBlank(message = "缩略图不能为空")
    private String thumbnail;

    private String status;

    private String suffix;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @Version
    private Integer version;

    @TableField(exist = false)
    Long[] articleCategory;

    @TableField(exist = false)
    String[] articleTag;

    @TableField(exist = false)
    String url;

    @TableField(exist = false)
    private String thumbUrl;

    public String getUrl() {
        return StringUtils.isBlank(getOutLink()) ? "/a/" + getId() : getOutLink();
    }

    public String getThumbUrl() {
        return "/static/" + getThumbnail();
    }
}
