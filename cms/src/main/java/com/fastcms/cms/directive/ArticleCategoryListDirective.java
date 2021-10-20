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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.core.directive.BaseDirective;
import freemarker.core.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/24
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("categoryListTag")
public class ArticleCategoryListDirective extends BaseDirective {

	static final String PARAM_TYPE = "type";

	@Autowired
	private IArticleCategoryService articleCategoryService;

	@Override
	public Object doExecute(Environment env, Map params) {
		String type = getStr(PARAM_TYPE, params, ArticleCategory.CATEGORY_TYPE);
		Integer count = getInt(PARAM_COUNT, params);
		String orderBy = getStr(PARAM_ORDER_BY, params, "created");

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("type", type);
		if(count != 0) {
			queryWrapper.last("limit 0," + count);
		}
		queryWrapper.orderByDesc(orderBy);

		return articleCategoryService.list(queryWrapper);
	}

}
