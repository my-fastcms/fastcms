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
 * 用户岗位表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 岗位名称
     */
    private String stationName;

    private String stationDesc;

    /**
     * 是否是管理岗位
     */
    private Boolean withManager = false;

    /**
     * 0删除，1启用
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updated;

    public String getStatusStr() {
        return getStatus() == 0 ? "已删除" : "正常使用";
    }


}
