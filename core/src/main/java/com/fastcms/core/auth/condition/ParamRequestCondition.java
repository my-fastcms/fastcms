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
package com.fastcms.core.auth.condition;

import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author： wjun_java@163.com
 * @date： 2022/5/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class ParamRequestCondition {

	private final Set<ParamExpression> expressions;

	public ParamRequestCondition(String... expressions) {
		this.expressions = parseExpressions(expressions);
	}

	private Set<ParamExpression> parseExpressions(String... params) {
		if (ObjectUtils.isEmpty(params)) {
			return Collections.emptySet();
		}
		Set<ParamExpression> expressions = new LinkedHashSet<>(params.length);
		for (String param : params) {
			expressions.add(new ParamExpression(param));
		}
		return expressions;
	}

	public Set<ParamExpression> getExpressions() {
		return expressions;
	}

	public ParamRequestCondition getMatchingCondition(HttpServletRequest request) {
		for (ParamExpression expression : this.expressions) {
			if (!expression.match(request)) {
				return null;
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return "ParamRequestCondition{" + "expressions=" + expressions + '}';
	}

	static class ParamExpression {

		private final String name;

		private final String value;

		private final boolean isNegated;

		ParamExpression(String expression) {
			int separator = expression.indexOf('=');
			if (separator == -1) {
				this.isNegated = expression.startsWith("!");
				this.name = isNegated ? expression.substring(1) : expression;
				this.value = null;
			} else {
				this.isNegated = (separator > 0) && (expression.charAt(separator - 1) == '!');
				this.name = isNegated ? expression.substring(0, separator - 1) : expression.substring(0, separator);
				this.value = expression.substring(separator + 1);
			}
		}

		public final boolean match(HttpServletRequest request) {
			boolean isMatch;
			if (this.value != null) {
				isMatch = matchValue(request);
			} else {
				isMatch = matchName(request);
			}
			return this.isNegated != isMatch;
		}

		private boolean matchName(HttpServletRequest request) {
			return request.getParameterMap().containsKey(this.name);
		}

		private boolean matchValue(HttpServletRequest request) {
			return ObjectUtils.nullSafeEquals(this.value, request.getParameter(this.name));
		}

		@Override
		public String toString() {
			return "ParamExpression{" + "name='" + name + '\'' + ", value='" + value + '\'' + ", isNegated=" + isNegated
					+ '}';
		}
	}

}
