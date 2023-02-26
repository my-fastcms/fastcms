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

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.mybatis.AbstractDataPermissionSqlHandler;
import com.fastcms.mybatis.SqlSegment;
import net.sf.jsqlparser.statement.Statement;
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
public class DeptLeaderDataPermissionSqlHandler extends AbstractDataPermissionSqlHandler {

    @Override
    protected SqlSegment doGetSqlSegment(String mappedStatementId, Statement statement) throws Exception {
        String permissionSql = FastcmsConstants.CREATE_USER_ID + " in (" + AuthUtils.getUserId() + ")";
        return new SqlSegment(permissionSql);
    }

    @Override
    public boolean isMatch(String mappedStatementId) {
        return super.isMatch(mappedStatementId);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 300;
    }

}
