package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * @param username
     */
    void updateUserLoginTime(String username);

    /**
     * 修改用户密码
     * @param userParam
     */
    void updateUserPassword(User userParam) throws Exception;

    /**
     * 根据登录账号获取用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 根据手机号码获取用户
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);

    /**
     * 保存openid的用户
     * @param openid
     * @param type
     */
    User saveUserOfOpenid(String openid, String unionId, String nickName, String avatarUrl, String type) throws Exception;

    /**
     * 根据openid以及phone保存用户信息
     * @param openid
     * @param unionId
     * @param phone
     * @param type
     * @return
     * @throws Exception
     */
    User saveUserOfOpenidAndPhone(String openid, String unionId, String phone, String type) throws Exception;

    /**
     * 获取用户标签集合
     * @param userId
     * @return
     */
    List<UserTag> getUserTagList(Long userId);

}
