package com.weixin.sdk.api.shakearound;

import java.io.File;

import com.weixin.sdk.api.AccessTokenApi;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.utils.HttpUtils;

/**
 * 上传图片素材
 * @author L.cm
 */
public class ShakeAroundMaterialApi {
	
	/**
	 * Icon：摇一摇页面展示的icon图；License：申请开通摇一摇周边功能时需上传的资质文件；若不传type，则默认type=icon
	 */
	public static enum MaterialType {
		Icon, 
		License
	}
	
	private static String materialAddUrl = "https://api.weixin.qq.com/shakearound/material/add?access_token=";
	
	/**
	 * 上传在摇一摇功能中需使用到的图片素材，素材保存在微信侧服务器上。图片格式限定为：jpg,jpeg,png,gif。
	 * 若图片为在摇一摇页面展示的图片，则其素材为icon类型的图片，图片大小建议120px*120 px，限制不超过200 px *200 px，图片需为正方形。
	 * 若图片为申请开通摇一摇周边功能需要上传的资质文件图片，则其素材为license类型的图片，图片的文件大小不超过2MB，尺寸不限，形状不限。
	 * 
	 * @param file 文件
	 * @param type Icon：摇一摇页面展示的icon图；License：申请开通摇一摇周边功能时需上传的资质文件；若不传type，则默认type=icon
	 * @return
	 */
	public static ApiResult addMaterial(File file, MaterialType materialType) {
		String url = materialAddUrl + AccessTokenApi.getAccessTokenStr() + "&type=" + materialType.name();
		
		String jsonResult = HttpUtils.upload(url, file, null);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 上传在摇一摇功能中需使用到的图片素材，素材保存在微信侧服务器上。图片格式限定为：jpg,jpeg,png,gif。
	 * 若图片为在摇一摇页面展示的图片，则其素材为icon类型的图片，图片大小建议120px*120 px，限制不超过200 px *200 px，图片需为正方形。
	 * 若图片为申请开通摇一摇周边功能需要上传的资质文件图片，则其素材为license类型的图片，图片的文件大小不超过2MB，尺寸不限，形状不限。
	 * 
	 * @param file 文件
	 * @return
	 */
	public static ApiResult addMaterial(File file) {
		return addMaterial(file, MaterialType.Icon);
	}
	
}
