package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.UserOpenid;

/**
 * <p>
 * 账号绑定信息表 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-06-08
 */
public interface IUserOpenidService extends IService<UserOpenid> {

	/**
	 * 查找用户
	 * @param openid
	 * @param type
	 * @return
	 */
	UserOpenid getByOpenid(String openid, String type);

}
