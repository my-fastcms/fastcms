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
package com.fastcms.cms.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.mybatis.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 文章接口
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：文章接口
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/article")
public class ArticleApi {

	@Autowired
	private IArticleService articleService;

	/**
	 * 文章列表
	 * @param page
	 * @param categoryId  	分类id
	 * @return
	 */
	@RequestMapping("list")
	public RestResult<Page<IArticleService.ArticleVo>> list(PageModel page,
															@RequestParam(name = "categoryId", required = false) Long categoryId) {

		QueryWrapper queryWrapper = Wrappers.query().eq(categoryId != null,"acr.category_id", categoryId)
				.eq("a.status", Article.STATUS_PUBLISH);
		Page<IArticleService.ArticleVo> articleVoPage = articleService.pageArticle(page.toPage(), queryWrapper);
		return RestResultUtils.success(articleVoPage);
	}

	/**
	 * 文章详情
	 * @param articleId 文章id
	 * @return
	 */
	@RequestMapping("detail/{articleId}")
	public RestResult<IArticleService.ArticleInfoVo> detail(@PathVariable("articleId") Long articleId) {
		IArticleService.ArticleInfoVo articleInfo = articleService.getArticleDetail(articleId);
		return RestResultUtils.success(articleInfo);
	}

	/**
	 * 保存文章
	 * @param article
	 * @return
	 */
	@PostMapping("save")
	public Object save(@Validated Article article) {
		try {
			if(article.getId() == null) {
				article.setStatus(Article.STATUS_AUDIT);
			}
			articleService.saveArticle(article);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

	/**
	 * 删除文章
	 * @param articleId
	 * @return
	 */
	@PostMapping("delete/{articleId}")
	public Object delete(@PathVariable("articleId") Long articleId) {
		try {
			articleService.removeById(articleId);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

}
