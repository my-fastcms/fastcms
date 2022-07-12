package com.fastcms.service;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 用户服务类
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface IUserService extends IService<User> {

    /**
     * 修改用户密码
     * @param updatePasswordParam
     */
    void updateUserPassword(UpdatePasswordParam updatePasswordParam) throws FastcmsException;

    /**
     * 获取用户openid
     * @param userId
     * @param type
     * @return
     */
    String getUserOpenId(Long userId, String type);

    /**
     * 获取用户标签集合
     * @param userId
     * @return
     */
    List<UserTag> getUserTagList(Long userId);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    Long saveUser(User user) throws FastcmsException;

    /**
     * 保存微信小程序授权用户信息
     * @param userInfo
     * @return
     * @throws FastcmsException
     */
    User saveWxMaUserInfo(WxMaUserInfo userInfo) throws FastcmsException;

    /**
     * 用户通过手机号授权登录后保存用户信息
     * @param openid
     * @param unionId
     * @param phone
     * @param type
     * @return
     * @throws FastcmsException
     */
    User saveUser(String openid, String unionId, String phone, String type) throws FastcmsException;

    /**
     * 删除用户
     * @param userId
     * @return
     */
    Boolean deleteUser(Long userId) throws FastcmsException;

    /**
     * 用户注册
     * @param username
     * @param password
     * @param repeatPassword
     * @return
     */
    Boolean register(String username, String password, String repeatPassword) throws FastcmsException;

    /**
     * 获取用户最大id值
     * 用来给小程序或者app授权用户自动生成账号
     * @return
     */
    String getLastUserNum();

    /**
     * 设置用户类型
     * @param userId
     * @param userType
     */
    void changeUserTyp(Long userId, Integer userType) throws FastcmsException;

    /**
     * 修改用户信息
     */
    class UpdateUserParam implements Serializable {
        /**
         * 昵称
         */
        String nickName;

        /**
         * 用户姓名
         */
        String realName;
        /**
         * 邮箱
         */
        String email;
        /**
         * 手机号
         */
        String mobile;
        /**
         * 性别
         */
        Integer sex;
        /**
         * 个性签名
         */
        String autograph;

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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }
    }

    /**
     * 修改用户密码
     */
    class UpdatePasswordParam implements Serializable {
        /**
         * 用户id
         */
        Long id;
        /**
         * 新密码
         */
        @NotBlank(message = "密码不能为空")
        String password;
        /**
         * 旧密码
         */
        @NotBlank(message = "确认密码不能为空")
        String confirmPassword;
        /**
         * 手机验证码
         */
        String phoneCode;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public String getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
        }
    }

}
