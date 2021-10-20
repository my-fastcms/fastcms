package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.entity.Station;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;
import com.fastcms.service.IUserService;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface UserMapper extends BaseMapper<User> {

	List<UserTag> getUserTagList(Long userId);

	List<Station> getUserStationList(Long userId);

	Page<IUserService.UserTeamVo> getUserTeamList(Page page, Long userId, String stationName);

	IUserService.UserTeam getUserTeam(Long parentId, Long userId);

	IUserService.UserTeam getUserBelongToTeam(Long userId);

	void saveUserTeam(Long parentId, Long userId);

	User getTeamUserByUserName(Long parentId, String userName);

}
