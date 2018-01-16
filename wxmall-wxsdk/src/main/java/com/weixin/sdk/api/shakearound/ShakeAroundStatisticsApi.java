package com.weixin.sdk.api.shakearound;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.weixin.sdk.api.AccessTokenApi;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.shakearound.bean.DeviceIdentifier;
import com.weixin.sdk.utils.HttpUtils;
import com.weixin.sdk.utils.JsonUtils;

/**
 * 数据统计接口
 * @author L.cm
 */
public class ShakeAroundStatisticsApi {
	
	private static String statisticsDeviceUrl = "https://api.weixin.qq.com/shakearound/statistics/device?access_token=";
	
	/**
	 * 以设备为维度的数据统计接口
	 * @param deviceIdentifierList
	 * @param beginDate 起始日期时间戳，最长时间跨度为30天，单位为秒
	 * @param endDate 结束日期时间戳，最长时间跨度为30天，单位为秒
	 * @return
	 */
	public static ApiResult getByDevice(DeviceIdentifier deviceIdentifier, Date beginDate, Date endDate) {
		String url = statisticsDeviceUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("device_identifier", deviceIdentifier);
		data.put("begin_date", beginDate.getTime() / 1000);
		data.put("end_date", endDate.getTime() / 1000);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String statisticsDeviceListUrl = "https://api.weixin.qq.com/shakearound/statistics/devicelist?access_token=";
	
	/**
	 * 批量查询设备统计数据接口
	 * 查询指定时间商家帐号下的每个设备进行摇周边操作的人数、次数，点击摇周边消息的人数、次数。
	 * 只能查询最近90天内的数据，且一次只能查询一天。
	 * 
	 * @param date 指定查询日期时间戳，单位为秒
	 * @param pageIndex 指定查询的结果页序号；返回结果按摇周边人数降序排序，每50条记录为一页
	 * @return
	 */
	public static ApiResult getDeviceList(Date date, int pageIndex) {
		String url = statisticsDeviceListUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("date", date.getTime() / 1000);
		data.put("page_index", pageIndex);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String statisticsPageUrl = "https://api.weixin.qq.com/shakearound/statistics/page?access_token=";
	
	/**
	 * 以页面为维度的数据统计接口
	 * @param pageId 指定页面的设备ID
	 * @param beginDate 起始日期时间戳，最长时间跨度为30天，单位为秒
	 * @param endDate 结束日期时间戳，最长时间跨度为30天，单位为秒
	 * @return
	 */
	public static ApiResult getByPage(int pageId, Date beginDate, Date endDate) {
		String url = statisticsPageUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("page_id", pageId);
		data.put("begin_date", beginDate.getTime() / 1000);
		data.put("end_date", endDate.getTime() / 1000);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String statisticsPageListUrl = "https://api.weixin.qq.com/shakearound/statistics/pagelist?access_token=";
	
	/**
	 * 批量查询设备统计数据接口
	 * 查询指定时间商家帐号下的每个设备进行摇周边操作的人数、次数，点击摇周边消息的人数、次数。
	 * 只能查询最近90天内的数据，且一次只能查询一天。
	 * 
	 * @param date 指定查询日期时间戳
	 * @param pageIndex 指定查询的结果页序号；返回结果按摇周边人数降序排序，每50条记录为一页
	 * @return
	 */
	public static ApiResult getPageList(Date date, int pageIndex) {
		String url = statisticsPageListUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("date", date.getTime() / 1000);
		data.put("page_index", pageIndex);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
}
