package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.UserAmount;
import com.fastcms.service.IUserAmountService;

/**
 * <p>
 * 用户余额 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
public interface UserAmountMapper extends BaseMapper<UserAmount> {

	IUserAmountService.UserAmountVo getUserAmount();

}
