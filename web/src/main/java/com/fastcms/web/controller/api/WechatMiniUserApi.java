/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastcms.web.controller.api;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.User;
import com.fastcms.entity.UserOpenid;
import com.fastcms.service.IUserService;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序用户
 * @author： wjun_java@163.com
 * @date： 2021/6/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/wechat/user")
public class WechatMiniUserApi {

	@Autowired
	private WxMaService wxService;

	@Autowired
	private WxSessionManager wxSessionManager;

	@Autowired
	private IUserService userService;

	private String jwtSecret;

	/**
	 * code2session
	 * @param code code
	 * @return
	 */
	@GetMapping("code2session")
	public Object code2Session(String code) {

		if(StringUtils.isBlank(code)) {
			return RestResultUtils.failed("code is null");
		}

		String sessionKey;
		String openId;
		String unionId;
		try {
			WxMaJscode2SessionResult result = wxService.getUserService().getSessionInfo(code);
			sessionKey = result.getSessionKey();
			openId = result.getOpenid();
			unionId = result.getUnionid();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}

		if (sessionKey == null || openId == null) {
			return RestResultUtils.failed("sessionKey is null or openId is null");
		}

		final String sessionId = StrUtils.uuid();
		WxSession session = wxSessionManager.getSession(sessionId);
		session.setAttribute("sessionKey", sessionKey);
		session.setAttribute("openId", openId);
		session.setAttribute("unionId", unionId);
		HashMap<Object, Object> result = new HashMap<>();
		result.put("sessionId", sessionId);
		return RestResultUtils.success(result);
	}

	/**
	 * 登录
	 * @param params
	 * @return
	 */
	@PostMapping("login")
	public RestResult<User> login(@RequestBody Map<String, Object> params) {

		String sessionId = (String) params.get("sessionId");
		WxSession session = wxSessionManager.getSession(sessionId);
		if(session == null) {
			return RestResultUtils.failed("登录失败:获取到空session");
		}

		final String sessionKey = (String) session.getAttribute("sessionKey");
		final String openId = (String) session.getAttribute("openId");
		final String unionId = (String) session.getAttribute("unionId");

		if(StrUtils.isBlank(openId)) {
			return RestResultUtils.failed("登录失败:获取不到用户的openId");
		}

		//不包括敏感信息的原始数据字符串，用于计算签名
		String rawData = (String) params.get("rawData");
		//签名：使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
		String signature = (String) params.get("signature");
		//包括敏感数据在内的完整用户信息的加密数据
		//具体加密方法在：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html#%E5%8A%A0%E5%AF%86%E6%95%B0%E6%8D%AE%E8%A7%A3%E5%AF%86%E7%AE%97%E6%B3%95
		String encryptedData = (String) params.get("encryptedData");
		//加密算法的初始向量
		String iv = (String) params.get("iv");
		if(!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
			return RestResultUtils.failed("登录失败:校验用户信息失败");
		}

		WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
		userInfo.setOpenId(openId);
		userInfo.setUnionId(unionId);

		try {
			User user = userService.saveUserOfOpenid(userInfo.getOpenId(), userInfo.getUnionId(), userInfo.getNickName(), userInfo.getAvatarUrl(), UserOpenid.TYPE_WECHAT_MINI);
			return RestResultUtils.success(user);
		} catch (Exception e) {
			e.printStackTrace();
			return RestResultUtils.failed("登录失败:user is null");
		}
	}

	/**
	 * 取用户手机号码
	 * @param params
	 * @return
	 */
	@PostMapping("getUserPhone")
	public RestResult<String> getUserPhone(@RequestBody Map<String, Object> params) {
		String sessionId = (String) params.get("sessionId");
		if(StrUtils.isBlank(sessionId)) {
			return RestResultUtils.failed("获取手机号:获取到空session");
		}
		WxSession session = wxSessionManager.getSession(sessionId);
		if(session == null) {
			return RestResultUtils.failed("获取手机号:获取到空session");
		}
		final String sessionKey = (String) session.getAttribute("sessionKey");
		String encryptedData = (String) params.get("encryptedData");
		String iv = (String) params.get("iv");
		WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
		if(wxMaPhoneNumberInfo == null || StrUtils.isBlank(wxMaPhoneNumberInfo.getPhoneNumber())) {
			return RestResultUtils.failed("获取手机号码失败");
		}
		User user = userService.getById(null);
		user.setMobile(wxMaPhoneNumberInfo.getPhoneNumber());
		userService.updateById(user);
		return RestResultUtils.success(wxMaPhoneNumberInfo.getPhoneNumber());
	}

	/**
	 * 手机号码授权登录
	 * @Description
	 * 用户直接通过手机号码授权登录
	 * 	 * 注意:用户通过手机号码授权登录，后台是获取不到用户的昵称跟头像的，需要手动设置
	 * 	 * 小程序前端可以通过WXML标签直接显示用户头像昵称
	 * 	 * <open-data type="userAvatarUrl"></open-data>
	 * 	 * <open-data type="userNickName"></open-data>
	 * @param params
	 * @return
	 */
	@PostMapping("loginByPhone")
	public Object loginByPhone(@RequestBody Map<String, Object> params) {
		String sessionId = (String) params.get("sessionId");
		if(StrUtils.isBlank(sessionId)) {
			return RestResultUtils.failed("获取手机号:获取到空session");
		}
		WxSession session = wxSessionManager.getSession(sessionId);
		if(session == null) {
			return RestResultUtils.failed("获取手机号:获取到空session");
		}

		final String openId = (String) session.getAttribute("openId");
		if(StrUtils.isBlank(openId)) {
			return RestResultUtils.failed("登录失败:获取不到用户的openId");
		}
		final String unionId = (String) session.getAttribute("unionId");
		final String sessionKey = (String) session.getAttribute("sessionKey");
		String encryptedData = (String) params.get("encryptedData");
		String iv = (String) params.get("iv");
		WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
		if(wxMaPhoneNumberInfo == null || StrUtils.isBlank(wxMaPhoneNumberInfo.getPhoneNumber())) {
			return RestResultUtils.failed("获取手机号码失败");
		}
		try {
			User user = userService.saveUserOfOpenidAndPhone(openId, unionId, wxMaPhoneNumberInfo.getPurePhoneNumber(), UserOpenid.TYPE_WECHAT_MINI);
			return RestResultUtils.success(user);
		} catch (Exception e) {
			return RestResultUtils.failed("登录失败:user is null");
		}

	}

}
