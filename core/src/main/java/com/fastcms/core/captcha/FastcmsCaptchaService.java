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
package com.fastcms.core.captcha;

import com.fastcms.common.utils.StrUtils;
import com.fastcms.utils.RequestUtils;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.fastcms.common.constants.FastcmsConstants.WEB_CODE_CACHE_NAME;

/**
 * @author： wjun_java@163.com
 * @date： 2021/11/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class FastcmsCaptchaService {

	@Autowired
	private CacheManager cacheManager;

	public boolean checkCaptcha(String code) {
		final String codeKey = RequestUtils.getClientId(RequestUtils.getRequest());
		Cache.ValueWrapper valueWrapper = cacheManager.getCache(WEB_CODE_CACHE_NAME).get(codeKey);
		String codeInMemory = StrUtils.isBlank (codeKey) ? "" : (valueWrapper == null) ? "" : (String) valueWrapper.get();
		if(StrUtils.isNotBlank(codeKey)) {
			cacheManager.getCache(WEB_CODE_CACHE_NAME).evict(codeKey);
		}
		return StrUtils.isNotBlank(code) && code.equalsIgnoreCase(codeInMemory);
	}

	public FastcmsCaptcha getCaptcha() {
		HttpServletRequest request = RequestUtils.getRequest();
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
		final String verCode = specCaptcha.text().toLowerCase();
		final String key = StrUtils.isEmpty(RequestUtils.getClientId(request)) ? StrUtils.uuid() : RequestUtils.getClientId(request);
		cacheManager.getCache(WEB_CODE_CACHE_NAME).put(key, verCode);
		return new FastcmsCaptcha(key, specCaptcha.toBase64());
	}

}
