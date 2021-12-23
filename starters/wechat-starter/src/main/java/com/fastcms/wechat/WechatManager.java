package com.fastcms.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface WechatManager {

	/**
	 * 获取微信服务类
	 * @return
	 */
	WxMaService getWxService();

}
