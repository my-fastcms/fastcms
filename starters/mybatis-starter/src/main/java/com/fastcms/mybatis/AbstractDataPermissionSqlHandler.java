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

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fastcms.common.constants.FastcmsConstants;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;

import java.util.stream.Collectors;

/**
 * fastcms数据权限sql处理器工厂
 * @author： wjun_java@163.com
 * @date： 2022/8/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractDataPermissionSqlHandler implements DataPermissionSqlHandler {

    protected DataPermissionSqlHandler dataPermissionSqlHandler;

    @Override
    public SqlSegment getSqlSegment(String mappedStatementId, Statement statement) throws Exception {
        if (isFilter(mappedStatementId)) {
            return getNext() == null ? new SqlSegment(statement.toString()) : getNext().getSqlSegment(mappedStatementId, statement);
        }
        if (isMatch(mappedStatementId)) {
            new DataPermissionSqlProcessor(this, doGetSqlSegment(mappedStatementId, statement), statement).process();
        }
        return getNext() == null ? new SqlSegment(statement.toString()) : getNext().getSqlSegment(mappedStatementId, statement);
    }

    @Override
    public boolean isMatch(String mappedStatementId) {
        return false;
    }

    @Override
    public boolean isFilter(String mappedStatementId) {
        return false;
    }

    @Override
    public boolean isNeedProcess(Table table) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(table.getFullyQualifiedName());

        if (tableInfo != null) {
            return CollectionUtils.isNotEmpty(tableInfo.getFieldList().stream().filter(field -> field.getColumn().equals(FastcmsConstants.CREATE_USER_ID)).collect(Collectors.toList()));
        }
        return false;
    }

    @Override
    public void setNext(DataPermissionSqlHandler dataPermissionSqlHandler) {
        this.dataPermissionSqlHandler = dataPermissionSqlHandler;
    }

    @Override
    public DataPermissionSqlHandler getNext() {
        return dataPermissionSqlHandler;
    }

    protected abstract SqlSegment doGetSqlSegment(String mappedStatementId, Statement statement) throws Exception;

}
