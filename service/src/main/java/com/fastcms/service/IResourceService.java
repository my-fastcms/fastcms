package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Resource;

/**
 * 接口资源表
 * @author wjun_java@163.com
 * @since 2022-05-01
 */
public interface IResourceService extends IService<Resource> {

	void syncResources();

}
