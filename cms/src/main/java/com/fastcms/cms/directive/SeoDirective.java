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
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.directive.BaseFunction;
import com.fastcms.utils.ConfigUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * "website_title";
 * "website_seo";
 * "public_website_domain";
 *
 * ${seoTag("website_title")!""}
 *
 * @author： wjun_java@163.com
 * @date： 2021/5/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("seoTag")
public class SeoDirective extends BaseFunction {

	@Override
	public Object exec(List arguments) throws TemplateModelException {

		SimpleScalar simpleScalar = (SimpleScalar) arguments.get(0);
		String key = simpleScalar.getAsString();
		if(StringUtils.isBlank(key)) return "";

		HttpServletRequest request = getRequest();
		SinglePage singlePage = (SinglePage) request.getAttribute("singlePage");
		if(singlePage != null) {
			if(FastcmsConstants.WEBSITE_TITLE_KEY.equals(key.trim())) {
				return singlePage.getSeoKeywords();
			}

			if(FastcmsConstants.WEBSITE_SEO.equals(key.trim())) {
				return singlePage.getSeoDescription();
			}
		}

		Article article = (Article) request.getAttribute("article");
		if(article != null) {
			if(FastcmsConstants.WEBSITE_TITLE_KEY.equals(key.trim())) {
				return article.getSeoKeywords();
			}

			if(FastcmsConstants.WEBSITE_SEO.equals(key.trim())) {
				return article.getSeoDescription();
			}
		}

		return ConfigUtils.getConfig(key);
	}

}
