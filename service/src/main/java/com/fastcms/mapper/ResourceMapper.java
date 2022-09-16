package com.fastcms.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.fastcms.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 接口资源表 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-05-01
 */
public interface ResourceMapper extends BaseMapper<Resource> {

	@InterceptorIgnore(blockAttack="1")
	void deleteAll();

	List<String> getUserResourceList(@Param("userId") Long userId);

}
