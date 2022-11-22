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

import com.fastcms.cms.service.IArticleService;
import com.fastcms.core.directive.BaseDirective;
import freemarker.core.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文章标签: 根据文章id获取文章信息
 * 字段说明：
 * - url 				获取访问的url
 * - id
 * - createUserId 		创建用户
 * - title 				文章标题
 * - url				文章访问链接
 * - contentHtml 		文章详情
 * - summary 			文章摘要
 * - seoKeywords 		seo关键字
 * - seoDescription 	seo描述
 * - outLink 			文章外链
 * - sortNum 			文章排序，值越大越靠前
 * - viewCount	 		浏览量
 * - commentEnable 		是否开启评论
 * - thumbnail 			文章缩略图
 * - status 			状态
 * - suffix 			模板前缀
 * - attachId 			附件
 * - attachTitle		附件名称
 * - created 			创建时间		<@formatTime value=(item.created)!/>
 * - updated 			更新时间		<@formatTime value=(item.updated)!/>
 * - articleCategory 	分类id集合
 * - articleTag 		标签名称集合
 *
 * <@article articleId=(article.id)!>
 *  	<#if data??>
 *  		<a href="${data.url!}">${data.title!}</a>
 *  	</#if>
 * </@article>
 *
 * @author： wjun_java@163.com
 * @date： 2021/5/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("article")
public class ArticleDirective extends BaseDirective {

	private static final String PARAM_ARTICLE_ID = "articleId";

	@Autowired
	private IArticleService articleService;

	@Override
	public Object doExecute(Environment env, Map params) {
		return articleService.getArticleDetail(getLong(PARAM_ARTICLE_ID, params, 0l));
	}

}
