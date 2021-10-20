package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *  配置表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    public Config(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("`key`")
    private String key;

    @TableField("`value`")
    private String value;


}
