package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;
import org.apache.ibatis.annotations.Param;

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

	List<UserTag> getUserTagList(@Param("userId") Long userId);

	Long getLastUserNum();

}
