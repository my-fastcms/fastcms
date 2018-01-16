package com.dbumama.market.service.notify.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.weixin.sdk.utils.SignKit;

public class AlidayuSmsSender implements ISmsSender {
	private static final Log log = Log.getLog(AlidayuSmsSender.class);

	/**
	 * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7395905.0.0.Y1YXKM&
	 * apiId=25443
	 */

	@Override
	public boolean send(SmsMessage sms) {
		String app_key =  PropKit.get("sms_app_key");//"your app key";
		String app_secret =  PropKit.get("sms_app_secret");//"your app secret"

		String sendResult = doSend(sms, app_key, app_secret);


		if (StringUtils.isNotBlank(sendResult)) {
			if (sendResult != null && sendResult.contains("alibaba_aliqin_fc_sms_num_send_response")
					&& sendResult.contains("success") && sendResult.contains("true")) {
				return true;
			}
		}
		return false;
	}

	private static String doSend(SmsMessage sms, String app_key, String app_secret) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("format", "json");
		params.put("method", "alibaba.aliqin.fc.sms.num.send");
		params.put("sign_method", "md5");

		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		params.put("timestamp", timestamp);
		params.put("v", "2.0");
		params.put("rec_num", sms.getRec_num());
		params.put("sms_free_sign_name", sms.getSign_name());
		params.put("sms_param", sms.getParam());
		params.put("sms_template_code", sms.getTemplate());
		params.put("sms_type", "normal");
		params.put("app_key", app_key);

		String sign = SignKit.signForRequest(params, app_secret);
		params.put("sign", sign);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		try {
			return HttpKit.post("http://gw.api.taobao.com/router/rest", params, "", headers);
		} catch (Exception e) {
			log.error("AlidayuSmsSender doSend http exception", e);
		}
		return null;
	}

	public static void main(String[] args) {
		SmsMessage sms = new SmsMessage();

		sms.setContent("test");
		sms.setRec_num("18600000000");
		sms.setTemplate("SMS_6730856");
		sms.setParam("{\"code\":\"8888\",\"product\":\"Wxmall\",\"customer\":\"wjun_java@163.com\"}");
		sms.setSign_name("登录验证");

		boolean sendOk = new AlidayuSmsSender().send(sms);

		System.out.println(sendOk);
		System.out.println("===============finished!===================");
	}

}
