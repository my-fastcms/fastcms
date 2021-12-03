package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.utils.ConfigUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 附件
 * @author wjun_java@163.com
 * @since 2021-11-12
 */
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上传人id
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
     * 文件相对路径
     */
    private String filePath;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updated;

    /**
     * 文件大小
     */
    @TableField(exist = false)
    private String fileSize;

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
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getPath() {
        return ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN) + getFilePath();
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + id +
            ", userId=" + userId +
            ", fileName=" + fileName +
            ", fileDesc=" + fileDesc +
            ", filePath=" + filePath +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }
}
