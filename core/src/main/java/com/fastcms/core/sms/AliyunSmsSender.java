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
package com.fastcms.core.sms;

import com.fastcms.common.http.Header;
import com.fastcms.common.http.HttpRestResult;
import com.fastcms.common.http.Query;
import com.fastcms.common.utils.HttpUtils;
import com.fastcms.utils.ConfigUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/20
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class AliyunSmsSender implements FastcmsSmsSender {

	private static final Logger logger = LoggerFactory.getLogger(AliyunSmsSender.class);

	private final static String TIME_ZONE = "GMT";

	private final static String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	static final String ALIYUN_SMS_APP_KEY = "aliyun_sms_app_key";

	static final String ALIYUN_SMS_APP_SECRET = "aliyun_sms_app_secret";

	static final String ALIYUN_SMS_IS_ENABLE = "aliyun_sms_is_enable";

	@Override
	public boolean send(SmsMessage smsMessage) {
		if (isEnable()) {
			return doSend(smsMessage, getAppKey(), getAppSecret());
		}
		return false;
	}

	@Override
	public boolean isEnable() {
		final String config = ConfigUtils.getConfig(ALIYUN_SMS_IS_ENABLE);
		if (StringUtils.isBlank(config)) return true;
		try {
			return Boolean.parseBoolean(config);
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	private Boolean doSend(SmsMessage sms, String appKey, String appSecret) {
//		try {
//			AliyunSmsRequest aliyunSmsRequest = new AliyunSmsRequest(appKey, appSecret, sms.getSign(), sms.getTemplate(), sms.getCode(), sms.getMobile(), getISO8601Time());
//			aliyunSmsRequest
//					.addHeader("Content_Type", "application/x-www-form-urlencoded;charset=utf-8")
//					.addHeader("x-sdk-client", "Java/2.0.0");
//			RestResult restResult = aliyunSmsRequest.get();
//			String message = restResult.getMessage();
//			System.out.println(message);
//			int code = restResult.getCode();
//			return code == 200;
//		} catch (Exception e) {
//			logger.error("AlidayuSmsSender doSend http exception", e);
//		}

		Map<String, String> params = new HashMap<>();
		params.put("Format", "XML");
		params.put("SignName", sms.getSign());
		params.put("SignatureMethod", "HMAC-SHA1");

		params.put("Timestamp", getISO8601Time());
		params.put("TemplateCode", sms.getTemplate());
		params.put("TemplateParam", "{\"code\":\""+sms.getCode()+"\"}");
		params.put("Action", "SendSms");
		params.put("AccessKeyId", appKey);
		params.put("RegionId", "cn-hangzhou");
		params.put("PhoneNumbers", sms.getMobile());
		params.put("Version", "2017-05-25");
		params.put("SignatureVersion", "1.0");
		params.put("SignatureNonce", UUID.randomUUID().toString());

		String sign = null;
		try {
			sign = new AliyunSmsRequest().signShaHmac1(params, appSecret + "&");
		} catch (InvalidKeyException | IllegalStateException e1) {
			e1.printStackTrace();
			return null;
		}
//		params.put("Signature", SignUtils.SHA1.sign(params, appSecret, FastcmsConstants.ENCODE));

		params.put("Signature", sign);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.put("x-sdk-client", "Java/2.0.0");
		try {
			Header header = Header.EMPTY;
			header.addAll(headers);
			HttpRestResult<Object> objectHttpRestResult = HttpUtils.get("http://dysmsapi.aliyuncs.com/", header, Query.EMPTY.initParams(params), String.class);
			System.out.println(objectHttpRestResult.getData().toString());
		} catch (Exception e) {
		}

		return false;
	}

	String getISO8601Time() {
		Date nowDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_ISO8601);
		df.setTimeZone(new SimpleTimeZone(0, TIME_ZONE));
		return df.format(nowDate);
	}

	String getAppKey() {
		return ConfigUtils.getConfig(ALIYUN_SMS_APP_KEY);
	}

	String getAppSecret() {
		return ConfigUtils.getConfig(ALIYUN_SMS_APP_SECRET);
	}

}
