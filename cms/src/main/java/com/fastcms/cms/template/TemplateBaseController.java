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
package com.fastcms.cms.template;

import com.fastcms.core.template.Template;
import com.fastcms.core.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/27
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class TemplateBaseController {

	@Autowired
	protected TemplateService templateService;

	protected Template getTemplate() {
		return templateService.getCurrTemplate();
	}

	protected String getTemplatePath() {
		return getTemplatePath(null);
	}

	protected String getTemplatePath(String id) {
		Template template = templateService.getTemplate(id);
		if (template == null) {
			template = getTemplate();
		}
		return template == null ? "/cms/" : template.getPath();
	}

}
