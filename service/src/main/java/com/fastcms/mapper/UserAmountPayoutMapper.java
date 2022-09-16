package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.entity.UserAmountPayout;
import com.fastcms.service.IUserAmountPayoutService;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 提现申请表 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-04-09
 */
public interface UserAmountPayoutMapper extends BaseMapper<UserAmountPayout> {

	Page<IUserAmountPayoutService.CashOutListVo> pageCashOut(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	Long getUserTodayPayoutCount(@Param("userId") Long userId);

	IUserAmountPayoutService.CashOutDetailVo getCashOutDetail(@Param("payoutId") Long payoutId);

	BigDecimal getUserUnAuditAmountPayout(@Param("userId") Long userId);

	List<IUserAmountPayoutService.CashOutListVo> getUserCashOutList(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
