package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 发票信息
 * @author wjun_java@163.com
 * @since 2021-12-21
 */
public class OrderInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发票类型
     */
    private String type;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 发票内容
     */
    private String content;

    /**
     * 纳税人识别号
     */
    private String identity;

    /**
     * 单位名称
     */
    private String name;

    /**
     * 发票收取人手机号
     */
    private String mobile;

    /**
     * 发票收取人邮箱
     */
    private String email;

    /**
     * 发票状态
     */
    private Integer status;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "OrderInvoice{" +
            "id=" + id +
            ", type=" + type +
            ", title=" + title +
            ", content=" + content +
            ", identity=" + identity +
            ", name=" + name +
            ", mobile=" + mobile +
            ", email=" + email +
            ", status=" + status +
            ", updated=" + updated +
            ", created=" + created +
        "}";
    }
}
