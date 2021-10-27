package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fastcms.common.constants.FastcmsConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "登录账号不能为空")
    private String userName;

    private String nickName;

    private String email;

    private String headImg;

    private String mobile;

    private String address;

    private String company;

    private String source;

    private String password;

    private String salt;

    /**
     * 0禁用1正常
     */
    private Integer status;

    /**
     * 最近登录时间
     */
    private LocalDateTime loginTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @Version
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

}
