package com.fastcms.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.exception.I18nFastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.email.autoconfigure.FastcmsJavaMailSender;
import com.fastcms.entity.User;
import com.fastcms.entity.UserOpenid;
import com.fastcms.entity.UserTag;
import com.fastcms.mapper.DepartmentMapper;
import com.fastcms.mapper.RoleMapper;
import com.fastcms.mapper.UserMapper;
import com.fastcms.mapper.UserTagMapper;
import com.fastcms.service.*;
import com.fastcms.utils.ConfigUtils;
import com.fastcms.utils.I18nUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public void updateUserPassword(UpdatePasswordParam updatePasswordParam) throws FastcmsException {

        if (StringUtils.isBlank(updatePasswordParam.getOldPassword())) {
            throw new I18nFastcmsException(UserI18n.USER_OLD_PASSWORD_NOT_NULL);
        }

        if(StringUtils.isBlank(updatePasswordParam.getPassword())) {
            throw new I18nFastcmsException(UserI18n.USER_NEW_PASSWORD_NOT_NULL);
        }

        if(updatePasswordParam.getPassword().length() < getPwdMinLengthConfig()) {
            throw new I18nFastcmsException(UserI18n.USER_PASSWORD_LENGTH6, getPwdMinLengthConfig().toString());
        }

        if(!updatePasswordParam.getPassword().equals(updatePasswordParam.getConfirmPassword())) {
            throw new I18nFastcmsException(UserI18n.USER_PASSWORD_ERROR2);
        }

        User user = getById(updatePasswordParam.getId());
        if(user == null) {
            throw new I18nFastcmsException(FastcmsException.SERVER_ERROR, UserI18n.USER_NOT_EXIST);
        }

        if (!passwordEncoder.matches(updatePasswordParam.getOldPassword(), user.getPassword())) {
            throw new I18nFastcmsException(FastcmsException.SERVER_ERROR, UserI18n.USER_OLD_PASSWORD_ERROR1);
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

        if (user == null) {
            throw new I18nFastcmsException(FastcmsException.SERVER_ERROR, UserI18n.USER_NOT_EXIST);
        }

        if(user.getId() != null && Objects.equals(FastcmsConstants.ADMIN_USER_ID, user.getId())) {
            throw new I18nFastcmsException(FastcmsException.NO_RIGHT, UserI18n.USER_ADMIN_NOT_MODIFY);
        }

        if(user.getId() == null) {
            final String userName = user.getUserName();

            if (StringUtils.isBlank(userName)) {
                throw new I18nFastcmsException(FastcmsException.INVALID_PARAM, UserI18n.USER_ACCOUNT_NOT_NULL);
            }

            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(userName);
            if (m.matches()) {
                throw new I18nFastcmsException(FastcmsException.INVALID_PARAM, UserI18n.USER_ACCOUNT_REGISTER_ALL_NUMBER);
            }

            User one = getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()), false);
            if(one != null) throw new I18nFastcmsException(FastcmsException.SERVER_ERROR, UserI18n.USER_ACCOUNT_REGISTER_EXIST);

            if(StringUtils.isBlank(user.getPassword())) {
                throw new I18nFastcmsException(FastcmsException.SERVER_ERROR, UserI18n.USER_NEW_PASSWORD_NOT_NULL);
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
    public User saveWxMaUserInfo(String openid, WxMaUserInfo userInfo) throws FastcmsException {

        if(userInfo == null || StringUtils.isBlank(openid)) {
            throw new I18nFastcmsException(FastcmsException.INVALID_PARAM, UserI18n.USER_MINIAPP_PARAMS_ERROR1);
        }

        UserOpenid userOpenid = userOpenidService.getOne(Wrappers.<UserOpenid>lambdaQuery().eq(UserOpenid::getValue, openid).eq(UserOpenid::getType, UserOpenid.TYPE_WECHAT_MINI));
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
            userOpenid.setValue(openid);
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
            throw new I18nFastcmsException(UserI18n.USER_MINIAPP_PARAMS_ERROR1);
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
            throw new I18nFastcmsException(FastcmsException.NO_RIGHT, UserI18n.USER_ADMIN_NOT_DELETE);
        }
        ((RoleMapper) roleService.getBaseMapper()).deleteRoleByUserId(userId);
        ((UserTagMapper) userTagService.getBaseMapper()).deleteUserTagRelationByUserId(userId);
        ((DepartmentMapper) departmentService.getBaseMapper()).deleteDepartmentByUserId(userId);
        removeById(userId);
        return true;
    }

    @Override
    public synchronized Boolean register(@NotNull String username, @NotNull String password, @NotNull String repeatPassword) throws FastcmsException {
        if(StrUtils.isBlank(username)) {
            throw new I18nFastcmsException(UserI18n.USER_ACCOUNT_NOT_NULL);
        }

        final String userName = username.trim();
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(userName);
        if (m.matches()) {
            throw new I18nFastcmsException(UserI18n.USER_ACCOUNT_REGISTER_ALL_NUMBER);
        }

        if (StrUtils.isBlank(password)) {
            throw new I18nFastcmsException(UserI18n.USER_NEW_PASSWORD_NOT_NULL);
        }

        if (StrUtils.isBlank(repeatPassword)) {
            throw new I18nFastcmsException(UserI18n.USER_CONFIRM_PASSWORD_NOT_NULL);
        }

        if (password.length() < getPwdMinLengthConfig()) {
            throw new I18nFastcmsException(UserI18n.USER_PASSWORD_LENGTH6, getPwdMinLengthConfig().toString());
        }

        if (repeatPassword.length() < getPwdMinLengthConfig()) {
            throw new I18nFastcmsException(UserI18n.USER_PASSWORD_LENGTH6, getPwdMinLengthConfig().toString());
        }

        if(!repeatPassword.equals(password)) {
            throw new I18nFastcmsException(UserI18n.USER_PASSWORD_ERROR2);
        }

        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, username));
        if(user != null) throw new I18nFastcmsException(UserI18n.USER_ACCOUNT_REGISTER_EXIST);

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

    @Override
    public void changeUserType(Long userId, Integer userType) throws FastcmsException {
        if (userId != null && userId == FastcmsConstants.ADMIN_USER_ID) {
            throw new I18nFastcmsException(UserI18n.USER_ADMIN_NOT_MODIFY);
        }

        User user = getById(userId);
        if (user == null) {
            throw new I18nFastcmsException(UserI18n.USER_NOT_EXIST);
        }

        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new I18nFastcmsException(UserI18n.USER_DISABLE);
        }

        if (user.getUserType() == userType) {
            throw new I18nFastcmsException(UserI18n.USER_TYPE_CHANGE_NOT_MODIFY);
        }

        if (StrUtils.isBlank(user.getPassword())) {
            try {
                sendPasswordEmail(user);
            } catch (Exception e) {
                throw new FastcmsException(String.format(I18nUtils.getMessage(FastcmsJavaMailSender.EmailI18n.EMAIL_SEND_ERROR), user.getEmail()));
            }
        }

        user.setUserType(userType);
        updateById(user);
    }

    @Override
    public void resetPassword(@NotNull Long userId) throws FastcmsException {
        if (userId != null && userId == FastcmsConstants.ADMIN_USER_ID) {
            throw new I18nFastcmsException(UserI18n.USER_ADMIN_NOT_RESET_PASSWORD);
        }

        User user = getById(userId);
        resetPassword(user);
    }

    @Override
    public void resetPassword(@NotNull String userName) throws FastcmsException {
        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, userName));
        resetPassword(user);
    }

    @Override
    public void resetPassword(@NotNull User user) throws FastcmsException {
        if (user == null) {
            throw new I18nFastcmsException(UserI18n.USER_NOT_EXIST);
        }

        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new I18nFastcmsException(UserI18n.USER_DISABLE);
        }

        try {
            sendPasswordEmail(user);
        } catch (Exception e) {
            throw new I18nFastcmsException(String.format(I18nUtils.getMessage(FastcmsJavaMailSender.EmailI18n.EMAIL_SEND_ERROR), user.getEmail()));
        }

        updateById(user);
    }

    @Override
    public User getUserByOpenId(String openId) {
        UserOpenid userOpenid = userOpenidService.getOne(Wrappers.<UserOpenid>lambdaQuery().eq(UserOpenid::getValue, openId));
        return userOpenid == null ? null : getById(userOpenid.getUserId());
    }

    void sendPasswordEmail(User user) throws Exception {

        if (StringUtils.isBlank(user.getEmail())) {
            throw new FastcmsException(UserI18n.USER_EMAIL_NOT_NULL);
        }

        final String password = RandomStringUtils.random(8, RANDOM_STR);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(ConfigUtils.getConfig(FastcmsConstants.EMAIL_USERNAME));
        helper.setTo(user.getEmail());
        helper.setSubject(I18nUtils.getMessage(UserI18n.USER_EMAIL_ENABLE_SUBJECT));
        helper.setText(String.format(I18nUtils.getMessage(UserI18n.USER_EMAIL_ENABLE_CONTENT), user.getUserName(), password), true);
        helper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);

        user.setPassword(passwordEncoder.encode(password));
    }

    Integer getPwdMinLengthConfig() {
        return ConfigUtils.getInt(FastcmsConstants.PWD_MIN_LENGTH, 6);
    }

}
