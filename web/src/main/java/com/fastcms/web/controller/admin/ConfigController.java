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

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.config.ConfigChangeListenerManager;
import com.fastcms.core.response.Response;
import com.fastcms.entity.Config;
import com.fastcms.service.IConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/config")
public class ConfigController {

	private static final String LISTENER_KEY = "listener";

	@Autowired
	private IConfigService configService;

	@Autowired
	private ConfigChangeListenerManager configChangeListenerManager;

	@PostMapping("doSave")
	public ResponseEntity doSave(HttpServletRequest request) {

		Map<String, String[]> parameterMap = request.getParameterMap();
		if (parameterMap == null || parameterMap.isEmpty()) {
			return Response.fail("没有数据提交");
		}

		HashMap<String, String> datasMap = new HashMap<>();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			if (entry.getValue() != null && entry.getValue().length > 0) {
				String value = null;
				for (String v : entry.getValue()) {
					if (StringUtils.isNotEmpty(v)) {
						value = v;
						break;
					}
				}
				datasMap.put(entry.getKey(), value);
			}
		}

		List<String> listeners = new ArrayList<>();
		for (Map.Entry<String, String> entry : datasMap.entrySet()) {
			String key = entry.getKey().trim();
			if(LISTENER_KEY.equals(key)) {
				if(StringUtils.isNotBlank(entry.getValue())) {
					String[] listenerNames = entry.getValue().split(",");
					listeners.addAll(Arrays.asList(listenerNames));
				}
			}else {
				Config config = new Config(key, entry.getValue());
				configService.saveOrUpdateConfig(config);
			}
		}

		if(!listeners.isEmpty()) {
			for (String listener : listeners) {
				try {
					configChangeListenerManager.onChange(listener);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}

		return Response.success();
	}

}
