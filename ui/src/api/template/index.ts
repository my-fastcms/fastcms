import request from '/@/utils/request';

export function TemplateApi() {
	return {
		/**
		 * 获取系统已安装模板列表
		 * @returns 
		 */
		getTemplateList() {
			return request({
				url: '/admin/template/list',
				method: 'get'
			});
		},

		/**
		 * 安装模板
		 */
		installTemplate() {
			return request({
				url: '/admin/template/install',
				method: 'post'
			});
		},

		/**
		 * 卸载模板
		 */
		unInstallTemplate(templateId: string) {
			return request({
				url: '/admin/template/unInstall/' + templateId,
				method: 'post'
			});
		},

		/**
		 * 启用模板
		 */
		enableTemplate(templateId: string) {
			return request({
				url: '/admin/template/enable/' + templateId,
				method: 'post'
			});
		},

		/**
		 * 文件树形结构
		 * @param params 
		 * @returns 
		 */
		getTemplateFileTree() {
			return request({
				url: '/admin/template/files/tree/list',
				method: 'get'
			});
		},

		/**
		 * 获取文件内容
		 * @param filePath 
		 * @returns 
		 */
		getTemplateFile(filePath: string) {
			return request({
				url: '/admin/template/files/get?filePath=' + filePath,
				method: 'get'
			});
		},

		/**
		 * 保存模板文件
		 * @param params 
		 * @returns 
		 */
		saveTemplateFile(params: object) {
			return request({
				url: "/admin/template/file/save",
				method: 'post',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				data: params
			});
		},

		/**
		 * 删除模板文件
		 * @param filePath 
		 * @returns 
		 */
		delTemplateFile(filePath: string) {
			return request({
				url: "/admin/template/file/delete?filePath=" + filePath,
				method: 'post'
			});
		},

		/**
		 * 获取网站模板菜单
		 * @returns 
		 */
		getTemplateMenuList() {
			return request({
				url: "/admin/template/menu/list",
				method: 'get'
			});
		},

		/**
		 * 获取网站模板菜单
		 * @param menuId 
		 * @returns 
		 */
		getTemplateMenu(menuId: number) {
			return request({
				url: "/admin/template/menu/get/" + menuId,
				method: "get"
			})
		},

		/**
		 * 删除网站模板菜单
		 */
		delTemplateMenu(menuId: string) {
			return request({
				url: "/admin/template/menu/delete/" + menuId,
				method: 'post'
			});
		},

		/**
		 * 保存菜单数据
		 */
		saveTemplateMenu(params: object) {
			return request({
				url: "/admin/template/menu/save",
				method: 'post',
				params: params
			});
		},

	};
}