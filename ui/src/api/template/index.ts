import request from '/@/utils/request';

/**
 * 获取系统已安装模板列表
 * @returns 
 */
export function getTemplateList() {
	return request({
		url: '/admin/template/list',
		method: 'get'
	});
}

/**
 * 安装模板
 */
export function installTemplate() {
	return request({
		url: '/admin/template/install',
		method: 'post'
	});
}

/**
 * 卸载模板
 */
export function unInstallTemplate(templateId: string) {
	return request({
		url: '/admin/template/unInstall/' + templateId,
		method: 'post'
	});
}

/**
 * 启用模板
 */
export function enableTemplate(templateId: string) {
	return request({
		url: '/admin/template/enable/' + templateId,
		method: 'post'
	});
}

/**
 * 文件树形结构
 * @param params 
 * @returns 
 */
export function getTemplateFileTree() {
	return request({
		url: '/admin/template/files/tree/list',
		method: 'get'
	});
}

/**
 * 获取文件内容
 * @param filePath 
 * @returns 
 */
export function getTemplateFile(filePath: string) {
	return request({
		url: '/admin/template/files/get?filePath=' + filePath,
		method: 'get'
	});
}

/**
 * 保存模板文件
 * @param params 
 * @returns 
 */
export function saveTemplateFile(params: string) {
	return request({
		url: "/admin/template/file/save",
		method: 'post',
		data: params
	});
}

/**
 * 删除模板文件
 * @param filePath 
 * @returns 
 */
export function delTemplateFile(filePath: string) {
	return request({
		url: "/admin/template/file/delete?filePath=" + filePath,
		method: 'post'
	});
}

/**
 * 获取网站模板菜单
 * @returns 
 */
export function getTemplateMenuList() {
	return request({
		url: "/admin/template/menu/list",
		method: 'get'
	});
}

/**
 * 删除网站模板菜单
 */
export function delTemplateMenu(menuId: string) {
	return request({
		url: "/admin/template/menu/delete/" + menuId,
		method: 'post'
	});
}

/**
 * 保存菜单数据
 */
export function saveTemplateMenu(params: object) {
	return request({
		url: "/admin/template/menu/save",
		method: 'post',
		params: params
	});
}
