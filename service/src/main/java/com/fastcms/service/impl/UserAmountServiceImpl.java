package com.fastcms.service.impl;

import com.fastcms.entity.UserAmount;
import com.fastcms.mapper.UserAmountMapper;
import com.fastcms.service.IUserAmountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户余额 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
@Service
public class UserAmountServiceImpl extends ServiceImpl<UserAmountMapper, UserAmount> implements IUserAmountService {

}
