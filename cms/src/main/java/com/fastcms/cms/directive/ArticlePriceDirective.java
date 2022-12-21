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

import com.fastcms.cms.entity.Article;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.utils.ArticleUtils;
import com.fastcms.core.directive.BaseFunction;
import com.fastcms.utils.I18nUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.fastcms.cms.service.IArticleService.ArticleI18n.CMS_ARTICLE_IS_NOT_EXIST;

/**
 * 获取文章价格
 * 参数1：文章id
 * 参数2：文章价格实现类
 * ${price("1", "com.fastcms.cms.IArticlePriceService")}
 *
 * @author： wjun_java@163.com
 * @date： 2022/10/26
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("price")
public class ArticlePriceDirective extends BaseFunction {

    @Autowired
    private IArticleService articleService;

    @Override
    public Object exec(List arguments) throws TemplateModelException {

        SimpleScalar simpleScalar = (SimpleScalar) arguments.get(0);
        String articleId = simpleScalar.getAsString();
        if(StringUtils.isBlank(articleId)) return "";

        Article article = articleService.getById(articleId);
        if (article == null) {
            throw new TemplateModelException(I18nUtils.getMessage(CMS_ARTICLE_IS_NOT_EXIST));
        }

        if (arguments.size() == 2) {
            simpleScalar = (SimpleScalar) arguments.get(1);
            String priceClass = simpleScalar.getAsString();
            article.setPriceClass(priceClass);
        }

        return ArticleUtils.getPrice(article);
    }

}
