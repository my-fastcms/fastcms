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

import java.io.Serializable;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/20
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class SmsMessage implements Serializable {

	/**
	 * 要发送的手机号
	 */
	private String mobile;
	/**
	 * 短信签名
	 */
	private String sign;
	/**
	 * 短信模板
	 */
	private String template;
	/**
	 * 短信验证码
	 */
	private String code;

	public SmsMessage(String mobile, String sign, String template, String code) {
		this.mobile = mobile;
		this.sign = sign;
		this.template = template;
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
