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

import com.fastcms.core.auth.AuthUtils;
import com.fastcms.mybatis.DataPermissionSqlHandler;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.springframework.stereotype.Component;

/**
 * fastcms 部门主管数据权限
 * @author： wjun_java@163.com
 * @date： 2022/8/1
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class DeptLeaderDataPermissionSqlHandler implements DataPermissionSqlHandler {

    @Override
    public String getSqlSegment(String mappedStatementId, String mainTable, Statement statement) throws Exception {
        Select select = (Select) statement;

        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;

        String permissionSql = mainTable + ".user_id in (" + AuthUtils.getUserId() + ")";

        if (plainSelect.getWhere() == null) {
            plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression(permissionSql));
        } else {
            plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), CCJSqlParserUtil.parseCondExpression(permissionSql)));
        }

        return select.toString();
    }

}