package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2024-12-24
 */
@TableName("user_server_openid")
public class UserServerOpenid implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String clientId;

    private String openid;

    /**
     * fastcms用户唯一标志
     */
    private String sub;

    private LocalDateTime created;

    private LocalDateTime updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
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
        return "UserServerOpenid{" +
            "id=" + id +
            ", clientId=" + clientId +
            ", openid=" + openid +
            ", sub=" + sub +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }
}
