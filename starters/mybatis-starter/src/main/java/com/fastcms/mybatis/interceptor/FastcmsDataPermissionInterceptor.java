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

package com.fastcms.mybatis.interceptor;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.mybatis.DataPermissionSqlHandlerFactory;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * fastcms mybatis 数据权限 拦截器
 * @author： wjun_java@163.com
 * @date： 2021/4/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        }
)
@Component
public class FastcmsDataPermissionInterceptor implements Interceptor {

    @Autowired
    private DataPermissionSqlHandlerFactory dataPermissionSqlHandlerFactory;

    @Override
    public Object intercept(Invocation invocation) throws Exception {

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();

        final String finalSql = handleSql(mappedStatement.getId(), originalSql);

        if (StringUtils.isNotBlank(finalSql)) {
            metaObject.setValue("delegate.boundSql.sql", finalSql);
        }

        return invocation.proceed();
    }

    /**
     * sql处理
     * @param originalSql
     * @return
     * @throws Exception
     */
    protected String handleSql(String mappedStatementId, String originalSql) throws Exception {

        Statement statement = CCJSqlParserUtil.parse(originalSql);

        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(statement);

        AtomicBoolean needAuth = new AtomicBoolean(false);

        tableList.forEach(item -> {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(item);
            if (tableInfo != null) {
                List<TableFieldInfo> userIdFieldList = tableInfo.getFieldList().stream().filter(field -> field.getColumn().equals(FastcmsConstants.USER_ID)).collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(userIdFieldList)) {
                    needAuth.set(true);
                }
            }
        });

        if (!needAuth.get()) {
            return originalSql;
        }

        String tableName = "";

        Select select = (Select) statement;
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect) selectBody;
            FromItem fromItem = plainSelect.getFromItem();
            Alias alias = fromItem.getAlias();
            if (alias != null) {
                tableName = alias.getName();
            }

            if (StringUtils.isBlank(tableName) && fromItem instanceof Table) {
                Table table = (Table) fromItem;
                tableName = table.getName();
            }

            if (StringUtils.isBlank(tableName)) {
                return originalSql;
            }

        }

        return dataPermissionSqlHandlerFactory.getDataPermissionSqlHandler().getSqlSegment(mappedStatementId, tableName, statement);
    }

}
