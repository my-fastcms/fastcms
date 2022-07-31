package com.fastcms.core.mybatis;

import com.fastcms.mybatis.DataPermissionSqlHandler;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.stereotype.Component;

/**
 * fastcms 查看自身数据权限
 * @author： wjun_java@163.com
 * @date： 2022/7/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class SelfDataPermissionSqlHandler implements DataPermissionSqlHandler {

    @Override
    public String getSqlSegment(String mappedStatementId, String mainTable, Statement statement) {
        return null;
    }

}
