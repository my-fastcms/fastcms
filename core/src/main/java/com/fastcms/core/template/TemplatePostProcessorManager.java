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
package com.fastcms.core.template;

import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.PluginUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2023/2/19
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class TemplatePostProcessorManager implements ApplicationListener<ApplicationStartedEvent> {

	List<TemplatePostProcessor> templatePostProcessorList = Collections.synchronizedList(new ArrayList<>());

	TemplatePostProcessor getTemplatePostProcessor() {
		List<TemplatePostProcessor> extensions = PluginUtils.getExtensions(TemplatePostProcessor.class);
		templatePostProcessorList.addAll(extensions);

		for (TemplatePostProcessor templatePostProcessor : templatePostProcessorList) {
			if (templatePostProcessor.isSupport()) {
				return templatePostProcessor;
			}
		}
		return new DefaultTemplatePostProcessor();
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		Map<String, TemplatePostProcessor> fastcmsSearcherMap = ApplicationUtils.getApplicationContext().getBeansOfType(TemplatePostProcessor.class);
		templatePostProcessorList.addAll(fastcmsSearcherMap.values());
	}

	class DefaultTemplatePostProcessor implements  TemplatePostProcessor {

		@Override
		public Template postProcess(Template template) {
			return template;
		}

		@Override
		public Boolean isSupport() {
			return null;
		}

	}

}
