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

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.fastcms.mybatis.DataPermissionSqlHandlerManager;
import com.fastcms.mybatis.IgnoreDataPermissionCache;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

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

    private static final Logger logger = LoggerFactory.getLogger(FastcmsDataPermissionInterceptor.class);

    @Autowired
    private DataPermissionSqlHandlerManager dataPermissionSqlHandlerManager;

    @Value("${fastcms.mybatis.ignore.mappedStatementIds:}")
    private List<String> ignoreMappedStatementIds;

    @Override
    public Object intercept(Invocation invocation) throws Exception {

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        if (ignoreMappedStatementIds.stream().filter(item -> mappedStatement.getId().contains(item)).count() > 0) {
            return invocation.proceed();
        }

        if (IgnoreDataPermissionCache.isIgnore(mappedStatement.getId())) {
            return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        logger.debug("==> originalSql:" + originalSql);
        final String finalSql = handleSql(mappedStatement.getId(), originalSql);
        logger.debug("==> finalSql:" + finalSql);
        metaObject.setValue("delegate.boundSql.sql", finalSql);

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
        return dataPermissionSqlHandlerManager.getSqlSegment(mappedStatementId, statement);
    }

}
