package com.weixin.sdk.api.shakearound;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.weixin.sdk.api.AccessTokenApi;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.utils.HttpUtils;
import com.weixin.sdk.utils.JsonUtils;

public class ShakeAroundPageApi {

	private static String pageAddUrl = "https://api.weixin.qq.com/shakearound/page/add?access_token=";
	
	/**
	 * 新增摇一摇出来的页面信息，包括在摇一摇页面出现的主标题、副标题、图片和点击进去的超链接。
	 * 其中，图片必须为用素材管理接口上传至微信侧服务器后返回的链接。
	 * 
	 * @param title 在摇一摇页面展示的主标题，不超过6个汉字或12个英文字母
	 * @param pageUrl 页面地址
	 * @param description 在摇一摇页面展示的副标题，不超过7个汉字或14个英文字母
	 * @param iconUrl 在摇一摇页面展示的图片。图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，返回的图片URL再配置在此处
	 * @return
	 */
	public static ApiResult addPage(String title, String pageUrl, String description, String iconUrl) {
		return addPage(title, pageUrl, description, null, iconUrl);
	}
	
	/**
	 * 新增摇一摇出来的页面信息，包括在摇一摇页面出现的主标题、副标题、图片和点击进去的超链接。
	 * 其中，图片必须为用素材管理接口上传至微信侧服务器后返回的链接。
	 * 
	 * @param title 在摇一摇页面展示的主标题，不超过6个汉字或12个英文字母
	 * @param pageUrl 页面地址
	 * @param description 在摇一摇页面展示的副标题，不超过7个汉字或14个英文字母
	 * @param iconUrl 在摇一摇页面展示的图片。图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，返回的图片URL再配置在此处
	 * @param comment 页面的备注信息，不超过15个汉字或30个英文字母
	 * @return
	 */
	public static ApiResult addPage(String title, String pageUrl, String description, String comment, String iconUrl) {
		String url = pageAddUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("title", title);
		data.put("description", description);
		data.put("page_url", pageUrl);
		if (StringUtils.isNotBlank(comment)) {
			data.put("comment", comment);
		}
		data.put("icon_url", iconUrl);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String pageUpdateUrl = "https://api.weixin.qq.com/shakearound/page/update?access_token=";
	
	/**
	 * 新增摇一摇出来的页面信息，包括在摇一摇页面出现的主标题、副标题、图片和点击进去的超链接。
	 * 其中，图片必须为用素材管理接口上传至微信侧服务器后返回的链接。
	 * 
	 * @param pageId 摇周边页面唯一ID
	 * @param title 在摇一摇页面展示的主标题，不超过6个汉字或12个英文字母
	 * @param pageUrl 页面地址
	 * @param description 在摇一摇页面展示的副标题，不超过7个汉字或14个英文字母
	 * @param iconUrl 在摇一摇页面展示的图片。图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，返回的图片URL再配置在此处
	 * @return
	 */
	public static ApiResult updatePage(int pageId, String title, String pageUrl, String description, String iconUrl) {
		return updatePage(pageId, title, pageUrl, description, null, iconUrl);
	}
	
	/**
	 * 新增摇一摇出来的页面信息，包括在摇一摇页面出现的主标题、副标题、图片和点击进去的超链接。
	 * 其中，图片必须为用素材管理接口上传至微信侧服务器后返回的链接。
	 * 
	 * @param pageId 摇周边页面唯一ID
	 * @param title 在摇一摇页面展示的主标题，不超过6个汉字或12个英文字母
	 * @param pageUrl 页面地址
	 * @param description 在摇一摇页面展示的副标题，不超过7个汉字或14个英文字母
	 * @param iconUrl 在摇一摇页面展示的图片。图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，返回的图片URL再配置在此处
	 * @param comment 页面的备注信息，不超过15个汉字或30个英文字母
	 * @return
	 */
	public static ApiResult updatePage(int pageId, String title, String pageUrl, String description, String comment, String iconUrl) {
		String url = pageUpdateUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("page_id", pageId);
		data.put("title", title);
		data.put("description", description);
		data.put("page_url", pageUrl);
		if (StringUtils.isNotBlank(comment)) {
			data.put("comment", comment);
		}
		data.put("icon_url", iconUrl);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String pageSearchUrl = "https://api.weixin.qq.com/shakearound/page/search?access_token=";
	
	/**
	 * 查询已有的页面，包括在摇一摇页面出现的主标题、副标题、图片和点击进去的超链接。
	 * 页面ID查询
	 * 
	 * @param pageIds 定页面ID查询
	 * @return
	 */
	public static ApiResult searchByIds(int... pageIds) {
		String url = pageSearchUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", 1);
		data.put("page_ids", pageIds);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 查询已有的页面，包括在摇一摇页面出现的主标题、副标题、图片和点击进去的超链接。
	 * 批量拉取页面列表。
	 * @param begin 页面列表的起始索引值；
	 * @param count 待查询的页面数量，不能超过50个；
	 * @return
	 */
	public static ApiResult searchPage(int begin, int count) {
		String url = pageSearchUrl + AccessTokenApi.getAccessTokenStr();
		
		if(begin < 0) begin = 0;
		if(count > 50) count = 50;
		if(count < 1) count = 1;
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", 2);
		data.put("begin", begin);
		data.put("count", count);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String pageDeleteUrl = "https://api.weixin.qq.com/shakearound/page/delete?access_token=";
	
	/**
	 * 删除已有的页面，包括在摇一摇页面出现的主标题、副标题、图片和点击进去的超链接。
	 * 只有页面与设备没有关联关系时，才可被删除。
	 * 
	 * @param pageId 指定页面的id
	 * @return
	 */
	public static ApiResult deletePage(int pageId) {
		String url = pageDeleteUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("page_id", pageId);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
}
