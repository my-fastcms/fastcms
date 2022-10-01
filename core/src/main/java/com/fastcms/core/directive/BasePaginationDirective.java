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
package com.fastcms.core.directive;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.template.StaticPathHelper;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateModelException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class BasePaginationDirective extends BaseDirective {

	protected static final String PAGENO_PARAM = "page=";

	protected static final int SHOW_PAGE_NO_COUNT = 5;

	@Override
	public Object doExecute(Environment env, Map params) throws TemplateModelException {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();

		String queryString = request.getQueryString();
		String url = request.getRequestURI();

		if (StringUtils.isNotBlank(queryString)) {
			int index = queryString.indexOf(PAGENO_PARAM);
			if(index != -1){
				queryString = queryString.substring(0, index>0 ? index-1 : index);
			}
			if(StringUtils.isNotBlank(queryString))
				url = url.concat("?").concat(queryString);
		}

		Page page = (Page) RequestContextHolder.getRequestAttributes().getAttribute(getPageAttr(), RequestAttributes.SCOPE_REQUEST);

		if (page == null) {
			StringModel pageModel = (StringModel) env.getDataModelOrSharedVariable(getPageAttr());
			if (pageModel != null) {
				page = (Page) pageModel.getWrappedObject();
			}
		}

		Map<String, Object> result = new HashMap<>();

		//当前页
		long current = page == null ? 0 : page.getCurrent();
		//显示页码数
		long showCount = SHOW_PAGE_NO_COUNT;
		//页码显示开始与结束
		long start, end;
		//总页数
		long totalPage = page == null ? 0 : (page.getTotal() % page.getSize() == 0 ? page.getTotal() / page.getSize() : page.getTotal() / page.getSize() + 1);

		start = current <= showCount/2 + 1 ? 1 : current - showCount/2;
		end = start + showCount-1 >= totalPage ? totalPage : start + showCount - 1;
		if(end >= totalPage) {
			start = end - showCount + 1;
		}

		List<PaginationDirective.PageItem> pageItemList = new ArrayList<>();

		String first = url.contains("?") ? url.concat("&"+PAGENO_PARAM + 1) : url + "?"+PAGENO_PARAM + 1;
		result.put("prev", new PaginationDirective.PageItem("上一页", url.contains("?")
				? url.concat("&"+PAGENO_PARAM + (current-1 <1 ? 1: current-1))
				: url + "?"+PAGENO_PARAM + (current-1 < 1 ? 1: current-1), (current-1< 1 ? 1: current-1)));
		result.put("first", new PaginationDirective.PageItem("首页", first, 1l));

		if(start > 1) {
			pageItemList.add(new PaginationDirective.PageItem(1 + "", first, 1l));
			pageItemList.add(new PaginationDirective.PageItem("...", "javascript:void(0)", 0l));
		}

		for(long i=start; i<=end; i++) {
			if(i>0){
				pageItemList.add(new PaginationDirective.PageItem(i+"", url.contains("?") ? url.concat("&"+PAGENO_PARAM + i) : url + "?"+ PAGENO_PARAM + i, i));
			}
		}

		String last = url.contains("?") ? url.concat("&"+PAGENO_PARAM + totalPage) : url + "?"+PAGENO_PARAM + totalPage;
		result.put("next", new PaginationDirective.PageItem("下一页",
				url.contains("?")
						? url.concat("&"+PAGENO_PARAM + (current+1 > totalPage ? totalPage : current+1))
						: url + "?"+PAGENO_PARAM + (current+1 > totalPage ? totalPage : current+1),
				(current+1 > totalPage ? totalPage : current+1)));
		result.put("last", new PageItem("尾页", last, 0l));

		if(end < totalPage) {
			pageItemList.add(new PaginationDirective.PageItem("...", "javascript:void(0)", 0l));
			pageItemList.add(new PaginationDirective.PageItem(totalPage + "", last, totalPage));
		}

		result.put("list", pageItemList);
		result.put("current", current);
		result.put("total", page == null ? 0 : page.getTotal());
		result.put("totalPage", totalPage);
		return result;
	}

	protected abstract String getPageAttr();

	public class PageItem implements StaticPathHelper {
		private String text;
		private String pageUrl;
		private Long pageNo;

		public PageItem(String text, String url, Long pageNo) {
			this.text = text;
			this.pageUrl = url;
			this.pageNo = pageNo;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getPageUrl() {
			return pageUrl;
		}

		public void setPageUrl(String url) {
			this.pageUrl = url;
		}

		public Long getPageNo() {
			return pageNo;
		}

		public void setPageNo(Long pageNo) {
			this.pageNo = pageNo;
		}

		@Override
		public String getUrl() {
			return getPageLinkUrl(this);
		}

	}

	protected String getPageLinkUrl(PageItem pageItem) {
		if (pageItem.isEnableStatic()) {
			return pageItem.getWebSiteDomain().concat(pageItem.getCategoryStaticPath()).concat(StrUtils.UNDERLINE) + pageItem.pageNo + pageItem.getStaticSuffix();
		}

		if (pageItem.isEnableFakeStatic()) {
			return pageItem.getPageUrl().substring(0, pageItem.getPageUrl().indexOf("?")).concat(pageItem.getStaticSuffix()).concat(pageItem.getPageUrl().substring(pageItem.getPageUrl().indexOf("?")));
		}
		return pageItem.getPageUrl();
	}

}
