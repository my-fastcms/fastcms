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
package com.fastcms.web.controller.admin;

import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.config.ConfigListenerManager;
import com.fastcms.entity.Config;
import com.fastcms.service.IConfigService;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.I18nUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fastcms.common.constants.FastcmsConstants.FASTCMS_SYSTEM_NO_DATA;
import static com.fastcms.service.IResourceService.ResourceI18n.RESOURCE_NAME_CONFIG_LIST;
import static com.fastcms.service.IResourceService.ResourceI18n.RESOURCE_NAME_CONFIG_SAVE;

/**
 * 配置
 * @author： wjun_java@163.com
 * @date： 2021/5/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/config")
public class ConfigController {

	@Autowired
	private IConfigService configService;

	@Autowired
	private ConfigListenerManager configListenerManager;

	/**
	 * 保存配置
	 * @param request
	 * @return
	 */
	@PostMapping("save")
	@Secured(name = RESOURCE_NAME_CONFIG_SAVE, resource = "config:save", action = ActionTypes.WRITE)
	public RestResult<Boolean> save(HttpServletRequest request) {

		Map<String, String[]> parameterMap = request.getParameterMap();
		if (parameterMap == null || parameterMap.isEmpty()) {
			return RestResultUtils.failed(I18nUtils.getMessage(FASTCMS_SYSTEM_NO_DATA));
		}

		Map<String, String> datasMap = new HashMap<>();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			if (entry.getValue() != null && entry.getValue().length > 0) {
				String value = null;
				for (String v : entry.getValue()) {
					if (StringUtils.isNotEmpty(v)) {
						value = v;
						break;
					}
				}

				//值不发生变化的，不处理
				String oldValue = configService.getValue(entry.getKey());
				if(StringUtils.isNotBlank(value) && !value.equals(oldValue)) {
					datasMap.put(entry.getKey(), value);
				}
			}
		}

		for (Map.Entry<String, String> entry : datasMap.entrySet()) {
			String key = entry.getKey().trim();
			configService.saveConfig(key, entry.getValue());
		}

		try {
			if (datasMap.size() > 0) {
				configListenerManager.change(datasMap);
			}
		} catch (Exception e) {
		}

		return RestResultUtils.success(true);
	}

	/**
	 * 获取配置
	 * @param configKeys	key数组
	 * @return
	 */
	@PostMapping("list")
	@Secured(name = RESOURCE_NAME_CONFIG_LIST, resource = "config:list", action = ActionTypes.READ)
	public RestResult<List<Config>> getConfigList(@RequestParam("configKeys") List<String> configKeys) {
		return RestResultUtils.success(configService.getConfigs(configKeys));
	}

	/**
	 * 获取配置(无需登录)
	 * @param configKeys	key数组
	 * @return
	 */
	@PostMapping("public/list")
	public RestResult<List<Config>> getPublicConfigList(@RequestParam("configKeys") List<String> configKeys) {
		return RestResultUtils.success(configService.getPublicConfigs(configKeys));
	}

	/**
	 * 测试邮件配置
	 * @return
	 */
	@GetMapping("mail/test")
	public Object testMailConfig() {
		try {
			ApplicationUtils.getBean(JavaMailSenderImpl.class).testConnection();
			return RestResultUtils.success();
		} catch (MessagingException e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

}
