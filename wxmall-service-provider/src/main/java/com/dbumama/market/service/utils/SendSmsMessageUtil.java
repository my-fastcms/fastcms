package com.dbumama.market.service.utils;


import com.dbumama.market.service.notify.sms.SmsMessage;
import com.dbumama.market.service.notify.sms.SmsSenderFactory;
import com.jfinal.kit.PropKit;


public class SendSmsMessageUtil {
	private SendSmsMessageUtil() {}

	public static boolean sendCheckCodeSMS(final String phone, final String checkcode) {
		SmsMessage sms = new SmsMessage();
		sms.setContent("短信验证码");
		sms.setRec_num(phone);
		sms.setTemplate(PropKit.get("sms_template_code"));
		sms.setParam("{\"code\":\"" + checkcode + "\",\"product\":\""+PropKit.get("sms_product")+"\"}");
		sms.setSign_name(PropKit.get("sms_sign_name"));
		return SmsSenderFactory.createSender().send(sms);
	}
   
}
