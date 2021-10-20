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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.service.ISinglePageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Controller
public class TemplateIndexController extends TemplateBaseController {

    static final String INDEX = "index";
    static final String DEFAULT_PAGE_VIEW = "page";
    static final String DEFAULT_ARTICLE_VIEW = "article";
    static final String DEFAULT_ARTICLE_LIST_VIEW = "article_list";

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ISinglePageService singlePageService;

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @RequestMapping({"/", "index"})
    public String index() {
        return getTemplatePath() + INDEX;
    }

    @RequestMapping({"/{file}"})
    public String index(@PathVariable("file") String file) {
        if(StringUtils.isBlank(file) || !file.contains(".txt")) {
            return UrlBasedViewResolver.REDIRECT_URL_PREFIX  + INDEX;
        }
        return getTemplatePath() + file.substring(0, file.lastIndexOf("."));
    }

    @RequestMapping("p/{path}")
    public String path(@PathVariable("path") String path, Model model) {
        SinglePage singlePage = singlePageService.getPageByPath(path);
        if(singlePage == null) {
            singlePage = singlePageService.getById(path);
        }
        model.addAttribute("singlePage", singlePage);

        String view = getTemplatePath() + DEFAULT_PAGE_VIEW;
        if(singlePage != null && StringUtils.isNotBlank(singlePage.getSuffix())) {
            view = view + singlePage.getSuffix();
        }

        return view;
    }

    @RequestMapping("a/{id}")
    public String article(@PathVariable("id") Long id, Model model) {
        IArticleService.ArticleInfoVo article = articleService.getArticleById(id);
        model.addAttribute("article", article);

        String view = getTemplatePath() + DEFAULT_ARTICLE_VIEW;
        if(article != null && StringUtils.isNotBlank(article.getSuffix())) {
            view = view + "_" + article.getSuffix();
        }

        return view;
    }

    @RequestMapping("a/c/{id}")
    public String category(@PathVariable("id") String id,
                           @RequestParam(name = "page", required = false, defaultValue = "1") int pageNo,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                           Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("acr.category_id", id);
        queryWrapper.eq("a.status", Article.STATUS_PUBLISH);
        Page<IArticleService.ArticleVo> articleVoPage = articleService.pageArticleByCategoryId(new Page(pageNo, pageSize), queryWrapper);
        model.addAttribute("articleVoPage", articleVoPage);

        ArticleCategory articleCategory = articleCategoryService.getById(id);
        model.addAttribute("category", articleCategory);

        String view = getTemplatePath() + DEFAULT_ARTICLE_LIST_VIEW;

        if(articleCategory != null && StringUtils.isNotBlank(articleCategory.getSuffix())) {
            view = view + "_" + articleCategory.getSuffix();
        }

        return view;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {}

}
