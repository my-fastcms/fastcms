package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.User;
import com.fastcms.entity.UserOpenid;

/**
 * 账号绑定信息 服务类
 * @author wjun_java@163.com
 * @since 2021-06-08
 */
public interface IUserOpenidService extends IService<UserOpenid> {

    /**
     * 根据openId获取用户
     * @param openId
     * @return
     */
    User getUserByOpenId(String openId);

}
