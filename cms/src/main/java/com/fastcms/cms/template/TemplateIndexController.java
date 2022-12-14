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
package com.fastcms.cms.template;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.ArticleTag;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.service.IArticleTagService;
import com.fastcms.cms.service.ISinglePageService;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.template.FastcmsStaticHtmlManager;
import com.fastcms.utils.ApplicationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @ignore
 * Freemarker模板
 * @author： wjun_java@163.com
 * @date： 2021/2/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Controller
public class TemplateIndexController extends TemplateBaseController {

    static final String UNDERLINE = StrUtils.UNDERLINE;
    static final String INDEX = "index";
    static final String DEFAULT_PAGE_VIEW = "page";
    static final String DEFAULT_ARTICLE_VIEW = "article";
    static final String DEFAULT_ARTICLE_LIST_VIEW = "article_list";

    static final String SINGLE_PAGE_ATTR = "singlePage";
    static final String ARTICLE_ATTR = "article";
    static final String CATEGORY_ATTR = "category";
    static final String ARTICLE_VO_PAGE_ATTR = "articleVoPage";
    static final String TAG_ATTR = "tag";

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ISinglePageService singlePageService;

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @Autowired
    private IArticleTagService articleTagService;

    @RequestMapping({"/", "index"})
    public String index() {
        if (ApplicationUtils.getBean(FastcmsStaticHtmlManager.class).isEnable()) {
            return UrlBasedViewResolver.FORWARD_URL_PREFIX.concat(getTemplate().getPath()).concat("index.html");
        }

        return getTemplatePath().concat(INDEX);
    }

    @RequestMapping("p/{path}")
    public String path(@PathVariable("path") String path, Model model) {
        SinglePage singlePage = singlePageService.getPageByPath(path);
        if(singlePage == null) {
            singlePage = singlePageService.getById(path);
        }

        if(singlePage != null && SinglePage.STATUS_PUBLISH.equals(singlePage.getStatus())) {
            model.addAttribute(SINGLE_PAGE_ATTR, singlePage);
        }

        String view = getTemplatePath() + DEFAULT_PAGE_VIEW;
        if(singlePage != null && StringUtils.isNotBlank(singlePage.getSuffix())) {
            view = view.concat(UNDERLINE).concat(singlePage.getSuffix());
        }

        return view;
    }

    @RequestMapping("a/{id}")
    public String article(@PathVariable("id") Long id, Model model) {
        IArticleService.ArticleInfoVo article = articleService.getArticleDetail(id);

        if(article != null && Article.STATUS_PUBLISH.equals(article.getStatus())) {
            model.addAttribute(ARTICLE_ATTR, article);
        }

        String view = getTemplatePath() + DEFAULT_ARTICLE_VIEW;
        if(article != null && StringUtils.isNotBlank(article.getSuffix())) {
            view = view.concat(UNDERLINE).concat(article.getSuffix());
        }

        return view;
    }

    @RequestMapping("a/c/{id}")
    public String category(@PathVariable("id") String id,
                           @RequestParam(name = "page", required = false, defaultValue = "1") int pageNo,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                           Model model) {

        ArticleCategory articleCategory = articleCategoryService.getById(id);
        if(articleCategory == null) {
            articleCategory = articleCategoryService.getOne(Wrappers.<ArticleCategory>lambdaQuery().eq(ArticleCategory::getPath, id), false);
        }

        if(articleCategory != null) {
            model.addAttribute(CATEGORY_ATTR, articleCategory);
            QueryWrapper<Object> queryWrapper = Wrappers.query().eq("acr.category_id", articleCategory.getId()).eq("a.status", Article.STATUS_PUBLISH).orderByDesc("a.created");
            Page<IArticleService.ArticleVo> articleVoPage = articleService.pageArticleByCategoryId(new Page(pageNo, pageSize), queryWrapper);
            model.addAttribute(ARTICLE_VO_PAGE_ATTR, articleVoPage);
        }

        String view = getTemplatePath() + DEFAULT_ARTICLE_LIST_VIEW;

        if(articleCategory != null && StringUtils.isNotBlank(articleCategory.getSuffix())) {
            view = view.concat(UNDERLINE).concat(articleCategory.getSuffix());
        }

        return view;
    }

    @RequestMapping("a/t/{id}")
    public String tag(@PathVariable("id") String id,
                           @RequestParam(name = "page", required = false, defaultValue = "1") int pageNo,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                           Model model) {

        ArticleTag articleTag = articleTagService.getById(id);

        if(articleTag != null) {
            model.addAttribute(TAG_ATTR, articleTag);
            QueryWrapper<Object> queryWrapper = Wrappers.query().eq("acr.tag_id", articleTag.getId()).eq("a.status", Article.STATUS_PUBLISH).orderByDesc("a.created");
            Page<IArticleService.ArticleVo> articleVoPage = articleService.pageArticleByTagId(new Page(pageNo, pageSize), queryWrapper);
            model.addAttribute(ARTICLE_VO_PAGE_ATTR, articleVoPage);
        }

        String view = getTemplatePath() + DEFAULT_ARTICLE_LIST_VIEW;

        if(articleTag != null && StringUtils.isNotBlank(articleTag.getSuffix())) {
            view = view.concat(UNDERLINE).concat(articleTag.getSuffix());
        }

        return view;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {}

}
