import request from '/@/utils/request';

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
