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
package com.fastcms.cms.directive;

import com.fastcms.core.directive.BaseFunction;
import com.fastcms.core.template.TemplateService;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 获取模板上下文路径
 * ${ctx()}
 *
 * @author： wjun_java@163.com
 * @date： 2022/10/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("ctx")
public class TemplateCtxDirective extends BaseFunction {

	@Resource
	private TemplateService templateService;

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		String path = templateService.getCurrTemplate().getPath();
		return path.substring(0, path.length() - 1);
	}

}
