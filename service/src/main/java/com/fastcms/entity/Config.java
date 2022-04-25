package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * 系统配置
 * @author wjun_java@163.com
 * @since 2021-11-12
 */
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配置key键值
     */
    @TableField("`key`")
    private String key;

    /**
     * 配置值
     */
    @TableField("`value`")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getJsonValue() {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value) ? Boolean.valueOf(value) : value;
    }

    @Override
    public String toString() {
        return "Config{" +
            "id=" + id +
            ", key=" + key +
            ", value=" + value +
        "}";
    }
}
