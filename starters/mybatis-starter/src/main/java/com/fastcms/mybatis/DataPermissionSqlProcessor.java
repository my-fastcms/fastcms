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

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.*;

/**
 * 数据权限sql处理器
 * @author： wjun_java@163.com
 * @date： 2022/8/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class DataPermissionSqlProcessor extends StatementVisitorAdapter implements FromItemVisitor, SelectVisitor {

    private SqlSegment sqlSegment;

    private String permissionSql;

    private Statement statement;

    private DataPermissionSqlHandler dataPermissionSqlHandler;

    public DataPermissionSqlProcessor(DataPermissionSqlHandler dataPermissionSqlHandler, SqlSegment sqlSegment, Statement statement) {
        this.dataPermissionSqlHandler = dataPermissionSqlHandler;
        this.sqlSegment = sqlSegment;
        this.statement = statement;
        this.permissionSql = sqlSegment == null ? null : sqlSegment.getSqlSegment();
    }

    public void process() {
        this.statement.accept(this);
    }

    @Override
    public void visit(Select select) {

        if (select.getWithItemsList() != null) {
            for (WithItem withItem : select.getWithItemsList()) {
                withItem.accept(this);
            }
        }
        select.getSelectBody().accept(this);

    }

    @Override
    public void visit(Table tableName) {
    }

    @Override
    public void visit(SubSelect subSelect) {
        if (subSelect != null) {
            subSelect.getSelectBody().accept(this);
        }
    }

    @Override
    public void visit(SubJoin subjoin) {

    }

    @Override
    public void visit(LateralSubSelect lateralSubSelect) {

    }

    @Override
    public void visit(ValuesList valuesList) {

    }

    @Override
    public void visit(TableFunction tableFunction) {

    }

    @Override
    public void visit(ParenthesisFromItem aThis) {

    }

    @Override
    public void visit(PlainSelect plainSelect) {

        if (sqlSegment == null) {
            return;
        }

        if (plainSelect.getFromItem() != null) {
            if (plainSelect.getFromItem() instanceof Table) {
                if (!sqlSegment.isJoinTable()) {
                    processWhere((Table) plainSelect.getFromItem(), plainSelect);
                }
            }
            plainSelect.getFromItem().accept(this);
        }

        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                if (join.getRightItem() instanceof Table) {
                    Table table = (Table) join.getRightItem();
                    if (sqlSegment.isJoinTable() && sqlSegment.getTableName() != null && sqlSegment.getTableName().equals(table.getName())) {
                        processWhere(table, plainSelect);
                    }
                }
                join.getRightItem().accept(this);
            }
        }

    }

    @Override
    public void visit(SetOperationList setOpList) {
        for (SelectBody plainSelect : setOpList.getSelects()) {
            plainSelect.accept(this);
        }
    }

    @Override
    public void visit(WithItem withItem) {

    }

    void processWhere(Table table, PlainSelect plainSelect) {

        if (dataPermissionSqlHandler == null) {
            return;
        }

        if (!dataPermissionSqlHandler.isNeedProcess(table)) {
            return;
        }

        if (StringUtils.isNotBlank(permissionSql)) {
            if (table.getAlias() != null) {
                permissionSql = table.getAlias().getName() + StringPool.DOT + permissionSql;
            }

            try {
                Expression expression = CCJSqlParserUtil.parseCondExpression(permissionSql);
                if (plainSelect.getWhere() == null) {
                    plainSelect.setWhere(expression);
                } else {
                    plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), expression));
                }
            } catch (JSQLParserException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

    }

}
