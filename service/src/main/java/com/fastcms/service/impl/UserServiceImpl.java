package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.aspect.Log;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.Station;
import com.fastcms.entity.User;
import com.fastcms.entity.UserOpenid;
import com.fastcms.entity.UserTag;
import com.fastcms.mapper.UserMapper;
import com.fastcms.service.IStationService;
import com.fastcms.service.IUserOpenidService;
import com.fastcms.service.IUserService;
import com.fastcms.utils.PasswordUtils;
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

    @Autowired
    private IStationService stationService;

    @Override
    @Log
    public void updateUserLoginTime(String loginAccount) {

        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.eq(User::getLoginAccount, loginAccount);
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

        final String result = PasswordUtils.getMd5Password(user.getSalt(), userParam.getPassword());
        if(!result.equals(user.getPassword())) {
            throw new Exception("旧密码输入错误");
        }

        final String salt = System.currentTimeMillis() + "";
        final String newPwd = PasswordUtils.getMd5Password(salt, userParam.getNewPassword());

        user.setPassword(newPwd);
        user.setSalt(salt);
        updateById(user);

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
    public Page<UserTeamVo> getUserTeamList(Page page, Long userId, String stationName) {
        return getBaseMapper().getUserTeamList(page, userId, stationName);
    }

    @Override
    public User getUserBelongToTeam(Long userId) {
        List<Station> userStationList = getUserStationList(userId);
        if(userStationList == null || userStationList.size()<=0) {
            return null;
        }

        Station station = userStationList.get(0);
        if(station.getWithManager()) {
            //管理岗位角色
            return getById(userId);
        }

        UserTeam userBelongToTeam = getBaseMapper().getUserBelongToTeam(userId);
        if (userBelongToTeam == null) return null;

        return getUserBelongToTeam(userBelongToTeam.getParentId());
    }

    @Override
    public List<Station> getUserStationList(Long userId) {
        return getBaseMapper().getUserStationList(userId);
    }

    @Override
    public User getUserBySearch(Long userId, String keyword) {
        User user = getBaseMapper().getTeamUserByUserName(userId, keyword);
        if(user == null) {
            user = getUserByPhone(keyword);
        }
        return user;
    }

    @Override
    @Transactional
    public synchronized void saveGroupUser(AddGroupUserParam addGroupUserParam) throws Exception {

        if(addGroupUserParam.getTagId() == null) {
            throw new FastcmsException("请选择成员岗位");
        }

        User parent = getById(addGroupUserParam.getParentId());
        if(parent == null) {
            throw new FastcmsException("上级用户不存在");
        }

        User user = getUserByPhone(addGroupUserParam.getPhone());
        if(user == null) {
            throw new FastcmsException("用户不存在");
        }

        if(Objects.equals(parent.getId(), user.getId())) {
            throw new FastcmsException("自己不能分配给自己");
        }

        UserTeam userTeam = getBaseMapper().getUserTeam(parent.getId(), user.getId());
        if(userTeam != null) {
            throw new FastcmsException("该用户已经存在其他团队了");
        }

        user.setUserName(addGroupUserParam.getUserName());
        updateById(user);

        getBaseMapper().saveUserTeam(parent.getId(), user.getId());

        List<Long> stationIds = new ArrayList<>();
        stationIds.add(addGroupUserParam.getTagId());
        stationService.saveUserStation(user.getId(), stationIds);

    }

}
