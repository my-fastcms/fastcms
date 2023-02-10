/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fastcms.common.constants.FastcmsConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户
 * @author wjun_java@163.com
 * @since 2021-11-12
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer SEX_MAN = 1;
    public static final Integer SEX_WOMAN = 0;

    public static final Integer USER_TYPE_SYS = 1;
    public static final Integer USER_TYPE_CLIENT = 2;

    public enum UserType {

        TYPE_SYS(USER_TYPE_SYS, "系统用户"),
        TYPE_CLIENT(USER_TYPE_CLIENT, "网站用户");

        UserType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private final Integer key;
        private final String value;

        public static String getValue(Integer key) {
            for (UserType s: values()) {
                if (s.key == key) {
                    return s.value;
                }
            }
            return "";
        }

    }

    public enum SourceType {

        WX_WEB("wx_web_auth", "微信网页授权"),
        WX_QRCODE("wx_qrcode", "扫描公众号二维码"),
        WX_MINI_PROGRAM("wx_mini_program", "微信小程序"),
        WX_MINI_PROGRAM_QRCODE("wx_mini_program_qrcode", "扫描小程序二维码"),
        WEB_REGISTER("web_register", "网页注册"),
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

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录账号
     */
    @NotBlank(message = "{fastcms.user.account.notnull}")
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

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
     * 个性签名
     */
    private String autograph;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 来源
     */
    private String source;

    /**
     * 登录密码
     */
    @JsonIgnore
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
     * 登录ip
     */
    private String accessIp;

    /**
     * 用户类型
     */
    private Integer userType;

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
     * @ignore
     */
    @Version
    private Integer version;

    /**
     * 标签名称集合
     */
    @TableField(exist = false)
    List<String> tagList;

    /**
     * 已分配角色列表
     */
    @TableField(exist = false)
    private List<Long> roleList;

    /**
     * 已分配的部门列表
     */
    @TableField(exist = false)
    private List<Long> deptList;

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
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
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

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public List<Long> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Long> roleList) {
        this.roleList = roleList;
    }

    public List<Long> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Long> deptList) {
        this.deptList = deptList;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getSourceStr() {
        String text = SourceType.getValue(getSource());
        return StringUtils.isBlank(text) ? "" : text;
    }

    public String getStatusStr() {
        return getStatus() != null && getStatus() == FastcmsConstants.STATUS_NORMAL ? "启用" : "禁用";
    }

    public String getAvatar() {
        if(StringUtils.isBlank(getHeadImg())) return "/img/default.jpg";
        return getHeadImg().startsWith("http") ? getHeadImg() : getHeadImg();
    }

    public String getUserTypeStr() {
        return UserType.getValue(getUserType());
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
