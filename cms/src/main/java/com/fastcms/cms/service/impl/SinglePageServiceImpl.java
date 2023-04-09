package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.cms.mapper.SinglePageMapper;
import com.fastcms.cms.service.ISinglePageService;
import com.fastcms.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单页服务实现类
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
@Service
public class SinglePageServiceImpl extends ServiceImpl<SinglePageMapper, SinglePage> implements ISinglePageService {

	@Override
	public SinglePage getPageByPath(String path) {
		return getOne(Wrappers.<SinglePage>lambdaQuery().eq(SinglePage::getPath, path));
	}

	@Override
	public Page<SinglePageVo> pageSinglePage(Page pageParam, QueryWrapper queryWrapper) {
		return getBaseMapper().pageSinglePage(pageParam, queryWrapper);
	}

	@Override
	public List<SinglePage> getSinglePageByIds(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return null;
		}
		return list(Wrappers.<SinglePage>lambdaQuery().in(SinglePage::getId, ids));
	}

}
