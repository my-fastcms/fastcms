package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.UserTag;
import com.fastcms.service.IUserTagService;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-30
 */
public interface UserTagMapper extends BaseMapper<UserTag> {

	void saveUserTagRelation(Long userId, List<Long> tagIds);

	void deleteUserTagRelationByUserId(Long userId);

	List<IUserTagService.UserTagVo> getTagListByUserId(Long userId);

}
