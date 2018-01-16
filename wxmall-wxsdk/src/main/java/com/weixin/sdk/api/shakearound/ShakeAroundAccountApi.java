package com.weixin.sdk.api.shakearound;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.weixin.sdk.api.AccessTokenApi;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.utils.HttpUtils;
import com.weixin.sdk.utils.JsonUtils;

/**
 * 申请开通摇一摇周边
 * @author L.cm
 */
public class ShakeAroundAccountApi {
	
	private static String registerUrl = "https://api.weixin.qq.com/shakearound/account/register?access_token=";
	
	/**
	 * 申请开通摇一摇周边功能。成功提交申请请求后，工作人员会在三个工作日内完成审核。
	 * 若审核不通过，可以重新提交申请请求。
	 * 若是审核中，请耐心等待工作人员审核，在审核中状态不能再提交申请请求。
	 * 
	 * @param name 联系人姓名，不超过20汉字或40个英文字母
	 * @param phoneNumber 联系人电话
	 * @param email 联系人邮箱
	 * @param industryId 平台定义的行业代号，具体请查看:http://3gimg.qq.com/shake_nearby/Qualificationdocuments.html
	 * @param applyReason 申请理由，可为空，不超过250汉字或500个英文字母
	 * @param qualificationCertUrls 相关资质文件的图片url，图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，返回的图片URL再配置在此处；当不需要资质文件时，数组内可以不填写url
	 * @return
	 */
	public static ApiResult register(String name, String phoneNumber, String email,
			String industryId, String applyReason, String... qualificationCertUrls
			) {
		String url = registerUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", name);
		data.put("phone_number", phoneNumber);
		data.put("email", email);
		data.put("industry_id", industryId);
		if (StringUtils.isNotBlank(applyReason)) {
			data.put("apply_reason", applyReason);
		}
		data.put("qualification_cert_urls", qualificationCertUrls);
		
		String jsonResult = HttpUtils.post(url, JsonUtils.toJson(data));
		return new ApiResult(jsonResult);
	}
	
	private static String auditStatusUrl = "https://api.weixin.qq.com/shakearound/account/auditstatus?access_token=";
	
	/**
	 * 查询已经提交的开通摇一摇周边功能申请的审核状态。在申请提交后，工作人员会在三个工作日内完成审核。
	 * @return
	 */
	public static ApiResult getAuditStatus() {
		String url = auditStatusUrl + AccessTokenApi.getAccessTokenStr();
		
		String jsonResult = HttpUtils.get(url);
		return new ApiResult(jsonResult);
	}
}
