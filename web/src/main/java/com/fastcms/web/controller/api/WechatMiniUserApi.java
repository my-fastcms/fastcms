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

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.entity.User;
import com.fastcms.entity.UserOpenid;
import com.fastcms.service.IUserService;
import com.fastcms.web.security.JwtTokenManager;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序登录授权
 * @author： wjun_java@163.com
 * @date： 2021/6/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/wechat/miniapp/user")
public class WechatMiniUserApi {

	@Autowired
	private WxMaService wxService;

	@Autowired
	private WxSessionManager wxSessionManager;

	@Autowired
	private IUserService userService;

	@Autowired
	private JwtTokenManager tokenManager;

	@Autowired
	private WxMaQrcodeService wxMaQrcodeService;

	static final String MINIAPP_SESSIONKEY = "sessionKey";

	static final String MINIAPP_OPENID = "openId";

	static final String MINIAPP_UNIONID = "unionId";

	static final String MINIAPP_SESSIONID = "sessionId";

	static final String MINIAPP_ENCRYPTED_DATA = "encryptedData";

	static final String MINIAPP_RAW_DATA = "rawData";

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
		session.setAttribute(MINIAPP_SESSIONKEY, sessionKey);
		session.setAttribute(MINIAPP_OPENID, openId);
		session.setAttribute(MINIAPP_UNIONID, unionId);
		HashMap<Object, Object> result = new HashMap<>();
		result.put(MINIAPP_SESSIONID, sessionId);
		result.put(MINIAPP_OPENID, openId);
		return RestResultUtils.success(result);
	}

	/**
	 * 登录
	 * @param params
	 * @return
	 */
	@PostMapping("login")
	public RestResult<String> login(@RequestBody Map<String, Object> params) {

		WxSession session = getSession(params);
		if(session == null) {
			return RestResultUtils.failed("登录失败:获取到空session");
		}

		final String sessionKey = (String) session.getAttribute(MINIAPP_SESSIONKEY);
		final String openId = (String) session.getAttribute(MINIAPP_OPENID);
		final String unionId = (String) session.getAttribute(MINIAPP_UNIONID);

		if(StrUtils.isBlank(openId)) {
			return RestResultUtils.failed("登录失败:获取不到用户的openId");
		}

		//不包括敏感信息的原始数据字符串，用于计算签名
		String rawData = (String) params.get(MINIAPP_RAW_DATA);
		//签名：使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
		String signature = (String) params.get("signature");
		//包括敏感数据在内的完整用户信息的加密数据
		//具体加密方法在：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html#%E5%8A%A0%E5%AF%86%E6%95%B0%E6%8D%AE%E8%A7%A3%E5%AF%86%E7%AE%97%E6%B3%95
		//加密算法的初始向量
		String iv = (String) params.get("iv");
		if(!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
			return RestResultUtils.failed("登录失败:校验用户信息失败");
		}

		WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, getEncryptedData(params), iv);
		userInfo.setOpenId(openId);
		userInfo.setUnionId(unionId);

		try {
			User user = userService.saveWxMaUserInfo(userInfo);
			return RestResultUtils.success(tokenManager.createToken(user.getId(), user.getUserName()));
		} catch (FastcmsException e) {
			e.printStackTrace();
			return RestResultUtils.failed("登录失败:user is null");
		}

	}

	/**
	 * 获取用户手机号码
	 * @param code
	 * @return
	 */
	@GetMapping("phone")
	public RestResult<String> getUserPhone(@RequestParam("code") String code) {
		WxMaPhoneNumberInfo wxMaPhoneNumberInfo;
		try {
			wxMaPhoneNumberInfo = wxService.getUserService().getNewPhoneNoInfo(code);
		} catch (WxErrorException e) {
			e.printStackTrace();
			return RestResultUtils.failed("获取手机号码失败");
		}
		if(wxMaPhoneNumberInfo == null || StrUtils.isBlank(wxMaPhoneNumberInfo.getPhoneNumber())) {
			return RestResultUtils.failed("获取手机号码失败");
		}
		User user = userService.getById(AuthUtils.getUserId());
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
	@PostMapping("login/phone")
	public Object loginByPhone(@RequestBody Map<String, Object> params) {
		WxSession session = getSession(params);
		if(session == null) {
			return RestResultUtils.failed("获取手机号:获取到空session");
		}

		final String openId = (String) session.getAttribute(MINIAPP_OPENID);
		if(StrUtils.isBlank(openId)) {
			return RestResultUtils.failed("登录失败:获取不到用户的openId");
		}
		final String unionId = (String) session.getAttribute(MINIAPP_UNIONID);
		final String sessionKey = (String) session.getAttribute(MINIAPP_SESSIONKEY);
		String encryptedData = getEncryptedData(params);
		String iv = (String) params.get("iv");
		WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
		if(wxMaPhoneNumberInfo == null || StrUtils.isBlank(wxMaPhoneNumberInfo.getPhoneNumber())) {
			return RestResultUtils.failed("获取手机号码失败");
		}

		try {
			User user = userService.saveUser(openId, unionId, wxMaPhoneNumberInfo.getPurePhoneNumber(), UserOpenid.TYPE_WECHAT_MINI);
			return RestResultUtils.success(tokenManager.createToken(user.getId(), user.getUserName()));
		} catch (FastcmsException e) {
			e.printStackTrace();
			return RestResultUtils.failed("登录失败:user is null");
		}

	}

	/**
	 * 获取小程序二维码
	 * @param scene
	 * @param envVersion | release
	 * @param path
	 * @return
	 * @throws WxErrorException
	 */
	@GetMapping("getWxaQrCode")
	public Object getWxaQrCode(@RequestParam(value = "scene", required = false) String scene,
							   @RequestParam(value = "path", required = false, defaultValue = "pages/index/index") String path,
							   @RequestParam(value = "envVersion") String envVersion) throws FastcmsException {
		if (StringUtils.isBlank(scene)) {
			scene = String.valueOf(AuthUtils.getUserId());
		}

		try {
			byte[] qrCodeBytes = wxMaQrcodeService.createWxaCodeUnlimitBytes(scene, path, true, envVersion, 430, true, new WxMaCodeLineColor("0", "0", "0"), false);
			return RestResultUtils.success("data:image/png;base64," + Base64.getEncoder().encodeToString(qrCodeBytes));
		} catch (Exception e) {
			throw new FastcmsException(e.getMessage());
		}

	}

	WxSession getSession(Map<String, Object> params) {
		String sessionId = (String) params.get(MINIAPP_SESSIONID);
		if(StrUtils.isBlank(sessionId)) {
			return null;
		}
		return wxSessionManager.getSession(sessionId);
	}

	String getEncryptedData(Map<String, Object> params) {
		return (String) params.get(MINIAPP_ENCRYPTED_DATA);
	}

}
