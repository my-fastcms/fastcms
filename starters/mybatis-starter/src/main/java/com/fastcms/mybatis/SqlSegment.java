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
package com.fastcms.mybatis;

import java.io.Serializable;

/**
 * @author： wjun_java@163.com
 * @date： 2023/2/26
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class SqlSegment implements Serializable {

	private String sqlSegment;

	private String tableName;

	private boolean isJoinTable;

	public SqlSegment(String sqlSegment, String tableName, boolean isJoinTable) {
		this.sqlSegment = sqlSegment;
		this.tableName = tableName;
		this.isJoinTable = isJoinTable;
	}

	public SqlSegment(String sqlSegment, String tableName) {
		this(sqlSegment, tableName, true);
	}

	public SqlSegment(String sqlSegment) {
		this(sqlSegment, null, false);
	}

	public String getSqlSegment() {
		return sqlSegment;
	}

	public void setSqlSegment(String sqlSegment) {
		this.sqlSegment = sqlSegment;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isJoinTable() {
		return isJoinTable;
	}

	public void setJoinTable(boolean joinTable) {
		isJoinTable = joinTable;
	}

}
