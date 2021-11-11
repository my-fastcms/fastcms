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
import com.fastcms.common.model.RestResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章管理接口
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

    @RequestMapping("list")
    public Object list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
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

    @PostMapping("doSave")
    public Object doSave(@Validated Article article) {
        try {
            articleService.saveArticle(article);
            return RestResultUtils.success();
        } catch (Exception e) {
            return RestResultUtils.failed(e.getMessage());
        }
    }

    @PostMapping("category/doSave")
    public Object doSaveCategory(ArticleCategory articleCategory) {
        articleCategoryService.saveOrUpdate(articleCategory);
        return RestResultUtils.success();
    }

    @PostMapping("category/doDelete")
    public Object doDeleteCategory(@RequestParam(name = "id") Long id) {
        articleCategoryService.deleteByCategoryId(id);
        return RestResultUtils.success();
    }

    @PostMapping("comment/doSave")
    public Object doSaveComment(@Validated ArticleComment articleComment) {
        articleCommentService.saveOrUpdate(articleComment);
        return RestResultUtils.success();
    }

    @PostMapping("comment/doDelete")
    public Object doDeleteComment(@RequestParam(name = "id") Long id) {
        articleCommentService.removeById(id);
        return RestResultUtils.success();
    }

}
