package com.dbumama.market.service.provider;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Weapp;
import com.dbumama.market.service.api.user.UserException;
import com.dbumama.market.service.api.user.UserService;
import com.dbumama.market.service.api.user.WeappLoginResultDto;
import com.dbumama.market.service.api.user.WeappUserCheckParamDto;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.utils.CommonUtil;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.encrypt.SHA1;
import com.weixin.sdk.kit.WxSdkPropKit;

@Service("userService")
public class UserServiceImpl extends AbstractServiceImpl implements UserService{
	
	//wx23fa20cdbb6dd2b6
	//0c0ea26be4ffa590a65892a173b0d078
	//https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
	final String login_url = "https://api.weixin.qq.com/sns/jscode2session";
	private static final BuyerUser buyerUserdao = new BuyerUser().dao();
	private static final Weapp weappDao = new Weapp().dao();
	
	@Override
	public WeappLoginResultDto loginWeapp(String appId, String code) throws UserException {
		if(StrKit.isBlank(appId) || StrKit.isBlank(code)) throw new UserException("loginWeapp 缺少完整参数");
		//根据code拿用户授权token
    	String url = login_url+"?appid="+WxSdkPropKit.get("weapp_id")+"&secret="+WxSdkPropKit.get("weapp_secret")
    			+"&js_code="+code+"&grant_type=authorization_code";
    	ApiResult loginRes = null;
    	try {
    		loginRes = new ApiResult(HttpKit.get(url));
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
         
        if(loginRes.getErrorCode() != null){
        	throw new UserException(loginRes.getErrorMsg());
        }
        
        //再请求一次comp accessToken，如果还获取不到token就报错
        if(loginRes.getErrorCode() != null)
        	throw new UserException("通过code获取accessToken接口：" +loginRes.getErrorMsg());
        
        WeappLoginResultDto resDto = new WeappLoginResultDto();
        resDto.setOpenid(loginRes.getStr("openid"));
        resDto.setSessionKey(loginRes.getStr("session_key"));
		return resDto;
	}

	@Override
	public BuyerUser check(WeappUserCheckParamDto userCheckParam) throws UserException {
		if(userCheckParam == null 
				|| StrKit.isBlank(userCheckParam.getSessionKey())
				|| StrKit.isBlank(userCheckParam.getSignature())
				|| StrKit.isBlank(userCheckParam.getRawData())
				|| StrKit.isBlank(userCheckParam.getEncryptedData())
				|| StrKit.isBlank(userCheckParam.getIv()))
			throw new UserException("校验用户合法性，请传入完整参数");
		final String sign2 = SHA1.sha1(userCheckParam.getRawData()+userCheckParam.getSessionKey());
		if(!sign2.equals(userCheckParam.getSignature())){
			throw new UserException("签名错误");
		}
		
		Weapp weapp = weappDao.findFirst("select * from " + Weapp.table);
		if(weapp == null) throw new UserException("未配置小程序相关信息，请到管理后台设置》小程序中配置");
		
		//解密用户数据，并存储到数据库
		Base64 base64 = new Base64();
		byte [] content = base64.decode(userCheckParam.getEncryptedData());
		byte [] ivbyte = base64.decode(userCheckParam.getIv());
		byte [] sessionKeyByte = base64.decode(userCheckParam.getSessionKey());
		
		Security.addProvider(new BouncyCastleProvider());
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(sessionKeyByte, "AES");

			AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
	        params.init(new IvParameterSpec(ivbyte));
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);// 初始化
            byte[] resultByte = cipher.doFinal(content);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                JSONObject json = (JSONObject) JSONObject.parse(result);
                JSONObject watermarkJson = json.getJSONObject("watermark");
                if(watermarkJson == null 
                		|| watermarkJson.getString("appid")==null 
                		|| !watermarkJson.getString("appid").equals(weapp.getWeappId())){
                	throw new UserException("非法用户数据");
                }
                //save or update into db
                String openId = json.getString("openId");
                BuyerUser buyer = buyerUserdao.findFirst("select * from " + BuyerUser.table + " where open_id=?", openId);
                if(buyer == null){
                	buyer = new BuyerUser();
                	buyer.setSellerId(weapp.getSellerId());
                	buyer.setOpenId(openId);
                	buyer.setCreated(new Date());
                	buyer.setUserOrigin(2);//二表示从小程序关注而来的用户
                }
                buyer.setNickname(json.getString("nickName"));
                buyer.setHeadimgurl(json.getString("avatarUrl"));
                buyer.setSex(json.getInteger("gender"));
                buyer.setLanguage(json.getString("language"));
                buyer.setCity(json.getString("city"));
                buyer.setProvince(json.getString("province"));
                buyer.setCountry(json.getString("country"));
                buyer.setUnionid(json.getString("unionid"));
                buyer.setUpdated(new Date());
                if(buyer.getId() == null){
                	buyer.save();
                }else{
                	buyer.update();
                }
                return buyer;
            }
		}  catch (Exception e) {
			throw new UserException(e.getMessage());
		}
		return null;	
	}

	@Override
	public BuyerUser findById(Long userId) {
		return buyerUserdao.findById(userId);
	}

	
	/**
	 * 用户取消关注返回的用户信息json {"subscribe":0,"openid":"oQ774wnoZjqJt4UdAXusjT9WBvgI","tagid_list":[]}
	 * 用户关注返回用户信息json{"subscribe":1,"openid":"oQ774wnoZjqJt4UdAXusjT9WBvgI","nickname":"做个好农民","sex":1,
	 * "language":"zh_CN","city":"广州","province":"广东","country":"中国",
	 * "headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/NLOzukWr2a1LaMmJWASVHPZiayia0OaeibKY9ICYqibH08RP4O9qXldeSNwbwJic0wu1Gc8ScT5EoyDazI8YnyfRYc9MdvXXOSp8R\/0",
	 * "subscribe_time":1501576685,"remark":"","groupid":0,"tagid_list":[]}
	 * 用户通过商城分享的链接通过auth2.0授权后进入商城，获取的用户json
	 * //{"openid":"oQ774wnoZjqJt4UdAXusjT9WBvgI","nickname":"做个好农民","sex":1,
       //"language":"zh_CN","city":"广州","province":"广东","country":"中国",
       //"headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/NLOzukWr2a1LaMmJWASVHPZiayia0OaeibKY9ICYqibH08RP4O9qXldeSNwbwJic0wu1Gc8ScT5EoyDazI8YnyfRYc9MdvXXOSp8R\/0",
       //"privilege":[]}
	 */
	@Override
	public BuyerUser saveOrUpdate(Long sellerId, String openId, String userInfoJson,  String accessIp) throws UserException {
		JSONObject userInfo = JSONObject.parseObject(userInfoJson);
    	BuyerUser member = buyerUserdao.findFirst("select * from t_buyer_user where open_id=? ", openId);
        if (member == null) {
        	member = new BuyerUser().setCreated(new Date()).setUpdated(new Date())
        			.setActive(1)
        			.setSellerId(sellerId)
        			.setUserOrigin(1)  ////1表示从公众账号关注而来的用户
        			.setEmail("").setOpenId(openId);
        } 
        
        member.setAccessIp(accessIp).setPassword("")
        .setNickname(CommonUtil.filterEmoji(userInfo.getString("nickname")))
        .setHeadimgurl(userInfo.getString("headimgurl"))
		.setSex(userInfo.getInteger("sex")).setCity(userInfo.getString("city"))
		.setCountry(userInfo.getString("country")).setProvince(userInfo.getString("province"))
		.setLanguage(userInfo.getString("language")).setLastLoginTime(new Date());

		if(userInfo.containsKey("subscribe")){
			member.setSubscribe(userInfo.getInteger("subscribe") == null ? 0 :userInfo.getInteger("subscribe"));
		}
		if(userInfo.containsKey("subscribe_time")){
			member.setSubscribeTime(userInfo.getLong("subscribe_time") == null ? new Date() : new Date(userInfo.getLong("subscribe_time")));
		}
		if(userInfo.containsKey("unionid")){
			member.setUnionid(userInfo.getString("unionid"));
		}
		if(userInfo.containsKey("remark")){
			member.setRemark(userInfo.getString("remark"));
		}
		if(userInfo.containsKey("groupid")){
			member.setGroupid(userInfo.getInteger("groupid"));
		}
        
		if(member.getId() == null){
			member.setCreated(new Date()).setUpdated(new Date()).save();
		}else{
			member.setUpdated(new Date()).update();
		}
		
		return member;
	}

	@Override
	public BuyerUser findByOpenId(String openId) {
		return buyerUserdao.findFirst("select * from "+BuyerUser.table+" where open_id=?", openId);
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.user.UserService#cancelSubscribe(java.lang.String)
	 */
	@Override
	public BuyerUser cancelSubscribe(String openId) {
		BuyerUser user = buyerUserdao.findFirst("select * from " + BuyerUser.table + " where open_id=?", openId);
		user.setSubscribe(0);
		user.update();
		return user;
	}
	
}
