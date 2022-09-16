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
import com.fastcms.cms.service.IArticleService;
import com.fastcms.core.directive.BaseDirective;
import freemarker.core.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("articleListTag")
public class ArticleListDirective extends BaseDirective {

	private static final String ARTICLE_CATEGORY_ID = "categoryId";

	private static final String ARTICLE_TAG_ID = "tagId";

	@Autowired
	private IArticleService articleService;

	@Override
	public Object doExecute(Environment env, Map params) {

		final Long categoryId = getLong(ARTICLE_CATEGORY_ID, params, DEFAULT_ID);
		final Integer count = getInt(PARAM_COUNT, params, 10);
		String orderBy = getStr(PARAM_ORDER_BY, params, "a.created");
		if(categoryId != 0) {
			return articleService.getArticleListByCategoryId(categoryId, count, orderBy);
		}

		final Long tagId = getLong(ARTICLE_TAG_ID, params, DEFAULT_ID);
		if (tagId != null) {
			return articleService.getArticleListByTagId(tagId, count, orderBy);
		}

		orderBy = getStr(PARAM_ORDER_BY, params, "created");
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.last("limit 0," + count);
		wrapper.orderByDesc(orderBy);

		return articleService.list(wrapper);
	}

}
