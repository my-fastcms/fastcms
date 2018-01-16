package com.weixin.sdk.api.shakearound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.weixin.sdk.api.AccessTokenApi;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.shakearound.bean.DeviceIdentifier;
import com.weixin.sdk.utils.HttpUtils;
import com.weixin.sdk.utils.JsonUtils;

/**
 * 周边-设备管理
 * @author L.cm
 */
public class ShakeAroundDeviceApi {
	
	private static String applyIdUrl = "https://api.weixin.qq.com/shakearound/device/applyid?access_token=";
	
	/**
	 * 申请配置设备所需的UUID、Major、Minor。申请成功后返回批次ID，可用返回的批次ID通过“查询设备ID申请状态”接口查询目前申请的审核状态。
	 * 若单次申请的设备ID数量小于500个，系统会进行快速审核；若单次申请的设备ID数量大于等 500个 ，会在三个工作日内完成审核。
	 * 
	 * @param quantity 申请的设备ID的数量，单次新增设备超过500个，需走人工审核流程
	 * @param applyReason 申请理由，不超过100个汉字或200个英文字母
	 * @param comment 备注，可为空，不超过15个汉字或30个英文字母
	 * @param poiId 设备关联的门店ID，可为空，关联门店后，在门店1KM的范围内有优先摇出信息的机会。门店相关信息具体可查看：https://mp.weixin.qq.com/zh_CN/htmledition/comm_htmledition/res/store_manage/store_manage_file.zip
	 * @return
	 */
	public static ApiResult applyId(int quantity, String applyReason, String comment, Integer poiId) {
		String url = applyIdUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("quantity", quantity);
		data.put("apply_reason", applyReason);
		if (StringUtils.isBlank(comment)) {
			data.put("comment", comment);
		}
		if (null == poiId) {
			data.put("poi_id", poiId);
		}
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String applyStatusUrl = "https://api.weixin.qq.com/shakearound/device/applystatus?access_token=";
	
	/**
	 * 查询设备ID申请的审核状态。若单次申请的设备ID数量小于等于500个，
	 * 系统会进行快速审核；若单次申请的设备ID数量大于500个，则在三个工作日内完成审核。
	 * @param applyId 批次ID，申请设备ID时所返回的批次ID
	 * @return
	 */
	public static ApiResult getApplyStatus(int applyId) {
		String url = applyStatusUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("apply_id", applyId);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String updateUrl = "https://api.weixin.qq.com/shakearound/device/update?access_token=";
	
	/**
	 * 编辑设备的备注信息。可用设备ID或完整的UUID、Major、Minor指定设备，二者选其一。
	 * @param deviceIdentifier
	 * @param comment 设备的备注信息，不超过15个汉字或30个英文字母。
	 * @return
	 */
	public static ApiResult updateDeviceInfo(DeviceIdentifier deviceIdentifier, String comment) {
		String url = updateUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("device_identifier", deviceIdentifier);
		data.put("comment", comment);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String bindLocationUrl = "https://api.weixin.qq.com/shakearound/device/bindlocation?access_token=";
	
	/**
	 * 配置设备与门店的关联关系
	 * 支持创建门店后直接关联在设备上，无需为审核通过状态，摇周边后台自动更新门店的最新信息和状态。
	 * @param deviceIdentifier
	 * @param poiId 设备关联的门店ID，关联门店后，在门店1KM的范围内有优先摇出信息的机会。当值为0时，将清除设备已关联的门店ID。门店相关信息具体可查看：https://mp.weixin.qq.com/zh_CN/htmledition/comm_htmledition/res/store_manage/store_manage_file.zip
	 * @return
	 */
	public static ApiResult bindLocation(DeviceIdentifier deviceIdentifier, String poiId) {
		String url = bindLocationUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("device_identifier", deviceIdentifier);
		data.put("poi_id", poiId);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String searchUrl = "https://api.weixin.qq.com/shakearound/device/search?access_token=";
	
	/**
	 * 查询设备列表
	 * @param deviceIdentifier
	 * @return
	 */
	public static ApiResult searchByDevice(DeviceIdentifier deviceIdentifier) {
		String url = searchUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("device_identifier", deviceIdentifier);
		data.put("type", 1);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 需要分页查询或者指定范围内的设备时
	 * @param begin 页面列表的起始索引值；
	 * @param count 待查询的页面数量，不能超过50个；
	 * @return
	 */
	public static ApiResult searchPage(int begin, int count) {
		String url = searchUrl + AccessTokenApi.getAccessTokenStr();
		
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
	
	/**
	 * 根据批次id查找
	 * @param begin 页面列表的起始索引值；
	 * @param count 待查询的页面数量，不能超过50个；
	 * @return
	 */
	public static ApiResult searchPage(int applyId, int begin, int count) {
		String url = searchUrl + AccessTokenApi.getAccessTokenStr();
		
		if(begin < 0) begin = 0;
		if(count > 50) count = 50;
		if(count < 1) count = 1;
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", 3);
		data.put("apply_id", applyId);
		data.put("begin", begin);
		data.put("count", count);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String bindPageUrl = "https://api.weixin.qq.com/shakearound/device/bindpage?access_token=";
	
	/**
	 * 配置设备与页面的关联关系。配置时传入该设备需要关联的页面的id列表（该设备原有的关联关系将被直接清除）；页面的id列表允许为空，当页面的id列表为空时则会清除该设备的所有关联关系。配置完成后，在此设备的信号范围内，即可摇出关联的页面信息。
	 * 在申请设备ID后，可直接使用接口直接配置页面。
	 * 若设备配置多个页面，则随机出现页面信息。一个设备最多可配置30个关联页面。
	 * 
	 * @param deviceIdentifier 设备信息
	 * @param page_ids 待关联的页面列表
	 * @return
	 */
	public static ApiResult bindPage(DeviceIdentifier deviceIdentifier, int... pageIds) {
		String url = bindPageUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("device_identifier", deviceIdentifier);
		data.put("page_ids", pageIds);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String relationSearchUrl = "https://api.weixin.qq.com/shakearound/relation/search?access_token=";
	
	/**
	 * 查询设备与页面的关联关系。
	 * 提供两种查询方式，可指定页面ID分页查询该页面所关联的所有的设备信息；
	 * 也可根据设备ID或完整的UUID、Major、Minor查询该设备所关联的所有页面信息。
	 * @param deviceIdentifier
	 * @param type
	 * @return
	 */
	public static ApiResult searchRelation(DeviceIdentifier deviceIdentifier, int type) {
		String url = relationSearchUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("device_identifier", deviceIdentifier);
		data.put("type", type);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String groupAddUrl = "https://api.weixin.qq.com/shakearound/device/group/add?access_token=";
	
	/**
	 * 新建设备分组，每个帐号下最多只有1000个分组。
	 * @param groupName
	 * @return
	 */
	public static ApiResult addGroup(String groupName) {
		String url = groupAddUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> groupData = new HashMap<String, Object>();
		groupData.put("group_name", groupName);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(groupData));
		return new ApiResult(jsonResult);
	}
	
	private static String groupUpdateUrl = "https://api.weixin.qq.com/shakearound/device/group/update?access_token=";
	
	/**
	 * 编辑设备分组信息，目前只能修改分组名。
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	public static ApiResult updateGroup(int groupId, String groupName) {
		String url = groupUpdateUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> groupData = new HashMap<String, Object>();
		groupData.put("group_id", groupId);
		groupData.put("group_name", groupName);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(groupData));
		return new ApiResult(jsonResult);
	}
	
	private static String groupDeleteUrl = "https://api.weixin.qq.com/shakearound/device/group/delete?access_token=";
	
	/**
	 * 删除设备分组，若分组中还存在设备，则不能删除成功。需把设备移除以后，才能删除。
	 * @param groupId
	 * @return
	 */
	public static ApiResult deleteGroup(int groupId) {
		String url = groupDeleteUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> groupData = new HashMap<String, Object>();
		groupData.put("group_id", groupId);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(groupData));
		return new ApiResult(jsonResult);
	}
	
	private static String groupGetListUrl = "https://api.weixin.qq.com/shakearound/device/group/getlist?access_token=";
	
	/**
	 * 查询账号下所有的分组。
	 * @param begin 分组列表的起始索引值
	 * @param count 待查询的分组数量，不能超过1000个
	 * @return
	 */
	public static ApiResult getGroupList(int begin, int count) {
		String url = groupGetListUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> groupData = new HashMap<String, Object>();
		groupData.put("begin", begin);
		groupData.put("count", count);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(groupData));
		return new ApiResult(jsonResult);
	}
	
	private static String groupGetDetailUrl = "https://api.weixin.qq.com/shakearound/device/group/getdetail?access_token=";
	
	/**
	 * 查询分组详情，包括分组名，分组id，分组里的设备列表。
	 * @param groupId 分组唯一标识，全局唯一
	 * @param begin 分组列表的起始索引值
	 * @param count 待查询的分组数量，不能超过1000个
	 * @return
	 */
	public static ApiResult getGroupDetail(int groupId, int begin, int count) {
		String url = groupGetDetailUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> groupData = new HashMap<String, Object>();
		groupData.put("group_id", groupId);
		groupData.put("begin", begin);
		groupData.put("count", count);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(groupData));
		return new ApiResult(jsonResult);
	}
	
	private static String groupAddDeviceUrl = "https://api.weixin.qq.com/shakearound/device/group/adddevice?access_token=";
	
	/**
	 * 添加设备到分组，每个分组能够持有的设备上限为10000，并且每次添加操作的添加上限为1000。只有在摇周边申请的设备才能添加到分组。
	 * @param groupId 分组唯一标识，全局唯一
	 * @param List<DeviceIdentifier> deviceIdentifierList 设备信息
	 * @return
	 */
	public static ApiResult addDeviceToGroup(int groupId, List<DeviceIdentifier> deviceIdentifierList) {
		String url = groupAddDeviceUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> groupData = new HashMap<String, Object>();
		groupData.put("group_id", groupId);
		groupData.put("device_identifiers", deviceIdentifierList);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(groupData));
		return new ApiResult(jsonResult);
	}
	
	private static String groupDeleteDeviceUrl = "https://api.weixin.qq.com/shakearound/device/group/deletedevice?access_token=";
	
	/**
	 * 从分组中移除设备，每次删除操作的上限为1000。
	 * @param groupId 分组唯一标识，全局唯一
	 * @param List<DeviceIdentifier> deviceIdentifierList 设备信息
	 * @return
	 */
	public static ApiResult deleteDeviceFromGroup(int groupId, List<DeviceIdentifier> deviceIdentifierList) {
		String url = groupDeleteDeviceUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> groupData = new HashMap<String, Object>();
		groupData.put("group_id", groupId);
		groupData.put("device_identifiers", deviceIdentifierList);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(groupData));
		return new ApiResult(jsonResult);
	}
	
}
