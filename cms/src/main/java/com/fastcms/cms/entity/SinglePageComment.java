package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 页面评论表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
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

    private LocalDateTime updated;


}
