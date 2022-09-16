package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.utils.ConfigUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 附件
 * @author wjun_java@163.com
 * @since 2021-11-12
 */
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ATTACH_FILE_DOMAIN = "file_domain";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上传人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

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
     * 文件类型
     */
    private String fileType;

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
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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

    public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取文件网络全路径
     * @return
     */
    public String getPath() {
        return getFileDomain() + getFilePath();
    }

    /**
     * 根据文件类型获取显示路径
     * @return
     */
    public String getTypePath() {
        if(!TYPE_IMAGE.equals(getFileType())) {
            return "/default.jpg";
        }
        return getFileDomain() + getFilePath();
    }

    /**
     * 图片
     */
    public static final String TYPE_IMAGE = "image";
    /**
     * 音频
     */
    public static final String TYPE_AUDIO = "audio";
    /**
     * 视频
     */
    public static final String TYPE_VIDEO = "video";
    /**
     * 压缩文件
     */
    public static final String TYPE_ZIP = "zip";
    /**
     * 办公文件
     */
    public static final String TYPE_OFFICE = "office";

    /**
     * 附件类型
     */
    public enum AttachType {

        IMAGE(".jpg, .png, .gif, .jpeg, .webp", TYPE_IMAGE),
        AUDIO(".mp3, .aac, .ape, .flac, .alac", TYPE_AUDIO),
        VIDEO(".avi, .wmv, .mpeg, .rmvb, .mp4, .mov, .m4v, .flv", TYPE_VIDEO),
        ZIP(".zip, .rar, .gzip", TYPE_ZIP),
        OFFICE(".doc, .docx, .xls, .xlsx, .ppt", TYPE_OFFICE);

        AttachType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private final String key;
        private final String value;

        public static String getValue(String key) {
            for (Attachment.AttachType s: values()) {
                if (s.key.contains(key)) {
                    return s.value;
                }
            }
            return "";
        }

    }

    String getFileDomain() {
        String fileDomain = ConfigUtils.getConfig(ATTACH_FILE_DOMAIN);
        if(StrUtils.isBlank(fileDomain)) {
            fileDomain = ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN);
        }
        return StringUtils.isBlank(fileDomain) ? "" : fileDomain;
    }

    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + id +
            ", createUserId=" + createUserId +
            ", fileName=" + fileName +
            ", fileDesc=" + fileDesc +
            ", filePath=" + filePath +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }

}
