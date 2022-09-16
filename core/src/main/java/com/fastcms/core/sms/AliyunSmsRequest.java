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

import com.fastcms.common.request.AbstractRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class AliyunSmsRequest extends AbstractRequest {

    private transient final static String TIME_ZONE = "GMT";
    private transient final static String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private transient final static String AGLORITHM_NAME = "HmacSHA1";
    private transient final static String SEPARATOR = "&";

    private String Format = "JSON";
    private String SignName;
    private String SignatureMethod = "HMAC-SHA1";
    private String Timestamp;
    private String TemplateCode;
    private String TemplateParam;
    private String Action = "SendSms";
    private String AccessKeyId;
    private String RegionId = "cn-hangzhou";
    private String PhoneNumbers;
    private String Version = "2017-05-25";
    private String SignatureVersion = "1.0";
    private String SignatureNonce = UUID.randomUUID().toString();
    private String Signature;

    public AliyunSmsRequest(String appKey, String appSecret, String signName, String template, String code, String phoneNumbers) {
        this.SignName = signName;
        this.Timestamp = getISO8601Time();
        this.TemplateCode = template;
        this.TemplateParam = "{\"code\":\""+code+"\"}";
        this.AccessKeyId = appKey;
        this.PhoneNumbers = phoneNumbers;

        try {
            this.Signature = signShaHmac1(buildParams(), appSecret + "&");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        buildParams().put("Signature", this.Signature);
    }

    @Override
    protected String getRequestUrl() {
        return "http://dysmsapi.aliyuncs.com/";
    }

    @Override
    protected Type getResult() {
        return AliyunSmsResult.class;
    }

    protected String signShaHmac1(Map<String, String> queries, String accessSecret)
            throws InvalidKeyException, IllegalStateException {

        String[] sortedKeys = queries.keySet().toArray(new String[] {});
        Arrays.sort(sortedKeys);
        StringBuilder canonicalizedQueryString = new StringBuilder();
        StringBuilder stringToSign = new StringBuilder();
        try {
            for (String key : sortedKeys) {
                canonicalizedQueryString.append("&")
                        .append(AcsURLEncoder.percentEncode(key)).append("=")
                        .append(AcsURLEncoder.percentEncode(queries.get(key)));
            }

            stringToSign.append("GET");
            stringToSign.append(SEPARATOR);
            stringToSign.append(AcsURLEncoder.percentEncode("/"));
            stringToSign.append(SEPARATOR);
            stringToSign.append(AcsURLEncoder.percentEncode(
                    canonicalizedQueryString.toString().substring(1)));

        } catch (UnsupportedEncodingException exp) {
            throw new RuntimeException("UTF-8 encoding is not supported.");
        }

        try {
            Mac mac = Mac.getInstance(AGLORITHM_NAME);
            mac.init(new SecretKeySpec(
                    accessSecret.getBytes(AcsURLEncoder.URL_ENCODING), AGLORITHM_NAME));
            byte[] signData = mac.doFinal(stringToSign.toString().getBytes(AcsURLEncoder.URL_ENCODING));
            return Base64.getEncoder().encodeToString(signData);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HMAC-SHA1 not supported.");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.");
        }

    }

    protected String getISO8601Time() {
        Date nowDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_ISO8601);
        df.setTimeZone(new SimpleTimeZone(0, TIME_ZONE));
        return df.format(nowDate);
    }

    static class AcsURLEncoder {
        public final static String URL_ENCODING = "UTF-8";

        public static String encode(String value) throws UnsupportedEncodingException {
            return URLEncoder.encode(value, URL_ENCODING);
        }

        public static String percentEncode(String value) throws UnsupportedEncodingException {
            return value != null ? URLEncoder.encode(value, URL_ENCODING).replace("+", "%20")
                    .replace("*", "%2A").replace("%7E", "~") : null;
        }
    }

    public static class AliyunSmsResult implements Serializable {
        String Message;
        String RequestId;
        String BizId;
        String Code;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String requestId) {
            RequestId = requestId;
        }

        public String getBizId() {
            return BizId;
        }

        public void setBizId(String bizId) {
            BizId = bizId;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }
    }

}
