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

import com.fastcms.cms.service.ISinglePageService;
import com.fastcms.core.directive.BaseDirective;
import com.fastcms.utils.CollectionUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 单页标签
 * <@singlePageList ids="1,2,3">
 *     show data ...
 * </@singlePageList>
 * @author： wjun_java@163.com
 * @date： 2023/4/9
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("singlePageList")
public class SinglePageDirective extends BaseDirective {

	private static final String PAGE_IDS = "ids";

	@Autowired
	private ISinglePageService singlePageService;

	@Override
	public Object doExecute(Environment env, Map params) throws TemplateModelException {

		final String pageIdParams = getStr(PAGE_IDS, params);

		List<Long> pageIds = strArrayToList(pageIdParams);

		if (CollectionUtils.isEmpty(pageIds)) {
			return null;
		}

		return singlePageService.getSinglePageByIds(pageIds);
	}

}
