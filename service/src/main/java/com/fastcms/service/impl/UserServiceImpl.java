package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.aspect.Log;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.User;
import com.fastcms.entity.UserOpenid;
import com.fastcms.entity.UserTag;
import com.fastcms.mapper.UserMapper;
import com.fastcms.service.IUserOpenidService;
import com.fastcms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserOpenidService userOpenidService;

    @Override
    @Log
    public void updateUserLoginTime(String username) {

        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.eq(User::getUserName, username);
        User user = getOne(queryWrapper);
        if(user != null){
            user.setLoginTime(LocalDateTime.now());
            updateById(user);
        }

    }

    @Override
    public void updateUserPassword(User userParam) throws Exception {

        User user = getById(userParam.getId());
        if(user == null) {
            throw new Exception("用户不存在");
        }

        if(StringUtils.isBlank(userParam.getPassword())) {
            throw new Exception("请输入旧密码");
        }

        if(StringUtils.isBlank(userParam.getNewPassword())) {
            throw new Exception("请输入新密码");
        }

        if(StringUtils.isBlank(userParam.getNewConfirmPassword())) {
            throw new Exception("请再次输入新密码");
        }

        if(!userParam.getNewPassword().equals(userParam.getNewConfirmPassword())) {
            throw new Exception("两次新密码输入不一致");
        }

//        final String result = PasswordUtils.getMd5Password(user.getSalt(), userParam.getPassword());
//        if(!result.equals(user.getPassword())) {
//            throw new Exception("旧密码输入错误");
//        }
//
//        final String salt = System.currentTimeMillis() + "";
//        final String newPwd = PasswordUtils.getMd5Password(salt, userParam.getNewPassword());

//        user.setPassword(newPwd);
//        user.setSalt(salt);
        updateById(user);

    }

    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName, username);
        return getOne(queryWrapper);
    }

    @Override
    public User getUserByPhone(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile", phone);
        return getOne(queryWrapper);
    }

    @Override
    @Transactional
    public User saveUserOfOpenid(String openid, String unionId, String nickName, String avatarUrl, String type) throws Exception {
        UserOpenid userOpenid = userOpenidService.getByOpenid(openid, UserOpenid.TYPE_WECHAT_MINI);
        User user;
        if(userOpenid == null) {
            user = new User();
            user.setNickName(nickName);
            user.setHeadImg(avatarUrl);
            user.setSource(User.SourceType.WX_MINI_PROGRAM.name().toLowerCase());
            save(user);
            userOpenid = new UserOpenid();
            userOpenid.setUserId(user.getId());
            userOpenid.setType(type);
            userOpenid.setValue(openid);
            userOpenid.setUnionId(unionId);
            userOpenidService.save(userOpenid);
        }else {
            user = getById(userOpenid.getUserId());
        }
        return user;
    }

    @Override
    public synchronized User saveUserOfOpenidAndPhone(String openid, String unionId, String phone, String type) throws Exception {

        User userByPhone = getUserByPhone(phone);
        if(userByPhone != null) return userByPhone;

        UserOpenid userOpenid = userOpenidService.getByOpenid(openid, UserOpenid.TYPE_WECHAT_MINI);
        User user;
        if(userOpenid == null) {
            user = new User();
            user.setMobile(phone);
            user.setSource(User.SourceType.WX_MINI_PROGRAM.name().toLowerCase());
            save(user);
            userOpenid = new UserOpenid();
            userOpenid.setUserId(user.getId());
            userOpenid.setType(type);
            userOpenid.setValue(openid);
            userOpenid.setUnionId(unionId);
            userOpenidService.save(userOpenid);
        }else {
            user = getById(userOpenid.getUserId());
        }
        return user;
    }

    @Override
    public List<UserTag> getUserTagList(Long userId) {
        return getBaseMapper().getUserTagList(userId);
    }

    @Override
    public User getUserBySearch(Long userId, String keyword) {
        return getUserByPhone(keyword);
    }

    @Override
    @Transactional
    public synchronized void saveGroupUser(AddGroupUserParam addGroupUserParam) throws Exception {

        User parent = getById(addGroupUserParam.getParentId());
        if(parent == null) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "上级用户不存在");
        }

        User user = getUserByPhone(addGroupUserParam.getPhone());
        if(user == null) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "用户不存在");
        }

        if(Objects.equals(parent.getId(), user.getId())) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "自己不能分配给自己");
        }

        user.setUserName(addGroupUserParam.getUserName());
        updateById(user);

        List<Long> stationIds = new ArrayList<>();
        stationIds.add(addGroupUserParam.getTagId());

    }

}
