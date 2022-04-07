package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.entity.UserAmount;
import com.fastcms.service.IUserAmountService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户余额 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
public interface UserAmountMapper extends BaseMapper<UserAmount> {

    Page<IUserAmountService.CashOutListVo> pageCashOut(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
