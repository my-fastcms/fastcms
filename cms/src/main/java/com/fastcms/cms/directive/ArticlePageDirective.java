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

import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.directive.BasePaginationDirective;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文章分页标签
 *
 * <@articlePageTag>
 *
 *     <div class="pagelist">
 *         <a title="Total record">&nbsp;<b>${data.total}</b> </a>&nbsp;&nbsp;&nbsp;
 *         <a href="${data.prev.url!}">${data.prev.text!}</a>&nbsp;
 *         <#list data.list as item>
 *             <#if data.current?string == item.text>
 *                 <b>1</b>&nbsp;&nbsp;&nbsp;
 *             <#else>
 *                 <a href="${item.url!}">${item.text!}</a>&nbsp;&nbsp;
 *             </#if>
 *         </#list>
 *         <a href="${data.next.url!}">${data.next.text!}</a>&nbsp;
 *         <a href="${data.last.url!}">${data.last.text!}</a>
 *     </div>
 *
 * </@articlePageTag>
 *
 * @author： wjun_java@163.com
 * @date： 2021/5/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("articlePageTag")
public class ArticlePageDirective extends BasePaginationDirective {

	private static final String PAGE_ATTR = "articleVoPage";

	private static final String CATEGORY_ATTR = "category";

	Environment env;

	@Override
	public Object doExecute(Environment env, Map params) throws TemplateModelException {
		this.env = env;
		return super.doExecute(env, params);
	}

	@Override
	protected String getPageAttr() {
		return PAGE_ATTR;
	}

	@Override
	protected String getPageLinkUrl(PageItem pageItem) {

		ArticleCategory category;
		try {
			StringModel categoryModel = (StringModel) env.getDataModelOrSharedVariable(CATEGORY_ATTR);
			category = (ArticleCategory) categoryModel.getWrappedObject();
		} catch (Exception e) {
			return pageItem.getPageUrl();
		}

		if (category == null) {
			return pageItem.getPageUrl();
		}

		if (pageItem.isEnableStatic()) {
			return pageItem.getWebSiteDomain().concat(pageItem.getCategoryStaticPath()).concat(category.getId().toString()).concat(StrUtils.UNDERLINE) + pageItem.getPageNo() + pageItem.getStaticSuffix();
		}

		if (pageItem.isEnableFakeStatic()) {
			return pageItem.getPageUrl().substring(0, pageItem.getPageUrl().indexOf("?")).concat(pageItem.getStaticSuffix()).concat(pageItem.getPageUrl().substring(pageItem.getPageUrl().indexOf("?")));
		}
		return pageItem.getPageUrl();

	}

}
