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
 * 附件
 * @author wjun_java@163.com
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上传者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 文件描述
     */
    private String fileDesc;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    @TableField(exist = false)
    private String fileSize;

    /**
     * @ignore
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * @ignore
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    public String getPath() {
        return "/static/" + getFilePath();
    }

}
