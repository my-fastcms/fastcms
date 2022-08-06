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
package com.fastcms.web.security;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.utils.ConfigUtils;
import io.jsonwebtoken.io.Decoders;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/10/24
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
public class AuthConfigs {

	/**
	 * jwt secretKey
	 */
	@Value("${fastcms.auth.token.secret-key:}")
	private String secretKey;

	/**
	 * jwt token validity seconds
	 */
	@Value("${fastcms.auth.token.expire.seconds:18000}")
	private long tokenValidityInSeconds;

	/**
	 * 忽悠安全校验的api urls
	 */
	@Value("${fastcms.security.ignore.urls:}")
	private List<String> ignoreUrls;

	private byte[] secretKeyBytes;

	public long getTokenValidityInSeconds() {
		return StringUtils.isNotBlank(ConfigUtils.getConfig(FastcmsConstants.JWT_EXPIRE)) ? ConfigUtils.getLong(FastcmsConstants.JWT_EXPIRE, 18000l) : tokenValidityInSeconds;
	}

	/**
	 * 是否开启授权白名单
	 */
	@Value("${fastcms.auth.enable.userAgentAuthWhite:false}")
	private boolean enableUserAgentAuthWhite;

	public byte[] getSecretKeyBytes() {
		if (secretKeyBytes == null) {
			secretKeyBytes = Decoders.BASE64.decode(secretKey);
		}

		if (StringUtils.isNotBlank(ConfigUtils.getConfig(FastcmsConstants.JWT_SECRET))) {
			secretKeyBytes = Decoders.BASE64.decode(ConfigUtils.getConfig(FastcmsConstants.JWT_SECRET));
		}

		return secretKeyBytes;
	}

	public List<String> getIgnoreUrls() {
		return ignoreUrls;
	}

	public boolean isEnableUserAgentAuthWhite() {
		return enableUserAgentAuthWhite;
	}

}
