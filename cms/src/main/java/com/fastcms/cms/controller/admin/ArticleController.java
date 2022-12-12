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
package com.fastcms.cms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.ArticleComment;
import com.fastcms.cms.entity.ArticleTag;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleCommentService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.service.IArticleTagService;
import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.utils.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fastcms.cms.service.IArticleService.ArticleI18n.CMS_ARTICLE_CHILDREN_COMMENT_NOT_DELETE;
import static com.fastcms.cms.service.IArticleService.ArticleI18n.CMS_ARTICLE_CHILDREN_NOT_DELETE;
import static com.fastcms.service.IResourceService.ResourceI18n.*;

/**
 * 文章管理
 * @author： wjun_java@163.com
 * @date： 2021/4/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleTagService articleTagService;

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @Autowired
    private IArticleCommentService articleCommentService;

    /**
     * 文章列表
     * @param page
     * @param title         文章标题
     * @param status        状态
     * @param categoryId    分类id
     * @param tagId         标签id
     * @return
     */
    @RequestMapping("list")
    @Secured(name = RESOURCE_NAME_ARTICLE_LIST, resource = "articles:list", action = ActionTypes.READ)
	public RestResult<Page<IArticleService.ArticleVo>> list(PageModel page,
                                                            @RequestParam(name = "title", required = false) String title,
                                                            @RequestParam(name = "status", required = false) String status,
                                                            @RequestParam(name = "categoryId", required = false) String categoryId,
                                                            @RequestParam(name = "tagId", required = false) String tagId) {

        QueryWrapper<Object> queryWrapper = Wrappers.query()
                .like(StrUtils.isNotBlank(title), "a.title", title)
                .eq(StrUtils.isNotBlank(status), "a.status", status)
                .eq(StrUtils.isNotBlank(categoryId), "acr.category_id", categoryId)
                .eq(StrUtils.isNotBlank(tagId), "acr.category_id", tagId)
                .orderByDesc("a.created");

        Page<IArticleService.ArticleVo> pageData = articleService.pageArticle(page.toPage(), queryWrapper);

        return RestResultUtils.success(pageData);
    }

    /**
     * 保存文章
     * @param article
     * @return
     */
    @PostMapping("save")
    @Secured(name = RESOURCE_NAME_ARTICLE_SAVE, resource = "articles:save", action = ActionTypes.WRITE)
	public RestResult<Long> save(@Validated Article article) throws FastcmsException {
        articleService.saveArticle(article);
        return RestResultUtils.success(article.getId());
    }

    /**
     * 文章详情
     * @param articleId 文章id
     * @return
     */
    @GetMapping("get/{articleId}")
    @Secured(name = RESOURCE_NAME_ARTICLE_DETAIL, resource = "articles:get", action = ActionTypes.READ)
	public RestResult<Article> getArticle(@PathVariable("articleId") Long articleId) {
        return RestResultUtils.success(articleService.getArticle(articleId));
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @PostMapping("delete/{articleId}")
    @Secured(name = RESOURCE_NAME_ARTICLE_DELETE, resource = "articles:delete", action = ActionTypes.WRITE)
	public Object deleteArticle(@PathVariable("articleId") Long articleId) {
        Article article = articleService.getById(articleId);
        if(article != null) {
            article.setStatus(Article.STATUS_DELETE);
            articleService.updateById(article);
        }
        return RestResultUtils.success();
    }

    /**
     * 保存分类
     * @param articleCategory
     * @return
     */
    @PostMapping("category/save")
    @Secured(name = RESOURCE_NAME_ARTICLE_CATEGORY_SAVE, resource = "articles:category/save", action = ActionTypes.WRITE)
	public RestResult<Boolean> saveCategory(@Validated ArticleCategory articleCategory) {
        return RestResultUtils.success(articleCategoryService.saveOrUpdate(articleCategory));
    }

    /**
     * 分类列表
     * @description
     * @return
     */
    @GetMapping("category/list")
	public RestResult<List<IArticleCategoryService.CategoryTreeNode>> listCategory() {
        return RestResultUtils.success(articleCategoryService.getCategoryList());
    }

    /**
     * 删除分类
     * @param categoryId
     * @return
     */
    @PostMapping("category/delete/{categoryId}")
    @Secured(name = RESOURCE_NAME_ARTICLE_CATEGORY_DELETE, resource = "articles:category/delete", action = ActionTypes.WRITE)
	public Object deleteCategory(@PathVariable("categoryId") Long categoryId) {

        List<ArticleCategory> categoryList = articleCategoryService.list(Wrappers.<ArticleCategory>lambdaQuery().eq(ArticleCategory::getParentId, categoryId));
        if(categoryList != null && categoryList.size() >0) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_ARTICLE_CHILDREN_NOT_DELETE));
        }

        articleCategoryService.deleteByCategoryId(categoryId);
        return RestResultUtils.success();
    }

    /**
     * 保存标签
     * @param articleTag
     * @return
     */
    @PostMapping("tag/save")
    @Secured(name = RESOURCE_NAME_ARTICLE_TAG_SAVE, resource = "articles:tag/save", action = ActionTypes.WRITE)
    public RestResult<Boolean> saveCategory(@Validated ArticleTag articleTag) {
        return RestResultUtils.success(articleTagService.saveOrUpdate(articleTag));
    }

    /**
     * 标签列表
     * @description
     * @return
     */
    @GetMapping("tag/list")
	public RestResult<List<ArticleTag>> listTags() {
        return RestResultUtils.success(articleTagService.list());
    }

    /**
     * 删除标签
     * @param tagId
     * @return
     */
    @PostMapping("tag/delete/{tagId}")
    @Secured(name = RESOURCE_NAME_ARTICLE_TAG_DELETE, resource = "articles:tag/delete", action = ActionTypes.WRITE)
    public Object deleteTag(@PathVariable("tagId") Long tagId) {
        articleTagService.deleteByTagId(tagId);
        return RestResultUtils.success();
    }

    /**
     * 评论列表
     * @param page
     * @param author
     * @param content
     * @return
     */
    @GetMapping("comment/list")
    @Secured(name = RESOURCE_NAME_ARTICLE_COMMENT_LIST, resource = "articles:comment/list", action = ActionTypes.READ)
	public Object getCommentList(PageModel page, String author, String content, Boolean isParent) {
        QueryWrapper<Object> queryWrapper = Wrappers.query().eq(StringUtils.isNotBlank(author), "u.user_name", author)
                .eq(isParent != null && isParent == true, "ac.parentId", 0)
                .likeLeft(StringUtils.isNotBlank(content), "ac.content", content)
                .orderByDesc("ac.created");
        return RestResultUtils.success(articleCommentService.pageArticleComment(page.toPage(), queryWrapper));
    }

    /**
     * 保存评论
     * @param articleComment
     * @return
     */
    @PostMapping("comment/save")
    @Secured(name = RESOURCE_NAME_ARTICLE_COMMENT_SAVE, resource = "articles:comment/save", action = ActionTypes.WRITE)
	public RestResult<Boolean> saveComment(@Validated ArticleComment articleComment) {
        return RestResultUtils.success(articleCommentService.saveOrUpdate(articleComment));
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @PostMapping("comment/delete/{commentId}")
    @Secured(name = RESOURCE_NAME_ARTICLE_COMMENT_DELETE, resource = "articles:comment/delete/", action = ActionTypes.WRITE)
	public Object doDeleteComment(@PathVariable("commentId") Long commentId) {
        List<ArticleComment> articleCommentList = articleCommentService.list(Wrappers.<ArticleComment>lambdaQuery().eq(ArticleComment::getParentId, commentId));
        if(articleCommentList != null && articleCommentList.size() >0) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_ARTICLE_CHILDREN_COMMENT_NOT_DELETE));
        }
        articleCommentService.removeById(commentId);
        return RestResultUtils.success();
    }

}
