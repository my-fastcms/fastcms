package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 网站菜单表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_SHOW = "show";
    public static final String STATUS_HIDDEN = "hidden";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    @NotBlank(message = "菜单URL不能为空")
    private String menuUrl;

    private String menuIcon;

    private Integer sortNum;

    private String target;

    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;


}
