package com.fastcms.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.User;
import com.fastcms.entity.UserOpenid;
import com.fastcms.entity.UserTag;
import com.fastcms.mapper.DepartmentMapper;
import com.fastcms.mapper.RoleMapper;
import com.fastcms.mapper.UserMapper;
import com.fastcms.mapper.UserTagMapper;
import com.fastcms.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserTagService userTagService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserOpenidService userOpenidService;

    @Autowired
    private IDepartmentService departmentService;

    @Override
    public void updateUserPassword(UpdatePasswordParam updatePasswordParam) throws FastcmsException {

        if(StringUtils.isBlank(updatePasswordParam.getPassword())) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "请输入新密码");
        }

        if(updatePasswordParam.getPassword().length()< 6) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "密码长度不能少于6位");
        }

        if(!updatePasswordParam.getPassword().equals(updatePasswordParam.getConfirmPassword())) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "两次密码输入不一致");
        }

        User user = getById(updatePasswordParam.getId());
        if(user == null) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "用户不存在");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordParam.getPassword().trim()));

        updateById(user);
    }

    @Override
    public String getUserOpenId(Long userId, String type) {
        UserOpenid one = userOpenidService.getOne(Wrappers.<UserOpenid>lambdaQuery().eq(UserOpenid::getUserId, userId).eq(UserOpenid::getType, type), false);
        return one == null ? "" : one.getValue();
    }

    @Override
    public List<UserTag> getUserTagList(Long userId) {
        return getBaseMapper().getUserTagList(userId);
    }

    @Override
    @Transactional
    public Long saveUser(User user) throws FastcmsException {

        if(user.getId() != null && Objects.equals(FastcmsConstants.ADMIN_USER_ID, user.getId())) {
            throw new FastcmsException(FastcmsException.NO_RIGHT, "超级管理员不可修改");
        }

        if(user.getId() == null) {
            User one = getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()), false);
            if(one != null) throw new FastcmsException(FastcmsException.SERVER_ERROR, "账号已存在");

            if(StringUtils.isBlank(user.getPassword())) {
                throw new FastcmsException(FastcmsException.SERVER_ERROR, "密码不能为空");
            }
        } else {
            //登录账号不可修改
            user.setUserName(null);
        }

        if(StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        saveOrUpdate(user);
        roleService.saveUserRole(user.getId(), user.getRoleList());
        departmentService.saveUserDepartments(user.getId(), user.getDeptList());
        processTag(user.getId(), user.getTagList());
        return user.getId();
    }

    @Override
    @Transactional
    public User saveWxMaUserInfo(WxMaUserInfo userInfo) throws FastcmsException {

        if(userInfo == null || StringUtils.isBlank(userInfo.getOpenId())) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "小程序用户参数异常");
        }

        UserOpenid userOpenid = userOpenidService.getOne(Wrappers.<UserOpenid>lambdaQuery().eq(UserOpenid::getValue, userInfo.getOpenId()).eq(UserOpenid::getType, UserOpenid.TYPE_WECHAT_MINI));
        User user;
        if(userOpenid == null) {
            user = new User();
            user.setUserName(getLastUserNum());
            user.setNickName(userInfo.getNickName());
            user.setHeadImg(userInfo.getAvatarUrl());
            user.setSource(User.SourceType.WX_MINI_PROGRAM.name().toLowerCase());
            user.setUserType(User.USER_TYPE_CLIENT);
            save(user);
            userOpenid = new UserOpenid();
            userOpenid.setUserId(user.getId());
            userOpenid.setType(UserOpenid.TYPE_WECHAT_MINI);
            userOpenid.setValue(userInfo.getOpenId());
            userOpenidService.save(userOpenid);
        }else {
            user = getById(userOpenid.getUserId());
        }

        return user;
    }

    @Override
    @Transactional
    public User saveUser(String openid, String unionId, String phone, String type) throws FastcmsException {

        if(openid == null || StringUtils.isBlank(phone)) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "小程序用户参数异常");
        }

        User userByPhone = getOne(Wrappers.<User>lambdaQuery().eq(User::getMobile, phone));
        if(userByPhone != null) return userByPhone;

        UserOpenid userOpenid = userOpenidService.getOne(Wrappers.<UserOpenid>lambdaQuery().eq(UserOpenid::getValue, openid).eq(UserOpenid::getType, type));
        User user;
        if(userOpenid == null) {
            user = new User();
            user.setUserName(getLastUserNum());
            user.setMobile(phone);
            user.setSource(User.SourceType.WX_MINI_PROGRAM.name().toLowerCase());
            save(user);
            userOpenid = new UserOpenid();
            userOpenid.setUserId(user.getId());
            userOpenid.setType(type);
            userOpenid.setValue(openid);
            userOpenidService.save(userOpenid);
        }else {
            user = getById(userOpenid.getUserId());
        }
        return user;
    }

    void processTag(Long userId, List<String> processList) {
        List<UserTag> articleTagList = new ArrayList<>();
        if(processList != null && processList.size()>0) {
            //插入新的标签
            for (String tag : processList) {
                if(StringUtils.isNotBlank(tag)) {
                    articleTagList.add(userTagService.getByName(tag));
                }
            }

            if(!articleTagList.isEmpty()) {
                userTagService.saveBatch(articleTagList.stream().filter(item -> item.getId() == null).collect(Collectors.toList()));
            }
        }
        userTagService.saveUserTagRelation(userId, articleTagList.stream().map(UserTag::getId).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public Boolean deleteUser(Long userId) throws FastcmsException {
        if(userId != null && Objects.equals(userId, FastcmsConstants.ADMIN_USER_ID)) {
            throw new FastcmsException(FastcmsException.NO_RIGHT, "超级管理员不可删除");
        }
        ((RoleMapper) roleService.getBaseMapper()).deleteRoleByUserId(userId);
        ((UserTagMapper) userTagService.getBaseMapper()).deleteUserTagRelationByUserId(userId);
        ((DepartmentMapper) departmentService.getBaseMapper()).deleteDepartmentByUserId(userId);
        removeById(userId);
        return true;
    }

    @Override
    public synchronized Boolean register(String username, String password, String repeatPassword) throws FastcmsException {
        if(StrUtils.isBlank(username)) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "请输入完成注册数据");
        }

        final String userName = username.trim();
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(userName);
        if (m.matches()) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "注册账号不能全是数字");
        }

        if (StrUtils.isBlank(password)) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "请输入密码");
        }

        if (StrUtils.isBlank(repeatPassword)) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "请输入确认密码");
        }

        if(!repeatPassword.equals(password)) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "两次密码输入不一致");
        }

        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, username));
        if(user != null) throw new FastcmsException(FastcmsException.INVALID_PARAM, "账号已存在");

        user = new User();
        user.setUserName(userName);
        user.setSex(User.SEX_MAN);
        user.setPassword(passwordEncoder.encode(password));
        user.setSource(User.SourceType.WEB_REGISTER.name().toLowerCase());
        user.setUserType(User.USER_TYPE_CLIENT);
        save(user);
        return true;
    }

    @Override
    public synchronized String getLastUserNum() {
        return String.valueOf(getBaseMapper().getLastUserNum() + "1000".hashCode());
    }

}
