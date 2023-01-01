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
package com.fastcms.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class JsoupUtils {

	public static String parse(String contentHtml) {
		Document doc = Jsoup.parse(contentHtml);
		doc.outputSettings().prettyPrint(false);
		doc.outputSettings().outline(false);

		Elements imgElements = doc.select("img");
		if(!imgElements.isEmpty()) {
			replace(imgElements, "src");
		}
		return doc.body().html();
	}

	private static void replace(Elements elements, String attrName) {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String src = element.attr(attrName);
			if(!src.startsWith("http")) {
				element.attr(attrName, src);
			}
		}
	}

	public static String getFirstImageSrc(String html) {
		return getFirstQuerySrc(html, "img");
	}

	public static String getFirstVideoSrc(String html) {
		return getFirstQuerySrc(html, "video");
	}

	public static String getFirstAudioSrc(String html) {
		return getFirstQuerySrc(html, "audio");
	}

	public static String getFirstQuerySrc(String html, String query) {
		if (StrUtils.isBlank(html)) {
			return null;
		}

		Elements es = Jsoup.parseBodyFragment(html).select(query);
		if (es != null && es.size() > 0) {
			String src = es.first().attr("src");
			return StrUtils.isBlank(src) ? null : src;
		}

		return null;
	}


	public static List<String> getImageSrcs(String html) {
		if (StrUtils.isBlank(html)) {
			return null;
		}

		List<String> list = new ArrayList<String>();

		Document doc = Jsoup.parseBodyFragment(html);
		Elements es = doc.select("img");
		if (es != null && es.size() > 0) {
			for (Element e : es) {
				String src = e.attr("src");
				if (StrUtils.isNotBlank(src)) {
					list.add(src);
				}
			}
		}
		return list.isEmpty() ? null : list;
	}

	public static String getText(String html) {
		if (StrUtils.isBlank(html)) {
			return html;
		}
		return StrUtils.escapeHtml(Jsoup.parse(html).text());
	}

	private static MyWhitelist whitelist = new MyWhitelist();

	public static String clean(String html) {
		if (StrUtils.isNotBlank(html)) {
			return Jsoup.clean(html, whitelist);
		}

		return html;
	}

	public static class MyWhitelist extends org.jsoup.safety.Safelist {

		public MyWhitelist() {

			addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "span", "embed", "object", "dl", "dt",
					"em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small",
					"strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul");

			addAttributes("a", "href", "title", "target");
			addAttributes("blockquote", "cite");
			addAttributes("col", "span");
			addAttributes("colgroup", "span");
			addAttributes("img", "align", "alt", "src", "title");
			addAttributes("ol", "start");
			addAttributes("q", "cite");
			addAttributes("table", "summary");
			addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width");
			addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width");
			addAttributes("video", "src", "autoplay", "controls", "loop", "muted", "poster", "preload");
			addAttributes("object", "width", "height", "classid", "codebase");
			addAttributes("param", "name", "value");
			addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess", "flashvars", "name", "type", "pluginspage");

			addAttributes(":all", "class", "style", "height", "width", "type", "id", "name");

//
			addProtocols("blockquote", "cite", "http", "https");
			addProtocols("cite", "cite", "http", "https");
			addProtocols("q", "cite", "http", "https");

			//如果添加以下的协议，那么href 必须是http、 https 等开头，相对路径则被过滤掉了
			//addProtocols("a", "href", "ftp", "http", "https", "mailto", "tel");

			//如果添加以下的协议，那么src必须是http 或者 https 开头，相对路径则被过滤掉了，
			//所以必须注释掉，允许相对路径的图片资源
			//addProtocols("img", "src", "http", "https");
		}

		@Override
		protected boolean isSafeAttribute(String tagName, Element el, Attribute attr) {

			//不允许 javascript 开头的 src 和 href
			if ("src".equalsIgnoreCase(attr.getKey()) || "href".equalsIgnoreCase(attr.getKey())) {
				String value = attr.getValue();
				if (StrUtils.isNotBlank(value) && value.toLowerCase().startsWith("javascript")) {
					return false;
				}
			}


			//允许 base64 的图片内容
			if ("img".equals(tagName) && "src".equals(attr.getKey()) && attr.getValue().startsWith("data:;base64")){
				return true;
			}

			return super.isSafeAttribute(tagName, el, attr);
		}
	}

}
