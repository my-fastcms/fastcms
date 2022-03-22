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
import com.fastcms.common.request.HeaderField;

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

    @HeaderField(format = true)
    private String Content_Type;
    @HeaderField(format = true)
    private String x_sdk_client;

    public AliyunSmsRequest(String signName, String code, String accessKeyId, String phoneNumbers, String timestamp) {
        this.SignName = signName;
        this.Timestamp = timestamp;
        this.TemplateParam = "{\"code\":\""+code+"\"}";
        this.AccessKeyId = accessKeyId;
        this.PhoneNumbers = phoneNumbers;

        this.Content_Type = "application/x-www-form-urlencoded;charset=utf-8";
        this.x_sdk_client = "Java/2.0.0";
        buildParams();
    }

    @Override
    protected String getRequestUrl() {
        return "http://dysmsapi.aliyuncs.com/";
    }

}
