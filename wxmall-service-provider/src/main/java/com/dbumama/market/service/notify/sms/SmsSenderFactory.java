package com.dbumama.market.service.notify.sms;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PropKit;

public class SmsSenderFactory {
	

	public static ISmsSender createSender() {
		
		String provider = PropKit.get("sms_app_provider");
		
		if(StringUtils.isBlank(provider)){
			return new AlidayuSmsSender();
		}
		
		else if("sms_provider_alidayu".equals(provider)){
			return new AlidayuSmsSender();
		}
		
//		其他短信服务商
//		else if("sms_provider_xxx".equals(provider)){
//			return new XXXSmsSender();
//		}
		
		return new AlidayuSmsSender();

	}

}
