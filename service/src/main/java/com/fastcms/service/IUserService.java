package com.fastcms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户服务类
 * </p>
 *
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

    /**
     * 根据手机号或用户姓名查询用户
     * @param keyword
     * @param userId
     * @return
     */
    User getUserBySearch(Long userId, String keyword);

    void saveGroupUser(AddGroupUserParam addGroupUserParam) throws Exception;

    @Data
    class AddGroupUserParam implements Serializable {
        @NotBlank(message = "手机号码不能为空")
        private String phone;
        @NotBlank(message = "姓名不能为空")
        private String userName;
        @NotNull(message = "直属上级不能为空")
        private Long parentId;
        @NotNull(message = "职位不能为空")
        private Long tagId;
    }

}
