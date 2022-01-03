package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;
import com.fastcms.mapper.RoleMapper;
import com.fastcms.mapper.UserMapper;
import com.fastcms.service.IRoleService;
import com.fastcms.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void updateUserLoginTime(Long userId) {
        User user = getById(userId);
        if(user != null) {
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

        if(!user.getPassword().equals(passwordEncoder.encode(userParam.getPassword()))) {
            throw new Exception("旧密码输入错误");
        }

        updateById(user);
    }

    @Override
    public List<UserTag> getUserTagList(Long userId) {
        return getBaseMapper().getUserTagList(userId);
    }

    @Override
    @Transactional
    public Boolean saveUser(User user) throws FastcmsException {

        if(user.getId() == null) {
            User one = getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()));
            if(one != null) throw new FastcmsException(FastcmsException.SERVER_ERROR, "账号已存在");

            if(StringUtils.isBlank(user.getPassword())) {
                throw new FastcmsException(FastcmsException.SERVER_ERROR, "密码不能为空");
            }
        } else{
            if(user.getId() == FastcmsConstants.ADMIN_USER_ID) {
                throw new FastcmsException(FastcmsException.NO_RIGHT, "超级管理员不可修改");
            }
        }

        if(StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        saveOrUpdate(user);
        roleService.saveUserRole(user.getId(), user.getRoleList());
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteUser(Long userId) {
        roleMapper.deleteRoleByUserId(userId);
        removeById(userId);
        return true;
    }

    @Override
    public Boolean register(String username, String password, String repeatPassword) throws FastcmsException {
        if(StrUtils.isBlank(username) || StrUtils.isBlank(password) || StrUtils.isBlank(repeatPassword)) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "请输入完成注册数据");
        }

        if(!repeatPassword.equals(password)) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "两次密码输入不一致");
        }

        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, username));
        if(user != null) throw new FastcmsException(FastcmsException.INVALID_PARAM, "账号已存在");

        user = new User();
        user.setUserName(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setSource(User.SourceType.WEB_REGISTER.name().toLowerCase());
        save(user);
        return true;
    }

}
