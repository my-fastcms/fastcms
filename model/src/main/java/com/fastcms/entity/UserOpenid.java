package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 账号绑定信息表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserOpenid implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TYPE_WECHAT = "wechat";
    public static final String TYPE_WECHAT_MINI = "wechat_mini";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    /**
     * 第三方类型：wechat，dingding，qq...
     */
    private String type;

    /**
     * 第三方的openId的值
     */
    private String value;

    /**
     * 第三方的unionId值
     */
    private String unionId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @Version
    private Integer version;

}
