import request from '/@/utils/request';

/**
 * 文件树形结构
 * @param params 
 * @returns 
 */
export function getTemplateFileTree() {
	return request({
		url: '/admin/template/files/tree',
		method: 'get'
	});
}
