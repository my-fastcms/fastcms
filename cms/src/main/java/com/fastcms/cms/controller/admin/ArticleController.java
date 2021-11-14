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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.ArticleComment;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleCommentService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private IArticleCategoryService articleCategoryService;

    @Autowired
    private IArticleCommentService articleCommentService;

    /**
     * 文章列表
     * @param page
     * @param pageSize
     * @param title
     * @param status
     * @param categoryId
     * @param tagId
     * @return
     */
    @RequestMapping("list")
    public RestResult<Page<IArticleService.ArticleVo>> list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                                                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                                                            @RequestParam(name = "title", required = false) String title,
                                                            @RequestParam(name = "status", required = false) String status,
                                                            @RequestParam(name = "categoryId", required = false) String categoryId,
                                                            @RequestParam(name = "tagId", required = false) String tagId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(title)) {
            queryWrapper.like("a.title", title);
        }
        if(StringUtils.isNotBlank(status)) {
            queryWrapper.eq("a.status", status);
        }
        if(StringUtils.isNotBlank(categoryId)) {
            queryWrapper.eq("acr.category_id", categoryId);
        }
        if(StringUtils.isNotBlank(tagId)) {
            queryWrapper.eq("acr.category_id", tagId);
        }
        queryWrapper.orderByDesc("a.created");
        Page pageParam = new Page<>(page, pageSize);
        Page<IArticleService.ArticleVo> pageData = articleService.pageArticle(pageParam, queryWrapper);

        return RestResultUtils.success(pageData);
    }

    /**
     * 保存文章
     * @param article
     * @return
     */
    @PostMapping("save")
    public Object save(@Validated Article article) {
        try {
            articleService.saveArticle(article);
            return RestResultUtils.success();
        } catch (Exception e) {
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 文章详情
     * @param articleId 文章id
     * @return
     */
    @GetMapping("{articleId}")
    public RestResult<Article> getArticle(@PathVariable("articleId") Long articleId) {
        return RestResultUtils.success(articleService.getById(articleId));
    }

    /**
     * 保存分类
     * @param articleCategory
     * @return
     */
    @PostMapping("category/save")
    public RestResult<Boolean> saveCategory(ArticleCategory articleCategory) {
        return RestResultUtils.success(articleCategoryService.saveOrUpdate(articleCategory));
    }

    /**
     * 删除分类
     * @param categoryId
     * @return
     */
    @PostMapping("category/delete/{categoryId}")
    public Object deleteCategory(@PathVariable("categoryId") Long categoryId) {
        articleCategoryService.deleteByCategoryId(categoryId);
        return RestResultUtils.success();
    }

    /**
     * 保存评论
     * @param articleComment
     * @return
     */
    @PostMapping("comment/save")
    public RestResult<Boolean> saveComment(@Validated ArticleComment articleComment) {
        return RestResultUtils.success(articleCommentService.saveOrUpdate(articleComment));
    }

    /**
     * 删除评论
     * @param PathVariable
     * @return
     */
    @PostMapping("comment/delete/{commentId}")
    public Object doDeleteComment(@PathVariable("PathVariable") Long PathVariable) {
        articleCommentService.removeById(PathVariable);
        return RestResultUtils.success();
    }

}
