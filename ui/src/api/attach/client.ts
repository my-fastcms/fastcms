import request from '/@/utils/request';

/**
 * 删除附件
 * @param id 
 * @returns 
 */
export function delAttach(id: string) {
	return request({
		url: '/client/attachment/delete/'+id,
		method: 'post'
	});
}
