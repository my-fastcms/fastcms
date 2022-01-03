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
package com.fastcms.core.mybatis;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.fastcms.auth.AuthUtils;
import com.fastcms.auth.FastcmsUserDetails;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsDataPermissionHandler implements DataPermissionHandler {

	static final String USER_COLUMN = "user_id";

	@Override
	public Expression getSqlSegment(Expression where, String mappedStatementId) {

		String className = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			if(clazz == null) {
				return where;
			}
		}

		String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
		Method[] methods = clazz.getDeclaredMethods();

		Method myMethod = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName)) {
				// 获取当前的用户
				myMethod = method;
				break;
			}
		}

		if(myMethod != null) {

			final FastcmsUserDetails user;
			try {
				user = AuthUtils.getUser();
				if(user.isAdmin()) {
					return where;
				}
			} catch (Exception e) {
				return where;
			}

			if(user != null) {
				//只查看自己的数据
				DataPermission annotation = AnnotationUtils.getAnnotation(myMethod, DataPermission.class);
				if(annotation != null) {
					EqualsTo equalsTo = new EqualsTo();
					equalsTo.setLeftExpression(buildColumn(annotation.value(), USER_COLUMN));
					equalsTo.setRightExpression(new LongValue(user.getUserId()));
					return ObjectUtils.isNotEmpty(where) ? new AndExpression(where, equalsTo) : equalsTo;
				}
			}
		}

		return where;
	}

	protected Column buildColumn(String tableAlias, String columnName) {
		return new Column(StringUtils.isNotEmpty(tableAlias) ? tableAlias + "." + columnName : columnName);
	}

}
