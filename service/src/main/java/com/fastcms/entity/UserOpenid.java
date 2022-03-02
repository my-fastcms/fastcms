package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号绑定信息表
 * @author wjun_java@163.com
 * @since 2021-11-12
 */
public class UserOpenid implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TYPE_WECHAT_MP = "wechat_mp";
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

    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updated;

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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    @Override
    public String toString() {
        return "UserOpenid{" +
            "id=" + id +
            ", userId=" + userId +
            ", type=" + type +
            ", value=" + value +
            ", version=" + version +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }
}
