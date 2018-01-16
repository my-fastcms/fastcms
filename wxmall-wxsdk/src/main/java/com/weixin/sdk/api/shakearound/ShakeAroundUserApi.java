package com.weixin.sdk.api.shakearound;

import java.util.HashMap;
import java.util.Map;

import com.weixin.sdk.api.AccessTokenApi;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.utils.HttpUtils;
import com.weixin.sdk.utils.JsonUtils;

/**
 * 获取摇周边的设备及用户信息
 * @author L.cm
 */
public class ShakeAroundUserApi {
	
	private static String getShakeInfoUrl = "https://api.weixin.qq.com/shakearound/user/getshakeinfo?access_token=";
	
	/**
	 * 获取设备信息，包括UUID、major、minor，以及距离、openID等信息。
	 * @param ticket
	 * @return
	 */
	public static ApiResult getShakeInfo(String ticket) {
		return getShakeInfo(ticket, false);
	}
	
	/**
	 * 获取设备信息，包括UUID、major、minor，以及距离、openID等信息。
	 * @param ticket 摇周边业务的ticket，可在摇到的URL中得到，ticket生效时间为30分钟，每一次摇都会重新生成新的ticket
	 * @param needPoi 是否需要返回门店poi_id，传1则返回，否则不返回；可查看门店相关的接口文档:https://mp.weixin.qq.com/zh_CN/htmledition/comm_htmledition/res/store_manage/store_manage_file.zip
	 * @return
	 */
	public static ApiResult getShakeInfo(String ticket, boolean needPoi) {
		String url = getShakeInfoUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ticket", ticket);
		if (needPoi) {
			data.put("need_poi", 1);
		}
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
}
