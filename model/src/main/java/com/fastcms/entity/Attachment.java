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
 *  附件表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    /**
     * 原始文件名
     */
    private String fileName;

    private String fileDesc;

    private String filePath;

    @TableField(exist = false)
    private String fileSize;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    public String getPath() {
        return "/static/" + getFilePath();
    }

}
