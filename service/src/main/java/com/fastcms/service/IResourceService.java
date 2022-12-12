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

		String RESOURCE_NAME_ATTACHMENT_LIST = "fastcms.resource.name.attachment.list";
		String RESOURCE_NAME_ATTACHMENT_UPLOAD = "fastcms.resource.name.attachment.upload";
		String RESOURCE_NAME_ATTACHMENT_EDIT = "fastcms.resource.name.attachment.edit";
		String RESOURCE_NAME_ATTACHMENT_INFO = "fastcms.resource.name.attachment.info";
		String RESOURCE_NAME_ATTACHMENT_DELETE = "fastcms.resource.name.attachment.delete";

		String RESOURCE_NAME_CONFIG_SAVE = "fastcms.resource.name.config.save";
		String RESOURCE_NAME_CONFIG_LIST = "fastcms.resource.name.config.list";

		String RESOURCE_NAME_DEPARTMENT_LIST = "fastcms.resource.name.department.list";
		String RESOURCE_NAME_DEPARTMENT_SAVE ="fastcms.resource.name.department.save";
		String RESOURCE_NAME_DEPARTMENT_DELETE = "fastcms.resource.name.department.delete";

		String RESOURCE_NAME_ORDER_LIST = "fastcms.resource.name.order.list";
		String RESOURCE_NAME_ORDER_DETAIL = "fastcms.resource.name.order.detail";
		String RESOURCE_NAME_ORDER_DELETE = "fastcms.resource.name.order.delete";
		String RESOURCE_NAME_ORDER_CASHOUT_LIST = "fastcms.resource.name.order.cashout.list";
		String RESOURCE_NAME_ORDER_CASHOUT_INFO = "fastcms.resource.name.order.cashout.info";
		String RESOURCE_NAME_ORDER_CASHOUT_CONFIRM = "fastcms.resource.name.order.cashout.confirm";
		String RESOURCE_NAME_ORDER_CASHOUT_REFUSE = "fastcms.resource.name.order.cashout.refuse";
		String RESOURCE_NAME_ORDER_PAYMENT_LIST = "fastcms.resource.name.order.payment.list";
		String RESOURCE_NAME_ORDER_PAYMENT_INFO = "fastcms.resource.name.order.payment.info";

		String RESOURCE_NAME_PLUGIN_LIST = "fastcms.resource.name.plugin.list";
		String RESOURCE_NAME_PLUGIN_INSTALL = "fastcms.resource.name.plugin.install";
		String RESOURCE_NAME_PLUGIN_UNINSTALL = "fastcms.resource.name.plugin.uninstall";
		String RESOURCE_NAME_PLUGIN_CONFIG = "fastcms.resource.name.plugin.config";

		String RESOURCE_NAME_RESOURCE_LIST = "fastcms.resource.name.resource.list";
		String RESOURCE_NAME_RESOURCE_SYNC = "fastcms.resource.name.resource.sync";

		String RESOURCE_NAME_ROLE_LIST = "fastcms.resource.name.role.list";
		String RESOURCE_NAME_ROLE_SAVE = "fastcms.resource.name.role.save";
		String RESOURCE_NAME_ROLE_DELETE = "fastcms.resource.name.role.delete";
		String RESOURCE_NAME_ROLE_PERMISSION_LIST = "fastcms.resource.name.role.permission.list";
		String RESOURCE_NAME_ROLE_PERMISSION_SAVE = "fastcms.resource.name.role.permission.save";

		String RESOURCE_NAME_ROUTER_LIST = "fastcms.resource.name.router.list";
		String RESOURCE_NAME_ROUTER_SAVE = "fastcms.resource.name.router.save";
		String RESOURCE_NAME_ROUTER_DELETE = "fastcms.resource.name.router.delete";

		String RESOURCE_NAME_USER_LIST = "fastcms.resource.name.user.list";
		String RESOURCE_NAME_USER_SAVE = "fastcms.resource.name.user.save";
		String RESOURCE_NAME_USER_INFO = "fastcms.resource.name.user.info";
		String RESOURCE_NAME_USER_DELETE = "fastcms.resource.name.user.delete";
		String RESOURCE_NAME_USER_ROLE_SAVE = "fastcms.resource.name.user.role.save";
		String RESOURCE_NAME_USER_TAG_SAVE = "fastcms.resource.name.user.tag.save";
		String RESOURCE_NAME_USER_PASSWORD_EDIT = "fastcms.resource.name.user.password.edit";
		String RESOURCE_NAME_USER_PASSWORD_RESET = "fastcms.resource.name.user.password.reset";
		String RESOURCE_NAME_USER_TYPE_SAVE = "fastcms.resource.name.user.type.save";

	}

}
