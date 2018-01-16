package com.weixin.sdk.api;

import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.utils.HttpUtils;

/**
 * card API
 * @author drs
 *
 */
public class CardApi {
	
	//获取卡券详情接口
	private static String getCard = "https://api.weixin.qq.com/card/get?access_token=";
	
	//创建卡券接口
	private static String createCard = "https://api.weixin.qq.com/card/create?access_token=";
	
	//更新卡券接口
	private static String updateCard = "https://api.weixin.qq.com/card/update?access_token=";
	
	//批量获取卡券接口
	private static String batchget = "https://api.weixin.qq.com/card/batchget?access_token=";
	
	//激活会员卡
	private static String memberCard = "https://api.weixin.qq.com/card/membercard/activate?access_token=";
	
	//会员余额，积分变动，调用该接口进行更新
	private static String updateUser = "https://api.weixin.qq.com/card/membercard/updateuser?access_token=";
	
	//拉去会员的会员卡信息，通过该接口获取会员某张会员卡的积分，余额信息
	private static String memberCardInfo = "https://api.weixin.qq.com/card/membercard/userinfo/get?access_token=";
	
	/**
	 * {
		  "offset": 0,
		  "count": 10, 
		  "status_list": ["CARD_STATUS_VERIFY_OK", "CARD_STATUS_DISPATCH"]
		}
	 * 批量获取卡券
	 * @param accessToken
	 * @param authAppId
	 * @param jsonStr
	 * @return
	 */
	public static ApiResult batchGetCards(String jsonStr){
		String jsonResult = HttpUtils.post(batchget + AccessTokenApi.getAccessTokenStr(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * {
		  "card_id":"pFS7Fjg8kV1IdDz01r4SQwMkuCKc"
	   }
	 * 获取卡券详情
	 * @param accessToken
	 * @param authAppId
	 * @param jsonStr
	 * @return
	 */
	public static ApiResult getCard(String jsonStr){
		String jsonResult = HttpUtils.post(getCard + AccessTokenApi.getAccessTokenStr(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 创建卡券
	 * @param accessToken
	 * @param authAppId
	 * @param jsonStr
	 * @return
	 */
	public static ApiResult createCard(String jsonStr) {
		String jsonResult = HttpUtils.post(createCard + AccessTokenApi.getAccessTokenStr(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * {
       "card_id":"ph_gmt7cUVrlRk8swPwx7aDyF-pg",
       "member_card": {        //填写该cardid相应的卡券类型（小写）。
               "base_info": {
                   "logo_url": "http:\/\/www.supadmin.cn\/uploads\/allimg\/120216\/1_120216214725_1.jpg",
                   "color": "Color010",
                   "notice": "使用时向服务员出示此券",
                   "service_phone": "020-88888888",
                   "description": "不可与其他优惠同享\n如需团购券发票，请在消费时向商户提出"
                   "location_id_list" : [123, 12321, 345345]
               },
                  "bonus_cleared": "aaaaaaaaaaaaaa",
                  "bonus_rules": "aaaaaaaaaaaaaa",
                  "prerogative": ""
       		}
		}
	 * 更新卡券
	 * @param accessToken
	 * @param authAppId
	 * @param jsonStr
	 * @return
	 */
	public static ApiResult updateCard(String jsonStr) {
		String jsonResult = HttpUtils.post(updateCard + AccessTokenApi.getAccessTokenStr(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 激活会员卡
	 * @param accessToken
	 * @param authAppId
	 * @param jsonStr
	 * @return
	 * {
	    "init_bonus": 100,
	    "init_bonus_record":"旧积分同步",
	    "init_balance": 200,
	    "membership_number": "AAA00000001",
	    "code": "12312313",
	    "card_id": "xxxx_card_id",
	    "background_pic_url": "https://mmbiz.qlogo.cn/mmbiz/0?wx_fmt=jpeg",
	    "init_custom_field_value1": "xxxxx"
	}
	 * 
	 */
	public static ApiResult memberCard(String jsonStr){
		String jsonResult = HttpUtils.post(memberCard + AccessTokenApi.getAccessTokenStr(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * {   "card_id": "pbLatjtZ7v1BG_ZnTjbW85GYc_E8",   "code": "916679873278"}
	 * 通过该接口获取会员卡余额，积分信息
	 * @param accessToken
	 * @param authAppId
	 * @param jsonStr
	 * @return
	 */
	public static ApiResult memberCardInfo(String jsonStr){
		String jsonResult = HttpUtils.post(memberCardInfo + AccessTokenApi.getAccessTokenStr(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 会员会员卡积分，余额变动，调用改接口进行更新
	 * @param accessToken
	 * @param authAppId
	 * @param jsonStr
	 * @return
	 * {
	    "code": "179011264953",
	    "card_id": "p1Pj9jr90_SQRaVqYI239Ka1erkI",
	    "background_pic_url": "https://mmbiz.qlogo.cn/mmbiz/0?wx_fmt=jpeg",
	    "record_bonus": "消费30元，获得3积分",
	    "bonus": 3000,
	    "add_bonus": 30,
	    "balance": 3000,
	    "add_balance": -30,
	    "record_balance": "购买焦糖玛琪朵一杯，扣除金额30元。",
	    "custom_field_value1": "xxxxx"，
	    "custom_field_value2": "xxxxx"，
	    "notify_optional": {
	        "is_notify_bonus": true,
	        "is_notify_balance": true,
	        "is_notify_custom_field1":true
	    }
	}
	 */
	public static ApiResult updateUser(String jsonStr){
		String jsonResult = HttpUtils.post(updateUser + AccessTokenApi.getAccessTokenStr(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
}
