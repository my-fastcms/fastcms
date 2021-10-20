package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CATEGORY_TYPE = "category";
    public static final String TAG_TYPE = "tag";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    private Long parentId;

    private String title;

    private String type = CATEGORY_TYPE;

    private Integer sortNum;

    private String suffix;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @TableField(exist = false)
    private Boolean isCheck;

    @TableField(exist = false)
    private String url;

    @TableField(exist = false)
    List<ArticleCategory> children;

    public String getUrl() {
        return "/a/c/" + getId();
    }
}
