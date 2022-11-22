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

import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.ArticleTag;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.service.IArticleTagService;
import com.fastcms.core.directive.BaseDirective;
import freemarker.core.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 关联文章标签
 *
 * <@relatedArticleList articleId="1" count="10">
 *     <#if data??>
 *         <#list data as item>
 *             ${(item.id)!}
 *             ${(item.title)!}
 *             ${(item.viewCount)!}
 *             ${(item.status)!}
 *             ${(item.author)!}
 *             ${(item.outLink)!}
 *             ${(item.thumbnailUrl)!}
 *             ${(item.summary)!}
 *             ${(item.status)!}
 *             ${(item.url)!}
 *             <@formatTime value=(item.created)!/>
 *      		...
 *         </#list>
 *     </#if>
 * </@relatedArticleList>
 *
 * @author： wjun_java@163.com
 * @date： 2022/1/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("relatedArticleList")
public class RelatedArticleDirective extends BaseDirective {

	private static final String PARAM_ARTICLE_ID = "articleId";

	@Autowired
	private IArticleCategoryService articleCategoryService;

	@Autowired
	private IArticleTagService articleTagService;

	@Autowired
	private IArticleService articleService;

	@Override
	public Object doExecute(Environment env, Map params) {
		final Integer count = getInt(PARAM_COUNT, params, 10);
		final Long articleId = getLong(PARAM_ARTICLE_ID, params, 0l);

		List<ArticleCategory> articleCategoryList = articleCategoryService.getArticleCategoryListByArticleId(articleId);

		List<IArticleService.ArticleVo> result = new ArrayList<>();
		for (ArticleCategory articleCategory : articleCategoryList) {
			List<IArticleService.ArticleVo> articleList = articleService.getArticleListByCategoryId(articleCategory.getId(), count, "a.created");
			if(articleList.size() >= count) break;
			result.addAll(articleList);
		}

		List<ArticleTag> articleTagList = articleTagService.getArticleTagListByArticleId(articleId);
		for (ArticleTag articleTag : articleTagList) {
			List<IArticleService.ArticleVo> articleList = articleService.getArticleListByTagId(articleTag.getId(), count, "a.created");
			if(articleList.size() >= count) break;
			result.addAll(articleList);
		}

		//去重
		List<IArticleService.ArticleVo> unique = result.stream().collect(Collectors.collectingAndThen(
				Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(IArticleService.ArticleVo::getId))), ArrayList::new)
		);
		return unique;
	}

}
