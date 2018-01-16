/**
 * 文件名:PhoneCodeServiceImpl.java
 * 版本信息:1.0
 * 日期:2015-10-10
 * 版权所有
 */
package com.dbumama.market.service.provider;

import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.model.UserCode;
import com.dbumama.market.service.api.user.PhoneCodeService;
import com.dbumama.market.service.api.user.UserException;
import com.dbumama.market.service.utils.SendSmsMessageUtil;


/**
 * @author: wjun.java@gmail.com
 * @date:2015-10-10
 */
@Service("phoneCodeService")
public class PhoneCodeServiceImpl implements PhoneCodeService{

	private static final SellerUser sellerUserdao = new SellerUser().dao();
	private static final UserCode usercodedao = new UserCode().dao();
	
	@Override
	public String getCodeByForget(String phone,String ip) throws UserException{
		SellerUser user = sellerUserdao.findFirst("select * from "+SellerUser.table +" where phone =? ", phone);
		if(user == null)
			throw new UserException("手机号码不存在");
		
		return sendCode(phone, ip);
     }
	
	@Override
	public String getCode(String phone,String ip) throws UserException {
		SellerUser user = sellerUserdao.findFirst("select * from " + SellerUser.table + " where phone=? ", phone);
		if(user != null){
			throw new UserException("手机号码已注册");
		}

		return sendCode(phone,ip);
	}
	
	@Override
	public String verifyCode(String phone,String ip) throws UserException {
		return sendCode(phone,ip);
	}

	private String sendCode(String phone, String ip) throws UserException {
	
		JSONObject jsonObj = new JSONObject();
		//生成验证码
		Random random = new Random();
		int x = random.nextInt(899999) + 100000;
		
		String returnStr = "";
		returnStr = "0,success";
		boolean bl=SendSmsMessageUtil.sendCheckCodeSMS(phone, x+"");
		if(!bl){
			throw new UserException("验证码发送失败");
		}
		
		
		//发送短信成功
		try{
			//插入
		    UserCode userCode = new UserCode();
		    userCode.set("ip", ip);
			userCode.setVcodePhone(phone);
			userCode.setVcodeCode(x+"");
			userCode.setUpdated(new Date());
			userCode.setCreated(new Date());
			userCode.save();
			
		}catch(Exception ex){	
			throw new UserException("短信验证码错误");
		}
		
		jsonObj.put("returnmessage", returnStr);
		jsonObj.put("phone", phone);
		return jsonObj.toString();
	}

	@Override
	public String getCodeByAgent(String phone, String ip) throws UserException {
		return sendCode(phone,ip);
	}
	
	@Override
	public UserCode getVerifyUserCode(String phone, String code) {
		return usercodedao.findFirst("select * from " + UserCode.table + " where vcode_phone=? and vcode_code=? ", phone, code);
	}
}
