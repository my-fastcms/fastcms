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
package com.fastcms.core.directive;

import com.fastcms.entity.Config;
import com.fastcms.service.IConfigService;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ${config("xxx")!}
 *
 * @author： wjun_java@163.com
 * @date： 2021/5/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("config")
public class ConfigDirective extends BaseFunction implements InitializingBean {

	@Value("${fastcms.config.ignore.keys:}")
	private List<String> ignoreKeys;

	/**
	 * 私密配置属性
	 */
	static final List<String> securityKeyList = Collections.synchronizedList(new ArrayList<>());

	static {
		securityKeyList.add("wehcat_key_store_path");
		securityKeyList.add("aliyun_sms_app_secret");
		securityKeyList.add("wechat_miniapp_secret");
		securityKeyList.add("wechat_mp_secret");
		securityKeyList.add("wechat_mch_secret");
		securityKeyList.add("aliyunOssAccessKeySecret");
	}

	@Autowired
	private IConfigService configService;

	@Override
	public Object exec(List arguments) throws TemplateModelException {

		SimpleScalar simpleScalar = (SimpleScalar) arguments.get(0);
		if (securityKeyList.contains(simpleScalar.getAsString())) {
			return "";
		}

		Config config = configService.findByKey(simpleScalar.getAsString());
		return config == null ? "" : config.getValue();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		securityKeyList.addAll(ignoreKeys);
	}
}
