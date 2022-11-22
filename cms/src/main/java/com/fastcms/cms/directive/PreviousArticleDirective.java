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
import com.fastcms.core.directive.BaseDirective;
import freemarker.core.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * <@prevArticleTag articleId=(article.id)!>
 * 	<#if data??>
 *  	<a href="${data.url!}">上一篇</a>
 *  </#if>
 * </@prevArticleTag>
 *
 * @author： wjun_java@163.com
 * @date： 2021/5/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("prevArticleTag")
public class PreviousArticleDirective extends BaseDirective {

	private static final String PARAM_ARTICLE_ID = "articleId";

	@Autowired
	private IArticleService articleService;

	@Override
	public Object doExecute(Environment env, Map params) {
		final Long articleId = getLong(PARAM_ARTICLE_ID, params, 1l);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.lt("id", articleId);
		queryWrapper.eq("status", Article.STATUS_PUBLISH);
		queryWrapper.last("limit 0, 1");
		queryWrapper.orderByDesc("id");
		return articleService.getOne(queryWrapper);
	}

}
