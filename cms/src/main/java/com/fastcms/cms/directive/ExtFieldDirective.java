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
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.directive.BaseFunction;
import com.fastcms.utils.CollectionUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取文章扩展字段属性值
 * 参数1：文章id
 * 参数2：文章字段
 *
 * ${fieldValue("1", "price")}
 *
 * @author： wjun_java@163.com
 * @date： 2022/11/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("fieldValue")
public class ExtFieldDirective extends BaseFunction {

    @Autowired
    private IArticleService articleService;

    @Override
    public Object exec(List arguments) throws TemplateModelException {

        if (CollectionUtils.isEmpty(arguments)) {
            return StrUtils.EMPTY;
        }

        if (arguments.size() != 2) {
            return StrUtils.EMPTY;
        }

        SimpleScalar simpleScalar = (SimpleScalar) arguments.get(0);
        String articleId = simpleScalar.getAsString();
        if(StringUtils.isBlank(articleId)) {
            return StrUtils.EMPTY;
        }

        simpleScalar = (SimpleScalar) arguments.get(1);
        String field = simpleScalar.getAsString();
        if(StringUtils.isBlank(field)) {
            return StrUtils.EMPTY;
        }

        Article article = articleService.getById(articleId);
        if (article == null) {
            return StrUtils.EMPTY;
        }

        return ArticleUtils.getFieldProperty(article, field);
    }

}
