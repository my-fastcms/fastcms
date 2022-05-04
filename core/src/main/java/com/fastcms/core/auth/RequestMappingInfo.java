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
package com.fastcms.core.auth;

import com.fastcms.core.auth.condition.ParamRequestCondition;
import com.fastcms.core.auth.condition.PathRequestCondition;

import java.util.Comparator;

/**
 * @author： wjun_java@163.com
 * @date： 2022/5/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class RequestMappingInfo {

	private PathRequestCondition pathRequestCondition;

	private ParamRequestCondition paramRequestCondition;

	public ParamRequestCondition getParamRequestCondition() {
		return paramRequestCondition;
	}

	public void setParamRequestCondition(ParamRequestCondition paramRequestCondition) {
		this.paramRequestCondition = paramRequestCondition;
	}

	public void setPathRequestCondition(PathRequestCondition pathRequestCondition) {
		this.pathRequestCondition = pathRequestCondition;
	}

	@Override
	public String toString() {
		return "RequestMappingInfo{" + "pathRequestCondition=" + pathRequestCondition + ", paramRequestCondition="
				+ paramRequestCondition + '}';
	}

	public static class RequestMappingInfoComparator implements Comparator<RequestMappingInfo> {

		@Override
		public int compare(RequestMappingInfo o1, RequestMappingInfo o2) {
			return Integer.compare(o2.getParamRequestCondition().getExpressions().size(),
					o1.getParamRequestCondition().getExpressions().size());
		}
	}

}
