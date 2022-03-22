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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class AliyunSmsRequest extends AbstractRequest {

    private String Format = "XML";
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

    public AliyunSmsRequest() {}

    public AliyunSmsRequest(String appKey, String appSecret, String signName, String template, String code, String phoneNumbers, String timestamp) {
        this.SignName = signName;
        this.Timestamp = timestamp;
        this.TemplateCode = template;
        this.TemplateParam = "{\"code\":\""+code+"\"}";
        this.AccessKeyId = appKey;
        this.PhoneNumbers = phoneNumbers;

        java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<>();
        sortParas.putAll(buildParams());

        try {
            this.Signature = signShaHmac1(sortParas, appSecret);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        buildParams().put("Signature", this.Signature);
    }

    @Override
    protected String getRequestUrl() {
        return "http://dysmsapi.aliyuncs.com/";
    }

    private final static String AGLORITHM_NAME = "HmacSHA1";

    private final static String SEPARATOR = "&";

    public static String signShaHmac1(Map<String, String> queries, String accessSecret)
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

}
