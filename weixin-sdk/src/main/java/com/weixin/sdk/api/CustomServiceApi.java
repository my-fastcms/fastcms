/**
 * Copyright (c) 2011-2015, Unas 小强哥 (unas@qq.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.weixin.sdk.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weixin.sdk.utils.HttpUtils;
import com.weixin.sdk.utils.JsonUtils;

/**
 * 多客服功能</br>
 * 仅支持获取客服聊天记录接口，其他功能可以使用微信官方的多客服客户端软件来完成。
 * 
 * 客服接口：http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html
 */
public class CustomServiceApi {

    private static String getRecordUrl = "https://api.weixin.qq.com/customservice/msgrecord/getrecord?access_token=";

    /**
     * 获取客服聊天记录
     */
    public static ApiResult getRecord(String jsonStr) {
        String jsonResult = HttpUtils.post(getRecordUrl + AccessTokenApi.getAccessTokenStr(), jsonStr);
        return new ApiResult(jsonResult);
    }

    private static String addKfAccountUrl = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=";
    
    /**
     * 添加客服帐号
     * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
     * @param nickname 客服昵称，最长6个汉字或12个英文字符
     * @param password 客服账号登录密码，格式为密码明文的32位加密MD5值。该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，则不必设置密码
     * @return ApiResult
     */
    public static ApiResult addKfAccount(String kf_account, String nickname, String password) {
        String accessToken = AccessTokenApi.getAccessTokenStr();
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("kf_account", kf_account);
        params.put("nickname", nickname);
        params.put("password", password);
        
        String jsonResult = HttpUtils.post(addKfAccountUrl + accessToken, JsonUtils.toJson(params));
        return new ApiResult(jsonResult);
    }
    
    private static String updateKfAccountUrl = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=";
    
    /**
     * 修改客服帐号
     * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
     * @param nickname 客服昵称，最长6个汉字或12个英文字符
     * @param password 客服账号登录密码，格式为密码明文的32位加密MD5值。该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，则不必设置密码
     * @return ApiResult
     */
    public static ApiResult updateKfAccount(String kf_account, String nickname, String password) {
        String accessToken = AccessTokenApi.getAccessTokenStr();
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("kf_account", kf_account);
        params.put("nickname", nickname);
        params.put("password", password);
        
        String jsonResult = HttpUtils.post(updateKfAccountUrl + accessToken, JsonUtils.toJson(params));
        return new ApiResult(jsonResult);
    }
    
    private static String delKfAccountUrl = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=";
    
    /**
     * 删除客服帐号
     * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
     * @param nickname 客服昵称，最长6个汉字或12个英文字符
     * @param password 客服账号登录密码，格式为密码明文的32位加密MD5值。该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，则不必设置密码
     * @return ApiResult
     */
    public static ApiResult delKfAccount(String kf_account, String nickname, String password) {
        String accessToken = AccessTokenApi.getAccessTokenStr();
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("kf_account", kf_account);
        params.put("nickname", nickname);
        params.put("password", password);
        
        String jsonResult = HttpUtils.post(delKfAccountUrl + accessToken, JsonUtils.toJson(params));
        return new ApiResult(jsonResult);
    }
    
    private static String uploadKfAccountHeadImgUrl = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=";
    
    /**
     * 设置客服帐号的头像
     * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
     * @param headImg 客服人员的头像，头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
     * @return
     */
    public static ApiResult uploadKfAccountHeadImg(String kf_account, File headImg) {
        String accessToken = AccessTokenApi.getAccessTokenStr();
        String url = uploadKfAccountHeadImgUrl + accessToken + "&kf_account=" + kf_account;
        String jsonResult = HttpUtils.upload(url, headImg, null);
        return new ApiResult(jsonResult);
    }
    
    private static String getKfListUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=";
    
    /**
     * 获取所有客服账号
     * @return ApiResult
     */
    public static ApiResult getKfList() {
        String accessToken = AccessTokenApi.getAccessTokenStr();
        String jsonResult = HttpUtils.get(getKfListUrl + accessToken);
        return new ApiResult(jsonResult);
    }
    
    private static String getOnlineKfListUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=";
    
    public static ApiResult getKfOnlineList() {
    	String accessToken = AccessTokenApi.getAccessTokenStr();
    	String jsonResult = HttpUtils.get(getOnlineKfListUrl + accessToken);
        return new ApiResult(jsonResult);
    }
    
    private static String customMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    /**
     * 发送客服消息
     * @param message
     * @return ApiResult
     */
    private static ApiResult sendMsg(Map<String, Object> message) {
        String accessToken = AccessTokenApi.getAccessTokenStr();
        String jsonResult = HttpUtils.post(customMessageUrl + accessToken, JsonUtils.toJson(message));
        return new ApiResult(jsonResult);
    }

    /**
     * <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>1348831860</CreateTime>
 <MsgType><![CDATA[text]]></MsgType>
 <Content><![CDATA[this is a test]]></Content>
 <MsgId>1234567890123456</MsgId>
 </xml>
     * 发送文本客服消息
     * @param openId
     * @param text
     */
    public static ApiResult sendText(String openId, String text) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "text");

        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("content", text);

        json.put("text", textObj);
        return sendMsg(json);
    }
    
    /**
     * 发送图片消息
     * @param openId
     * @param media_id
     * @return
     */
    public static ApiResult sendImage(String openId, String media_id) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "image");

        Map<String, Object> image = new HashMap<String, Object>();
        image.put("media_id", media_id);

        json.put("image", image);
        return sendMsg(json);
    }

    /**
     * 发送语言回复
     * @param openId
     * @param media_id
     * @return
     */
    public static ApiResult sendVoice(String openId, String media_id) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "voice");

        Map<String, Object> voice = new HashMap<String, Object>();
        voice.put("media_id", media_id);

        json.put("voice", voice);
        return sendMsg(json);
    }

    /**
     * 发送视频回复
     * @param openId
     * @param media_id
     * @param title
     * @param description
     * @return
     */
    public static ApiResult sendVideo(String openId, String media_id, String title, String description) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "video");

        Map<String, Object> video = new HashMap<String, Object>();
        video.put("media_id", media_id);
        video.put("title", title);
        video.put("description", description);

        json.put("video", video);
        return sendMsg(json);
    }

    /**
     * 发送音乐回复
     * @param openId
     * @param musicurl
     * @param hqmusicurl
     * @param thumb_media_id
     * @param title
     * @param description
     * @return
     */
    public static ApiResult sendMusic(String openId, String musicurl, String hqmusicurl, String thumb_media_id, String title, String description) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "music");

        Map<String, Object> music = new HashMap<String, Object>();
        music.put("musicurl", musicurl);
        music.put("hqmusicurl", hqmusicurl);
        music.put("thumb_media_id", thumb_media_id);
        music.put("title", title);
        music.put("description", description);

        json.put("music", music);
        return sendMsg(json);
    }

    /**
     * 发送图文回复
     * @param openId
     * @param articles
     * @return
     */
    public static ApiResult sendNews(String openId, List<Articles> articles) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "news");

        Map<String, Object> news = new HashMap<String, Object>();
        news.put("articles", articles);

        json.put("news", news);
        return sendMsg(json);
    }
    
    /**
 <xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>1351776360</CreateTime>
<MsgType><![CDATA[link]]></MsgType>
<Title><![CDATA[公众平台官网链接]]></Title>
<Description><![CDATA[公众平台官网链接]]></Description>
<Url><![CDATA[url]]></Url>
<MsgId>1234567890123456</MsgId>
</xml>
     * 发送链接消息
     * @param openId
     * @param text
     * @return
     */
    public static ApiResult sendLink(String openId, String title, String desc, String url) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "link");

        Map<String, Object> link = new HashMap<String, Object>();
        link.put("title", title);
        link.put("description", desc);
        link.put("url", url);
        json.put("link", link);
        return sendMsg(json);
    }

    /**
     * 客户消息图文封装和 `News` 又略微区别，无法公用
     */
    public static class Articles {
        private String title;
        private String description;
        private String url;
        private String picurl;

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getPicurl() {
            return picurl;
        }
        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }
    }

    /**
     * 发送卡券
     * @param openId
     * @param card_id
     * @param card_ext 详情及签名规则: http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.954-.E5.8D.A1.E5.88.B8.E6.89.A9.E5.B1.95.E5.AD.97.E6.AE.B5.E5.8F.8A.E7.AD.BE.E5.90.8D.E7.94.9F.E6.88.90.E7.AE.97.E6.B3.95
     * @return
     */
    public static ApiResult sendCoupon(String openId, String card_id, String card_ext) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "wxcard");

        Map<String, Object> wxcard = new HashMap<String, Object>();
        wxcard.put("card_id", card_id);
        wxcard.put("card_ext", card_ext);

        json.put("wxcard", wxcard);
        return sendMsg(json);
    }
    
    public static void main(String[] args) {
    	ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken("__my__nettoken__2016");
        ac.setAppId("wx0dd16298bc16ed63");
        ac.setAppSecret("043397fb77eca2508d3f53a123076cc0");

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(true);
        ac.setEncodingAesKey("EpSqUcAzCKOHbm3LxaJgaaQpJIrFeSTgaxakbl7lspy");
        
        ApiConfigKit.setThreadLocalApiConfig(ac);
        
        ApiResult apiResult = CustomServiceApi.sendLink("oQ774wrNfB4nJ8E-zBg2H8sshqps", "疯狂抽奖",
        		"疯狂抽奖中抽中了(100)元，你也快去试试吧，点击去抽奖", 
        		"http://m.dbumama.com/pay/luck");
        if(apiResult.isSucceed()){
        	System.out.println(apiResult.getJson());
        }else{
    		System.out.println("code:" + apiResult.getErrorCode()+ "，错误消息:" + apiResult.getErrorMsg());
        }
        
        ApiConfigKit.removeThreadLocalApiConfig();
        
	}

}
