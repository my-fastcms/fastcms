package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.entity.UserAmountStatement;
import com.fastcms.service.IUserAmountStatementService;
import org.apache.ibatis.annotations.Param;

/**
 * 用户余额流水情况 Mapper 接口
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
public interface UserAmountStatementMapper extends BaseMapper<UserAmountStatement> {

	Page<IUserAmountStatementService.UserAmountStatementVo> pageUserAmountStatement(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
