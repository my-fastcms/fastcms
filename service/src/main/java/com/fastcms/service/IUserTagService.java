package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.UserTag;

import java.util.List;

/**
 * 用户标签服务类
 * @author wjun_java@163.com
 * @since 2021-05-30
 */
public interface IUserTagService extends IService<UserTag> {

	void saveUserTagRelation(Long userId, List<Long> tagIds);

	UserTag getByName(String tagName);

	List<UserTag> getTagListByUserId(Long userId);

}
