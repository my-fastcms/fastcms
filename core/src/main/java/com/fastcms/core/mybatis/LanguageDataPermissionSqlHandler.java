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

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fastcms.mybatis.AbstractDataPermissionSqlHandler;
import com.fastcms.mybatis.SqlSegment;
import com.fastcms.utils.I18nUtils;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 对带有language字段的表自动加上语言过滤条件
 * @author： wjun_java@163.com
 * @date： 2022/12/12
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class LanguageDataPermissionSqlHandler extends AbstractDataPermissionSqlHandler {

	final String PERMISSION_SQL_FIELD = "language";

	@Override
	protected SqlSegment doGetSqlSegment(String mappedStatementId, Statement statement) throws Exception {
		return new SqlSegment(PERMISSION_SQL_FIELD.concat(StringPool.EQUALS + "'"+I18nUtils.getLanguage()+"'"));
	}

	@Override
	public boolean isMatch(String mappedStatementId) {

		try {
			return I18nUtils.getLanguage() != null;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean isNeedProcess(Table table) {
		TableInfo tableInfo = TableInfoHelper.getTableInfo(table.getFullyQualifiedName());

		if (tableInfo != null) {
			return CollectionUtils.isNotEmpty(tableInfo.getFieldList().stream().filter(field -> field.getColumn().equals(PERMISSION_SQL_FIELD)).collect(Collectors.toList()));
		}
		return false;
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
