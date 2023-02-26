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
package com.fastcms.oauth2.authentication;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author： wjun_java@163.com
 * @date： 2023/2/26
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractFastcmsOAuth2LoginSuccessHandler implements FastcmsOAuth2LoginSuccessHandler, InitializingBean, ApplicationContextAware {

	protected ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		applicationContext.getBean(FastcmsOAuth2LoginSuccessUrlManager.class).addOAuth2LoginSuccessUrl(getRegistrationId(), this);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
