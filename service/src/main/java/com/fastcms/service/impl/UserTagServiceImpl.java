package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.UserTag;
import com.fastcms.mapper.UserTagMapper;
import com.fastcms.service.IUserTagService;
import com.fastcms.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-30
 */
@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag> implements IUserTagService {

	@Override
	public void saveUserTagRelation(Long userId, List<Long> tagIds) {
		getBaseMapper().deleteUserTagRelationByUserId(userId);
		if(CollectionUtils.isNotEmpty(tagIds)) {
			getBaseMapper().saveUserTagRelation(userId, tagIds);
		}
	}

	@Override
	public UserTag getByName(String tagName) {
		UserTag one = getOne(Wrappers.<UserTag>lambdaQuery().eq(UserTag::getTitle, tagName));
		if(one == null) {
			one = new UserTag();
			one.setTitle(tagName);
		}
		return one;
	}

	@Override
	public List<UserTag> getTagListByUserId(Long userId) {
		return getBaseMapper().getTagListByUserId(userId);
	}

}
