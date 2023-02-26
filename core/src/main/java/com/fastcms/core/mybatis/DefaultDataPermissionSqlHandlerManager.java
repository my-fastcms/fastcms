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

import com.fastcms.mybatis.DataPermissionSqlHandler;
import com.fastcms.mybatis.DataPermissionSqlHandlerManager;
import com.fastcms.utils.ApplicationUtils;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * fastcms数据权限sql处理器默认工厂实现
 * @author： wjun_java@163.com
 * @date： 2022/7/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class DefaultDataPermissionSqlHandlerManager implements DataPermissionSqlHandlerManager, ApplicationListener<ApplicationReadyEvent> {

    List<DataPermissionSqlHandler> dataPermissionSqlHandlerList = Collections.synchronizedList(new ArrayList<>());

    private DataPermissionSqlHandler dataPermissionSqlHandler;

    @Override
    public String getSqlSegment(String mappedStatementId, Statement statement) throws Exception {
        return dataPermissionSqlHandler == null ? statement.toString() : dataPermissionSqlHandler.getSqlSegment(mappedStatementId, statement).getSqlSegment();
    }

    @Override
    public DataPermissionSqlHandler getHandler() {

        Map<String, DataPermissionSqlHandler> dataPermissionSqlHandlerMap = ApplicationUtils.getApplicationContext().getBeansOfType(DataPermissionSqlHandler.class);
        dataPermissionSqlHandlerMap.values().forEach(item -> item.setNext(null));
        dataPermissionSqlHandlerList.addAll(dataPermissionSqlHandlerMap.values());

        // 排序
        List<DataPermissionSqlHandler> collect = dataPermissionSqlHandlerList.stream().sorted(Comparator.comparing(DataPermissionSqlHandler::getOrder)).collect(Collectors.toList());

        dataPermissionSqlHandler = collect.get(collect.size() - 1);
        for (int i = collect.size() - 2; i >= 0; i --) {
            DataPermissionSqlHandler temp = collect.get(i);
            temp.setNext(dataPermissionSqlHandler);
            dataPermissionSqlHandler = temp;
        }

        dataPermissionSqlHandlerList.clear();

        return dataPermissionSqlHandler;
    }

    @Override
    public void addDataPermissionSqlHandler(DataPermissionSqlHandler dataPermissionSqlHandler) {
        dataPermissionSqlHandlerList.add(dataPermissionSqlHandler);
    }

    @Override
    public void removeDataPermissionSqlHandler(DataPermissionSqlHandler dataPermissionSqlHandler) {
        dataPermissionSqlHandlerList.remove(dataPermissionSqlHandler);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        getHandler();
    }

}
