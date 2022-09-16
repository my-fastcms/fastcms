package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.UserAmountStatement;
import com.fastcms.mapper.UserAmountStatementMapper;
import com.fastcms.service.IUserAmountStatementService;
import org.springframework.stereotype.Service;

/**
 * 用户余额流水情况
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
@Service
public class UserAmountStatementServiceImpl extends ServiceImpl<UserAmountStatementMapper, UserAmountStatement> implements IUserAmountStatementService {

	@Override
	public Page<UserAmountStatementVo> pageUserAmountStatement(Page pageParam, String action, Integer status) {
		QueryWrapper<Object> queryWrapper = Wrappers.query()
				.eq(StringUtils.isNotBlank(action), "uas.action", action)
				.eq(status != null, "uas.status", status)
				.orderByDesc("uas.created");
		return getBaseMapper().pageUserAmountStatement(pageParam, queryWrapper);
	}

}
