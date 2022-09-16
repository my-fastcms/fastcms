package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.cms.service.ISinglePageService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 单页表 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
public interface SinglePageMapper extends BaseMapper<SinglePage> {

	Page<ISinglePageService.SinglePageVo> pageSinglePage(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
