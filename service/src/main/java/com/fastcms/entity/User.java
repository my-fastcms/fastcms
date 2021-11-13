package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fastcms.common.constants.FastcmsConstants;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 用户
 * @author wjun_java@163.com
 * @since 2021-11-12
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 通信地址
     */
    private String address;

    /**
     * 所在公司
     */
    private String company;

    /**
     * 来源
     */
    private String source;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 0禁用1正常
     */
    private Integer status;

    /**
     * 最近登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 修改时间
     */
    private LocalDateTime updated;

    private Integer version;

    @TableField(exist = false)
    private String newPassword;
    @TableField(exist = false)
    private String newConfirmPassword;
    @TableField(exist = false)
    private String sourceStr;
    @TableField(exist = false)
    private List<Role> roleList;

    public String getSourceStr() {
        String text = SourceType.getValue(getSource());
        return StringUtils.isBlank(text) ? "" : text;
    }

    public String getAvatar() {
        if(StringUtils.isBlank(getHeadImg())) return FastcmsConstants.STATIC_RESOURCE_PATH + "/img/default.jpg";
        return getHeadImg().startsWith("http") ? getHeadImg() : FastcmsConstants.STATIC_RESOURCE_PATH + getHeadImg();
    }

    public enum SourceType {

        //来自微信网页授权
        WX_WEB("wx_web_auth", "微信网页授权"),
        //来自扫描微信公众号二维码
        WX_QRCODE("wx_qrcode", "扫描公众号二维码"),
        //来自微信小程序
        WX_MINI_PROGRAM("wx_mini_program", "微信小程序"),
        //来自小程序二维码
        WX_MINI_PROGRAM_QRCODE("wx_mini_program_qrcode", "扫描小程序二维码"),
        //来自网页注册
        WEB_REGISTER("web_register", "网页注册"),
        //来自管理员的后台创建
        ADMIN_CREATE("admin_create", "后台创建");

        SourceType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private final String key;
        private final String value;

        public static String getValue(String key) {
            for (SourceType s: values()) {
                if (s.key.equals(key)) {
                    return s.value;
                }
            }
            return "";
        }

    }

    public boolean isAdmin() {
        if(roleList != null) {
            for (Role role : roleList) {
                if(Objects.equals(role.getId(), FastcmsConstants.ADMIN_ROLE_ID))
                    return true;
            }
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
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
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", userName=" + userName +
            ", nickName=" + nickName +
            ", email=" + email +
            ", headImg=" + headImg +
            ", mobile=" + mobile +
            ", address=" + address +
            ", company=" + company +
            ", source=" + source +
            ", password=" + password +
            ", salt=" + salt +
            ", status=" + status +
            ", loginTime=" + loginTime +
            ", created=" + created +
            ", updated=" + updated +
            ", version=" + version +
        "}";
    }
}
