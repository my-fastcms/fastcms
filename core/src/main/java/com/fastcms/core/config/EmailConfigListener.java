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
package com.fastcms.core.config;

import com.fastcms.utils.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

import static com.fastcms.common.constants.FastcmsConstants.*;

/**
 * 邮箱配置监听
 * @author： wjun_java@163.com
 * @date： 2022/7/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class EmailConfigListener extends AbstractConfigListener implements ApplicationListener<ApplicationStartedEvent> {

	final static String EMAIL_PREFIX = "email_";

	@Autowired
	private MailProperties mailProperties;

	@Autowired
	private JavaMailSenderImpl javaMailSender;

	@Override
	protected String getMatchKey() {
		return EMAIL_PREFIX;
	}

	@Override
	protected void doChange(Map<String, String> datasMap) {
		setMailProperties(mailProperties, javaMailSender);
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		setMailProperties(mailProperties, javaMailSender);
	}

	void setMailProperties(MailProperties mailProperties, JavaMailSenderImpl sender) {
		mailProperties.setHost(getEmailHost());
		mailProperties.setPort(getEmailPort());
		mailProperties.setUsername(getEmailUserName());
		mailProperties.setPassword(getEmailPassword());
		mailProperties.getProperties().put("mail.".concat(mailProperties.getProtocol()).concat(".socketFactory.port"), "465");

		applyProperties(mailProperties, javaMailSender);
	}

	String getEmailHost() {
		return ConfigUtils.getConfig(EMAIL_HOST);
	}

	Integer getEmailPort() {
		return ConfigUtils.getInt(EMAIL_PORT, 25);
	}

	String getEmailUserName() {
		return ConfigUtils.getConfig(EMAIL_USERNAME);
	}

	String getEmailPassword() {
		return ConfigUtils.getConfig(EMAIL_PASSWORD);
	}

	void applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
		sender.setHost(properties.getHost());
		if (properties.getPort() != null) {
			sender.setPort(properties.getPort());
		}
		sender.setUsername(properties.getUsername());
		sender.setPassword(properties.getPassword());
		sender.setProtocol(properties.getProtocol());
		if (properties.getDefaultEncoding() != null) {
			sender.setDefaultEncoding(properties.getDefaultEncoding().name());
		}
		if (!properties.getProperties().isEmpty()) {
			sender.setJavaMailProperties(asProperties(properties.getProperties()));
		}
	}

	Properties asProperties(Map<String, String> source) {
		Properties properties = new Properties();
		properties.putAll(source);
		return properties;
	}

}
