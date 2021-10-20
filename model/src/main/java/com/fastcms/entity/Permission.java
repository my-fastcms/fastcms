package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    public static final String CATEGORY_CENTER = "center";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long parentId;

    private String name;

    private String url;

    private String permInfo;

    private String icon;

    private String type;

    private String moduleId;

    private Integer sortNum;

    private String className;

    private String category;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;


}
