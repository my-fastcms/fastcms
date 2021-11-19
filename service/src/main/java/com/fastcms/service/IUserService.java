package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;

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
     * @param user
     */
    void updateUserPassword(User user) throws Exception;

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
    Boolean saveUser(User user) throws FastcmsException;

    /**
     * 删除用户
     * @param userId
     * @return
     */
    Boolean deleteUser(Long userId);

}
