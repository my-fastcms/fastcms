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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

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

}
