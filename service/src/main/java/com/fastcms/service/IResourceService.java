package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Resource;

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

	interface ResourceI18n {
		String RESOURCE_NAME_ARTICLE_LIST = "fastcms.resource.name.article.list";
		String RESOURCE_NAME_ARTICLE_SAVE = "fastcms.resource.name.article.save";
		String RESOURCE_NAME_ARTICLE_DETAIL = "fastcms.resource.name.article.detail";
		String RESOURCE_NAME_ARTICLE_DELETE = "fastcms.resource.name.article.delete";
		String RESOURCE_NAME_ARTICLE_CATEGORY_SAVE = "fastcms.resource.name.article.category.save";
		String RESOURCE_NAME_ARTICLE_CATEGORY_DELETE = "fastcms.resource.name.article.category.delete";
		String RESOURCE_NAME_ARTICLE_TAG_SAVE = "fastcms.resource.name.article.tag.save";
		String RESOURCE_NAME_ARTICLE_TAG_DELETE = "fastcms.resource.name.article.tag.delete";
		String RESOURCE_NAME_ARTICLE_COMMENT_LIST = "fastcms.resource.name.article.comment.list";
		String RESOURCE_NAME_ARTICLE_COMMENT_SAVE = "fastcms.resource.name.article.comment.save";
		String RESOURCE_NAME_ARTICLE_COMMENT_DELETE = "fastcms.resource.name.article.comment.delete";

		String RESOURCE_NAME_PAGE_LIST = "fastcms.resource.name.page.list";
		String RESOURCE_NAME_PAGE_SAVE = "fastcms.resource.name.page.save";
		String RESOURCE_NAME_PAGE_DETAIL = "fastcms.resource.name.page.detail";
		String RESOURCE_NAME_PAGE_DELETE = "fastcms.resource.name.page.delete";
		String RESOURCE_NAME_PAGE_COMMENT_LIST = "fastcms.resource.name.page.comment.list";
		String RESOURCE_NAME_PAGE_COMMENT_SAVE = "fastcms.resource.name.page.comment.save";
		String RESOURCE_NAME_PAGE_COMMENT_DELETE = "fastcms.resource.name.page.comment.delete";

		String RESOURCE_NAME_TEMPLATE_LIST = "fastcms.resource.name.template.list";
		String RESOURCE_NAME_TEMPLATE_INSTALL = "fastcms.resource.name.template.install";
		String RESOURCE_NAME_TEMPLATE_UNINSTALL = "fastcms.resource.name.template.uninstall";
		String RESOURCE_NAME_TEMPLATE_ENABLE = "fastcms.resource.name.template.enable";
		String RESOURCE_NAME_TEMPLATE_FILE_LIST = "fastcms.resource.name.template.file.list";
		String RESOURCE_NAME_TEMPLATE_FILE_INFO = "fastcms.resource.name.template.file.info";
		String RESOURCE_NAME_TEMPLATE_FILE_SAVE = "fastcms.resource.name.template.file.save";
		String RESOURCE_NAME_TEMPLATE_FILE_UPLOAD = "fastcms.resource.name.template.file.upload";
		String RESOURCE_NAME_TEMPLATE_FILE_DELETE = "fastcms.resource.name.template.file.delete";
		String RESOURCE_NAME_TEMPLATE_MENU_LIST = "fastcms.resource.name.template.menu.list";
		String RESOURCE_NAME_TEMPLATE_MENU_INFO = "fastcms.resource.name.template.menu.info";
		String RESOURCE_NAME_TEMPLATE_MENU_SAVE = "fastcms.resource.name.template.menu.save";
		String RESOURCE_NAME_TEMPLATE_MENU_DELETE = "fastcms.resource.name.template.menu.delete";

	}

}
