package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口资源表
 * @author wjun_java@163.com
 * @since 2022-05-01
 */
public interface IResourceService extends IService<Resource> {

	/**
	 * 同步接口资源
	 */
	void syncResources();

	/**
	 * 获取用户接口授权
	 * @param userId
	 * @return
	 */
	List<String> getUserResourceList(Long userId);

}
