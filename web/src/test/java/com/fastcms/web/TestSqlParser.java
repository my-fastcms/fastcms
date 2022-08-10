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

package com.fastcms.web;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fastcms.common.constants.FastcmsConstants;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author： wjun_java@163.com
 * @date： 2022/7/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@SpringBootTest
public class TestSqlParser {

    @Test
    public void test() throws JSQLParserException {
        String sql = "SELECT\n" +
                "        COUNT(o.id) all_count,\n" +
                "        COUNT( CASE WHEN o.pay_status = 0 THEN o.id ELSE NULL END ) AS unPay,\n" +
                "        COUNT( CASE WHEN o.delivery_status = 1 THEN o.id ELSE NULL END ) AS toSend,\n" +
                "        COUNT( CASE WHEN o.delivery_status = 2 THEN o.id ELSE NULL END ) AS send,\n" +
                "        COUNT( CASE WHEN o.delivery_status = 4 and o.trade_status = 1 THEN o.id ELSE NULL END ) AS comment,\n" +
                "        COUNT( CASE WHEN o.trade_status = 2 THEN o.id ELSE NULL END ) AS success,\n" +
                "        COUNT( CASE WHEN o.trade_status = 9 THEN o.id ELSE NULL END ) AS `close`,\n" +
                "        COUNT( CASE WHEN o.trade_status = 4 THEN o.id ELSE NULL END ) AS afterSale\n" +
                "        FROM `order` o\n" +
                "        WHERE o.user_id =1 and o.trade_status !=3";
        Statement stmt = CCJSqlParserUtil.parse(sql);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(stmt);
        assertEquals(1, tableList.size());
        assertTrue(tableList.contains("`order`"));
    }

    @Test
    public void test2() throws JSQLParserException {
        String sql = "select * from (select a.*, u.user_name from article a join user u on a.user_id = u.id) t where t.user_id=1 ";

        Statement statement = CCJSqlParserUtil.parse(sql);

        System.out.println(statement);

    }

    @Test
    public void test3() throws JSQLParserException {
        String sql = "select * from (select * from (select DISTINCT t.* from (\n" +
                "        select * from user u1\n" +
                "        join article a1 on u1.id = a1.user_id\n" +
                "        UNION ALL\n" +
                "        select * from user u2\n" +
                "        join article a2 on u2.id = a2.user_id\n" +
                "        UNION ALL\n" +
                "        select * from user u3\n" +
                "        join article a3 on u3.id = a3.user_id\n" +
                "        UNION ALL\n" +
                "        select * from user u4\n" +
                "        join article a4 on u4.id = a4.user_id\n" +
                "        ) t) a) b";

        Statement statement = CCJSqlParserUtil.parse(sql);

        FromItemFinder fromItemFinder = new FromItemFinder();
        fromItemFinder.getFromItemList(statement);

    }

    @Test
    public void test4() throws JSQLParserException {
        String sql = "select * from (select a.*, u.user_name from article a join user u on a.user_id = u.id) t where t.user_id=1 ";

        Statement statement = CCJSqlParserUtil.parse(sql);

        FromItemFinder fromItemFinder = new FromItemFinder();
        fromItemFinder.getFromItemList(statement);

    }

    class FromItemFinder extends StatementVisitorAdapter implements FromItemVisitor, SelectVisitor {

        private String permissionSql = "user_id = 1";
        private PlainSelect currentPlainSelect;

        public void getFromItemList(Statement statement) {
            statement.accept(this);
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
            // TODO 解析 join里面的table
            System.out.println("currPlainSel:" + this.currentPlainSelect);
            System.out.println("tableName:" + tableName);
            String fullyQualifiedName = tableName.getFullyQualifiedName();
            System.out.println("fullyQualifiedName:" + fullyQualifiedName);

            TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName.getFullyQualifiedName());

            AtomicBoolean needAuth = new AtomicBoolean(false);

            if (tableInfo != null) {
                List<TableFieldInfo> userIdFieldList = tableInfo.getFieldList().stream().filter(field -> field.getColumn().equals(FastcmsConstants.USER_ID)).collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(userIdFieldList)) {
                    needAuth.set(true);
                }
            }

            if (needAuth.get()) {
                try {
                    if (this.currentPlainSelect.getWhere() == null) {
                        this.currentPlainSelect.setWhere(CCJSqlParserUtil.parseCondExpression(permissionSql));
                    } else {
                        this.currentPlainSelect.setWhere(new AndExpression(this.currentPlainSelect.getWhere(), CCJSqlParserUtil.parseCondExpression(permissionSql)));
                    }
                } catch (JSQLParserException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

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

            if (plainSelect.getFromItem() != null) {
                if (plainSelect.getFromItem() instanceof Table) {
                    this.currentPlainSelect = plainSelect;
                }
                plainSelect.getFromItem().accept(this);
            }

            if (plainSelect.getJoins() != null) {
                for (Join join : plainSelect.getJoins()) {
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
    }

}
