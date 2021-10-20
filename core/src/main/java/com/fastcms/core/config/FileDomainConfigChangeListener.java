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

import com.fastcms.common.constants.FastcmsConfig;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.constants.FastcmsConfig;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.service.IConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class FileDomainConfigChangeListener extends BaseConfigChangeListener implements InitializingBean {

	@Autowired
	IConfigService configService;

	@Override
	protected void doChange() throws Exception {
		FastcmsConfig.FILE_DOMAIN_URL = configService.getValue(FastcmsConstants.FILE_DOMAIN);
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		super.afterPropertiesSet();

		String fileDomainUrl = configService.getValue(FastcmsConstants.FILE_DOMAIN);
		if(StringUtils.isNotBlank(fileDomainUrl)) {
			FastcmsConfig.FILE_DOMAIN_URL = fileDomainUrl + FastcmsConstants.STATIC_RESOURCE_PATH;
		}
	}
}
