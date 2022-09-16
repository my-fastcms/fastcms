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

import net.sf.jsqlparser.statement.Statement;

/**
 * fastcms数据权限sql处理器工厂
 * @author： wjun_java@163.com
 * @date： 2022/7/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface DataPermissionSqlHandlerManager {

    /**
     * 获取权限sql片段
     * @param mappedStatementId
     * @return
     */
    String getSqlSegment(String mappedStatementId, Statement statement) throws Exception;

    /**
     * 获取数据权限handler
     * @return
     */
    DataPermissionSqlHandler getHandler();

    /**
     * 添加sql权限处理器
     * @param dataPermissionSqlHandler
     */
    void addDataPermissionSqlHandler(DataPermissionSqlHandler dataPermissionSqlHandler);

    /**
     * 移除sql权限处理器
     * @param dataPermissionSqlHandler
     */
    void removeDataPermissionSqlHandler(DataPermissionSqlHandler dataPermissionSqlHandler);

}
