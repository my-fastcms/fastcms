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
package com.fastcms.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.ArticleComment;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleCommentService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.permission.AdminMenu;
import com.fastcms.core.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/article")
@AdminMenu(name = "文章", icon = "<i class=\"nav-icon fas fa-book\"></i>", sort = 1)
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @Autowired
    private IArticleCommentService articleCommentService;

    @AdminMenu(name = "文章管理", sort = 1)
    @RequestMapping("list")
    public String list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                       @RequestParam(name = "title", required = false) String title,
                       @RequestParam(name = "status", required = false) String status,
                       @RequestParam(name = "categoryId", required = false) String categoryId,
                       @RequestParam(name = "tagId", required = false) String tagId,
                       Model model) {
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
//        model.addAttribute(PAGE_DATA_ATTR, pageData);

//        model.addAttribute("categoryList", articleCategoryService.getCategoryList(getLoginUser().getId()));
//        model.addAttribute("tagList", articleCategoryService.getTagList(getLoginUser().getId()));

        return "admin/article/list";
    }

    @AdminMenu(name = "写文章", sort = 2)
    @RequestMapping("write")
    public String edit(@RequestParam(name = "id", required = false) Long id, Model model) {
        model.addAttribute("article", articleService.getById(id));
        model.addAttribute("tags", articleCategoryService.getArticleTagListByArticleId(id));
        model.addAttribute("categories", articleCategoryService.getArticleCategoryListByArticleId(id));
//        model.addAttribute("categoryList", articleCategoryService.getCategoryList(getLoginUser().getId()));
//        model.addAttribute("tagList", articleCategoryService.getTagList(getLoginUser().getId()));
        return "admin/article/edit";
    }

    @PostMapping("doSave")
    public ResponseEntity doSave(@Validated Article article) {
        try {
            articleService.saveArticle(article);
            return Response.success();
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @AdminMenu(name = "分类", sort = 3)
    @RequestMapping("category/list")
    public String categoryList(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                       Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page pageParam = new Page<>(page, pageSize);
        Page<IArticleCategoryService.ArticleCategoryVo> pageData = articleCategoryService.pageArticleCategory(pageParam, queryWrapper);
//        model.addAttribute(PAGE_DATA_ATTR, pageData);
        return "admin/article/category_list";
    }

    @RequestMapping("category/edit")
    public String editCategory(@RequestParam(name = "id", required = false) Long id, Model model) {
        model.addAttribute("articleCategory", articleCategoryService.getById(id));
        model.addAttribute("articleCategoryList", articleCategoryService.list());
        return "admin/article/category_edit";
    }

    @PostMapping("category/doSave")
    public ResponseEntity doSaveCategory(ArticleCategory articleCategory) {
        articleCategoryService.saveOrUpdate(articleCategory);
        return Response.success();
    }

    @PostMapping("category/doDelete")
    public ResponseEntity doDeleteCategory(@RequestParam(name = "id") Long id) {
        articleCategoryService.deleteByCategoryId(id);
        return Response.success();
    }

    @AdminMenu(name = "评论", sort = 4)
    @RequestMapping("comment/list")
    public String commentList(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                               Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page pageParam = new Page<>(page, pageSize);
        Page<IArticleCommentService.ArticleCommentVo> pageData = articleCommentService.pageArticleComment(pageParam, queryWrapper);
//        model.addAttribute(PAGE_DATA_ATTR, pageData);
        return "admin/article/comment_list";
    }

    @RequestMapping("comment/edit")
    public String editComment(@RequestParam(name = "id", required = false) Long id, Model model) {
        model.addAttribute("articleComment", articleCommentService.getById(id));
        return "admin/article/comment_edit";
    }

    @PostMapping("comment/doSave")
    public ResponseEntity doSaveComment(@Validated ArticleComment articleComment) {
        articleCommentService.saveOrUpdate(articleComment);
        return Response.success();
    }

    @PostMapping("comment/doDelete")
    public ResponseEntity doDeleteComment(@RequestParam(name = "id") Long id) {
        articleCommentService.removeById(id);
        return Response.success();
    }

}
