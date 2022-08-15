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

import net.sf.jsqlparser.schema.Table;

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
    public String getSqlSegment(String mappedStatementId) throws Exception {
        if (isFilter(mappedStatementId)) {
            return null;
        }
        if (isMatch()) {
            return doGetSqlSegment(mappedStatementId);
        } else {
            return getNext() == null ? null : getNext().getSqlSegment(mappedStatementId);
        }
    }

    @Override
    public boolean isMatch() {
        return false;
    }

    @Override
    public boolean isFilter(String mappedStatementId) {
        return false;
    }

    @Override
    public boolean isNeedProcess(Table table) {
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

    protected abstract String doGetSqlSegment(String mappedStatementId) throws Exception;

}
