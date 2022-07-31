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

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author： wjun_java@163.com
 * @date： 2022/7/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
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
        String sql = "select DISTINCT t.* from (\n" +
                "        select * from user u\n" +
                "        join article a on u.id = a.user_id\n" +
                "        UNION ALL\n" +
                "        select * from user u\n" +
                "        join article a on u.id = a.user_id\n" +
                "        UNION ALL\n" +
                "        select * from user u\n" +
                "        join article a on u.id = a.user_id\n" +
                "        UNION ALL\n" +
                "        select * from user u\n" +
                "        join article a on u.id = a.user_id\n" +
                "        ) t";

        Statement statement = CCJSqlParserUtil.parse(sql);

        System.out.println(statement);

    }

}
