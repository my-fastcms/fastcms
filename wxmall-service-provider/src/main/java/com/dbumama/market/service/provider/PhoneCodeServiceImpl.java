/**
 * 文件名:PhoneCodeServiceImpl.java
 * 版本信息:1.0
 * 日期:2015-10-10
 * 版权所有
 */
package com.dbumama.market.service.provider;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.Agent;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.user.PhoneCodeService;
import com.dbumama.market.service.api.user.UserException;


/**
 * @author: wjun.java@gmail.com
 * @date:2015-10-10
 */
@Service("phoneCodeService")
public class PhoneCodeServiceImpl implements PhoneCodeService{

	
	@Override
	public String getCodeByForget(String phone,String ip) throws UserException{
		SellerUser user = SellerUser.dao.findFirst("select * from "+SellerUser.table +" where phone =? ", phone);
		if(user == null)
			throw new UserException("手机号码不存在");
		
		return sendCode(phone, ip);
     }
	
	@Override
	public String getCode(String phone,String ip) throws UserException {
		SellerUser user = SellerUser.dao.findFirst("select * from " + SellerUser.table + " where phone=? ", phone);
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
		return null;
	}

	@Override
	public String getCodeByAgent(String phone, String ip) throws UserException {
		Agent agent = Agent.dao.findFirst(" select * from " + Agent.table + " where agent_phone= ? ", phone);
		if(agent != null) throw new UserException("手机号已注册");
		return sendCode(phone,ip);
	}

	
	
}
