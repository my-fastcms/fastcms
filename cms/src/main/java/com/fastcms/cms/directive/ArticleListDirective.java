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
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.directive.BaseDirective;
import freemarker.core.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 根据分类或者标签id获取文章列表
 * 参数分类id：categoryId
 * 或者
 * 参数标签id：tagId
 *
 * 包含的分类ids: includeCategoryIds (必须有categoryId参数才生效)
 * 不包含的分类ids：excludeCategoryIds (必须有categoryId参数才生效)
 *
 * 包含的标签ids：includeTagIds (必须有tagId参数才生效)
 * 不包含的标签ids: excludeTagIds	(必须有tagId参数才生效)
 *
 * <@articleListTag categoryId=category.id! includeCategoryIds="1,2,3" excludeCategoryIds="4,5,6" orderBy="created">
 *   <#if data??>
 *   	<div class="category-content-wrap">
 *           <#list data as item>
 *               <div class="category-post-list">
 *                    <div class="category-post-img">
 *                         <img src="${(item.thumbnail)!}" alt="">
 *                    </div>
 *                    <div class="category-post-content">
 *                         <h4><a href="${(item.url)!}">${(item.title)!}</a></h4>
 *                         <h4><a href="${(item.summary)!}">摘要：${(item.summary)!}</a></h4>
 *                         <p><@formatTime value=(item.created)! format="yyyy-MM-dd"/></p>
 *                    </div>
 *               </div>
 *           </#list>
 *      </div>
 *   </#if>
 * </@articleListTag>
 *
 * <@articleListTag tagId=tag.id! includeTagIds="1,2,3" excludeTagIds="4,5,6" orderBy="created">
 *     show data ...
 * </@articleListTag>
 *
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

	/**
	 * 包含的分类id，多个用逗号隔开
	 */
	private static final String INCLUDE_CATEGORY_ID = "includeCategoryIds";

	/**
	 * 排除的分类id，多个用逗号隔开
	 */
	private static final String EXCLUDE_CATEGORY_ID = "excludeCategoryIds";

	/**
	 * 包含的标签id，多个用逗号隔开
	 */
	private static final String INCLUDE_TAG_ID = "includeTagIds";

	/**
	 * 排除的标签id。多个用逗号隔开
	 */
	private static final String EXCLUDE_TAG_ID = "excludeTagIds";

	@Autowired
	private IArticleService articleService;

	@Override
	public Object doExecute(Environment env, Map params) {

		final Long categoryId = getLong(ARTICLE_CATEGORY_ID, params, DEFAULT_ID);
		final Integer count = getInt(PARAM_COUNT, params, 10);
		final String includeCategoryIds = getStr(INCLUDE_CATEGORY_ID, params);
		final String excludeCategoryIds = getStr(EXCLUDE_CATEGORY_ID, params);
		final String includeTagIds = getStr(INCLUDE_TAG_ID, params);
		final String excludeTagIds = getStr(EXCLUDE_TAG_ID, params);

		List<Long> includeCategoryIdList = strArrayToList(includeCategoryIds);
		List<Long> excludeCategoryIdList = strArrayToList(excludeCategoryIds);
		List<Long> includeTagIdList = strArrayToList(includeTagIds);
		List<Long> excludeTagIdList = strArrayToList(excludeTagIds);

		String orderBy = getStr(PARAM_ORDER_BY, params, "a.created");
		if(hasKey(ARTICLE_CATEGORY_ID, params) || hasKey(INCLUDE_CATEGORY_ID, params) || hasKey(EXCLUDE_CATEGORY_ID, params)) {
			return articleService.getArticleListByCategoryId(categoryId, includeCategoryIdList, excludeCategoryIdList, count, orderBy);
		}

		final Long tagId = getLong(ARTICLE_TAG_ID, params, DEFAULT_ID);
		if (hasKey(ARTICLE_TAG_ID, params) || hasKey(INCLUDE_TAG_ID, params) || hasKey(EXCLUDE_CATEGORY_ID, params)) {
			return articleService.getArticleListByTagId(tagId, includeTagIdList, excludeTagIdList, count, orderBy);
		}

		orderBy = getStr(PARAM_ORDER_BY, params, "created");
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("status", Article.STATUS_PUBLISH);
		wrapper.last(count > 0, "limit 0," + count);
		wrapper.orderByDesc(orderBy);

		return articleService.list(wrapper);
	}

	List<Long> strArrayToList(String ids) {
		return StrUtils.isBlank(ids) ? new ArrayList<>() : Arrays.stream(ids.split(StrUtils.COMMA)).map(Long::valueOf).collect(Collectors.toList());
	}

}
