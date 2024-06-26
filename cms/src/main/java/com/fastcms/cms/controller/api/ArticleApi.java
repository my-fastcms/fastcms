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
import com.fastcms.cms.entity.ArticleTag;
import com.fastcms.cms.search.FastcmsSearcherManager;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.service.IArticleTagService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.utils.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
	 * @param tagId
	 * @param orderBy
	 * @return
	 */
	@GetMapping("list")
	public RestResult<Page<IArticleService.ArticleVo>> list(PageModel page,
															@RequestParam(name = "categoryId", required = false) String categoryId,
															@RequestParam(name = "tagId", required = false) String tagId,
															@RequestParam(name = "orderBy", required = false, defaultValue = "a.created") String orderBy) {
		QueryWrapper<Object> queryWrapper = Wrappers.query()
				.eq(StrUtils.isNotBlank(categoryId), "acr.category_id", categoryId)
				.eq(StrUtils.isNotBlank(tagId), "atr.tag_id", tagId)
				.orderByDesc(orderBy);

		Page<IArticleService.ArticleVo> articleVoPage = articleService.pageArticle(page.toPage(), queryWrapper);
		return RestResultUtils.success(articleVoPage);
	}

	/**
	 * 文章列表（open）
	 * @param page
	 * @param categoryId
	 * @return
	 */
	@GetMapping("list/open")
	public RestResult<Page<IArticleService.ArticleVo>> listOpen(PageModel page,
															@RequestParam(name = "categoryId", required = false) String categoryId,
															@RequestParam(name = "tagId", required = false) String tagId,
															@RequestParam(name = "orderBy", required = false, defaultValue = "a.created") String orderBy) {
		QueryWrapper<Object> queryWrapper = Wrappers.query()
				.eq(StrUtils.isNotBlank(categoryId), "acr.category_id", categoryId)
				.eq(StrUtils.isNotBlank(tagId), "atr.tag_id", tagId)
				.eq("a.status", Article.STATUS_PUBLISH)
				.orderByDesc(orderBy);

		Page<IArticleService.ArticleVo> articleVoPage = articleService.pageArticleOpen(page.toPage(), queryWrapper);
		return RestResultUtils.success(articleVoPage);
	}

	/**
	 * 根据多个分类获取文章列表
	 * @param categoryIds 多个分类以英文逗号隔开
	 * @param count
	 * @param orderBy
	 * @return
	 */
	@GetMapping("list/category/ids")
	public RestResult<List<IArticleService.ArticleVo>> listCategories(
																@RequestParam(name = "categoryIds") String categoryIds,
																@RequestParam(name = "count", required = false, defaultValue = "20") Integer count,
																@RequestParam(name = "orderBy", required = false, defaultValue = "a.created") String orderBy) {
		List<IArticleService.ArticleVo> articleVoList = articleService.getArticleListByCategoryId(Arrays.stream(categoryIds.split(StrUtils.COMMA)).map(Long::valueOf).collect(Collectors.toList()), count, orderBy);
		return RestResultUtils.success(articleVoList);
	}

	/**
	 * 根据多个标签获取文章列表
	 * @param tagIds 多个标签以英文逗号隔开
	 * @param count
	 * @param orderBy
	 * @return
	 */
	@GetMapping("list/tag/ids")
	public RestResult<List<IArticleService.ArticleVo>> listTags(
			@RequestParam(name = "tagIds") String tagIds,
			@RequestParam(name = "count", required = false, defaultValue = "20") Integer count,
			@RequestParam(name = "orderBy", required = false, defaultValue = "a.created") String orderBy) {
		List<IArticleService.ArticleVo> articleVoList = articleService.getArticleListByTagId(Arrays.stream(tagIds.split(StrUtils.COMMA)).map(Long::valueOf).collect(Collectors.toList()), count, orderBy);
		return RestResultUtils.success(articleVoList);
	}

	/**
	 * 根据标签获取文章列表
	 * @param tagId
	 * @param includeTagIds 	多个以逗号隔开
	 * @param excludeTagIds 	多个以逗号隔开
	 * @param count
	 * @return
	 */
	@GetMapping("list/tagId")
	public RestResult<List<IArticleService.ArticleVo>> list(@RequestParam(name = "tagId") Long tagId,
															@RequestParam(required = false) String includeTagIds,
															@RequestParam(required = false) String excludeTagIds,
															@RequestParam(name = "count", required = false) Integer count) {
		List<Long> includeTagIdsList = StrUtils.isBlank(includeTagIds) ? new ArrayList<>() : Arrays.stream(includeTagIds.split(StrUtils.COMMA)).map(Long::valueOf).collect(Collectors.toList());
		List<Long> excludeTagIdsList = StrUtils.isBlank(excludeTagIds) ? new ArrayList<>() : Arrays.stream(excludeTagIds.split(StrUtils.COMMA)).map(Long::valueOf).collect(Collectors.toList());
		return RestResultUtils.success(articleService.getArticleListByTagId(tagId, includeTagIdsList, excludeTagIdsList, count, "a.created"));
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
	public RestResult<Article> getArticle(@PathVariable("articleId") Long articleId) {
		return RestResultUtils.success(articleService.getArticleDetail(articleId));
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
	public RestResult<List<IArticleCategoryService.CategoryTreeNode>> listCategory() {
		return RestResultUtils.success(articleCategoryService.getCategoryList());
	}

	/**
	 * 文章标签
	 * @return
	 */
	@GetMapping("tag/list")
	public RestResult<List<ArticleTag>> listTags() {
		return RestResultUtils.success(articleTagService.list());
	}

}
