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

package com.fastcms.hello;

import com.fastcms.mybatis.AbstractDataPermissionSqlHandler;
import com.fastcms.mybatis.SqlSegment;
import com.fastcms.service.IUserService;
import com.fastcms.utils.ApplicationUtils;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;

/**
 * @author： wjun_java@163.com
 * @date： 2022/8/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Extension
public class HelloDataPermissionSqlHandler extends AbstractDataPermissionSqlHandler implements ExtensionPoint {

    @Override
    protected SqlSegment doGetSqlSegment(String mappedStatementId, Statement statement) throws Exception {
        IUserService bean = ApplicationUtils.getBean(IUserService.class);
        System.out.println(bean);
        return new SqlSegment("1=1");
    }

    @Override
    public boolean isNeedProcess(Table table) {
        return true;
    }

    @Override
    public boolean isMatch(String mappedStatementId) {
        return mappedStatementId.equalsIgnoreCase("com.fastcms.hello.HelloPluginMapper.getHelloList");
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
