package com.fastcms.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.SinglePage;

/**
 * 单页服务类
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
	Page<ISinglePageService.SinglePageVo> pageSinglePage(Page pageParam, QueryWrapper queryWrapper);

	class SinglePageVo extends SinglePage {

		/**
		 * 创建者
		 */
		String author;

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}
	}

}
