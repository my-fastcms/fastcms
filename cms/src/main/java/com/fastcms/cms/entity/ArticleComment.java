package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_NORMAL = "public"; //发布
    public static final String STATUS_UNAUDITED = "unaudited"; //待审核
    public static final String STATUS_HIDDEN = "hidden"; //隐藏

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    private Long parentId;

    private Long articleId;

    @NotBlank(message = "评论内容不能为空")
    private String content;

    private Integer sortNum;

    private Integer replyCount;

    private Integer upCount;

    private Integer downCount;

    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;


}
