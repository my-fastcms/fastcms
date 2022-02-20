package com.fastcms.service;

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
     * 通过登录账号更新用户登录时间
     * @param userId
     */
    void updateUserLoginTime(Long userId);

    /**
     * 修改用户密码
     * @param updatePasswordParam
     */
    void updateUserPassword(UpdatePasswordParam updatePasswordParam) throws FastcmsException;

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
     * 修改用户信息
     */
    class UpdateUserParam implements Serializable {
        /**
         * 昵称
         */
        @NotBlank(message = "用户昵称不能为空")
        String nickName;
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
        @NotBlank(message = "个性签名不能为空")
        String autograph;

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
        String password;
        /**
         * 旧密码
         */
        String confirmPassword;

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
    }

}
