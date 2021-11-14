package com.fastcms.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.core.mybatis.DataPermission;
/**
 * <p>
 * 单页表 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
public interface ISinglePageService extends IService<SinglePage> {

	/**
	 * 根据页面path获取页面对象
	 * @param path
	 * @return
	 */
	SinglePage getPageByPath(String path);

	/**
	 * 分页获取页面列表
	 * @param pageParam
	 * @param queryWrapper
	 * @return
	 */
	@DataPermission("p")
	Page<ISinglePageService.SinglePageVo> pageSinglePage(Page pageParam, QueryWrapper queryWrapper);

	class SinglePageVo extends SinglePage {

		/**
		 * 创建者
		 */
		String createdUser;

		public String getCreatedUser() {
			return createdUser;
		}

		public void setCreatedUser(String createdUser) {
			this.createdUser = createdUser;
		}
	}

}
