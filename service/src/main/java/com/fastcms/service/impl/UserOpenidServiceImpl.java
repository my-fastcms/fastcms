package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.UserOpenid;
import com.fastcms.mapper.UserOpenidMapper;
import com.fastcms.service.IUserOpenidService;
import org.springframework.stereotype.Service;

/**
 * 账号绑定信息服务实现类
 * @author wjun_java@163.com
 * @since 2021-06-08
 */
@Service
public class UserOpenidServiceImpl extends ServiceImpl<UserOpenidMapper, UserOpenid> implements IUserOpenidService {

}
