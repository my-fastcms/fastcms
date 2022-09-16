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

import com.fastcms.common.auth.AuthConstants;

/**
 * @author： wjun_java@163.com
 * @date： 2022/5/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class PathRequestCondition {

	private final PathExpression pathExpression;

	public PathRequestCondition(String pathExpression) {
		this.pathExpression = parseExpressions(pathExpression);
	}

	private PathExpression parseExpressions(String pathExpression) {
		String[] split = pathExpression.split(AuthConstants.REQUEST_PATH_SEPARATOR);
		String method = split[0];
		String path = split[1];
		return new PathExpression(method, path);
	}

	@Override
	public String toString() {
		return "PathRequestCondition{" + "pathExpression=" + pathExpression + '}';
	}

	static class PathExpression {

		private final String method;

		private final String path;

		PathExpression(String method, String path) {
			this.method = method;
			this.path = path;
		}

		@Override
		public String toString() {
			return "PathExpression{" + "method='" + method + '\'' + ", path='" + path + '\'' + '}';
		}
	}

}
