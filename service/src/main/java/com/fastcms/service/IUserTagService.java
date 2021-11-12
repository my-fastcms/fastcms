package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.UserTag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-30
 */
public interface IUserTagService extends IService<UserTag> {

	void saveUserTagRelation(Long userId, List<Long> tagIds);

	List<UserTagVo> getTagListByUserId(Long userId);

	class UserTagVo extends UserTag {
		Long userId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}
	}

}
