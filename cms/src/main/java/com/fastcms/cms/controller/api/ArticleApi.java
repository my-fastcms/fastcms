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
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleTag;
import com.fastcms.cms.search.FastcmsSearcherManager;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.service.IArticleTagService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.plugin.PassFastcms;
import com.fastcms.utils.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.fastcms.cms.service.IArticleService.ArticleI18n.*;

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

	@Autowired
	private FastcmsSearcherManager fastcmsSearcherManager;

	@Autowired
	private IArticleTagService articleTagService;

	@Autowired
	private IArticleCategoryService articleCategoryService;

	/**
	 * 文章列表
	 * @param page
	 * @param categoryId
	 * @param status
	 * @return
	 */
	@GetMapping("list")
	@PassFastcms
	public RestResult<Page<IArticleService.ArticleVo>> list(PageModel page,
															@RequestParam(name = "categoryId", required = false) String categoryId,
															@RequestParam(name = "status", required = false) String status) {
		QueryWrapper<Object> queryWrapper = Wrappers.query()
				.eq(StringUtils.isNotBlank(categoryId), "acr.category_id", categoryId)
				.eq("a.status", StringUtils.isNotBlank(status) ? status : Article.STATUS_PUBLISH)
				.orderByDesc("a.created");
		Page<IArticleService.ArticleVo> articleVoPage = articleService.pageArticle(page.toPage(), queryWrapper);
		return RestResultUtils.success(articleVoPage);
	}

	/**
	 * 保存文章
	 * @param article
	 * @return
	 */
	@PostMapping("save")
	public Object save(@Validated Article article) throws FastcmsException {
		if(article.getId() == null) {
			article.setStatus(Article.STATUS_AUDIT);
		} else {
			if(!Objects.equals(article.getCreateUserId(), AuthUtils.getUserId())) {
				return RestResultUtils.failed(I18nUtils.getMessage(CMS_ARTICLE_IS_ALLOW_SELF_UPDATE));
			}
		}

		articleService.saveArticle(article);
		return RestResultUtils.success();
	}

	/**
	 * 文章详情
	 * @param articleId 文章id
	 * @return
	 */
	@GetMapping("get/{articleId}")
	@PassFastcms
	public RestResult<Article> getArticle(@PathVariable("articleId") Long articleId) {
		return RestResultUtils.success(articleService.getArticle(articleId));
	}

	/**
	 * 删除文章
	 * @param articleId
	 * @return
	 */
	@PostMapping("delete/{articleId}")
	public Object delete(@PathVariable("articleId") Long articleId) {
		Article article = articleService.getById(articleId);
		if(article == null) {
			return RestResultUtils.failed(I18nUtils.getMessage(CMS_ARTICLE_IS_NOT_EXIST));
		}

		if(!Objects.equals(AuthUtils.getUserId(), article.getCreateUserId())) {
			return RestResultUtils.failed(I18nUtils.getMessage(CMS_ARTICLE_IS_ALLOW_SELF_DELETE));
		}

		article.setStatus(Article.STATUS_DELETE);
		articleService.updateById(article);
		return RestResultUtils.success();
	}

	/**
	 * 文章搜索
	 * @param page
	 * @param keyword
	 * @return
	 */
	@GetMapping("search")
	public RestResult<Page<Article>> search(PageModel page, @RequestParam(name = "keyword") String keyword) {
		return RestResultUtils.success(fastcmsSearcherManager.getSearcher().search(keyword, page.getPageNum().intValue(), page.getPageSize().intValue()));
	}

	/**
	 * 文章分类
	 * @description
	 * @return
	 */
	@GetMapping("category/list")
	@PassFastcms
	public RestResult<List<IArticleCategoryService.CategoryTreeNode>> listCategory() {
		return RestResultUtils.success(articleCategoryService.getCategoryList());
	}

	/**
	 * 文章标签
	 * @return
	 */
	@GetMapping("tag/list")
	@PassFastcms
	public RestResult<List<ArticleTag>> listTags() {
		return RestResultUtils.success(articleTagService.list());
	}

}
