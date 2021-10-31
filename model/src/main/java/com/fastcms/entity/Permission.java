package com.fastcms.entity;

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
 *  权限表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CATEGORY_ADMIN = "admin";
    public static final String CATEGORY_CENTER = "ucenter";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long parentId;

    @NotBlank(message = "路由名称不能为空")
    private String name;

    @NotBlank(message = "路由地址不能为空")
    private String path;

    @NotBlank(message = "VUE组件地址不能为空")
    private String component;

    @NotBlank(message = "菜单名称不能为空")
    @TableField("meta_title")
    private String title;

    @NotBlank(message = "菜单图标不能为空")
    @TableField("meta_icon")
    private String icon;

    private Boolean isLink;

    private Boolean isHide;

    private Boolean isKeepAlive;

    private Boolean isAffix;

    private Boolean isIframe;

    private int sortNum;

    private String category;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

}
