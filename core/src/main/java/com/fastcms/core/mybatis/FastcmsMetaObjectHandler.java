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

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fastcms.core.auth.AuthUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/3
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class FastcmsMetaObjectHandler implements MetaObjectHandler {

    private final static String CREATED = "created";
    private final static String UPDATED = "updated";
    private final static String CREATE_USER_ID = "createUserId";
    private final static String UPDATE_USER_ID = "updateUserId";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(CREATED)) {
            this.strictInsertFill(metaObject, CREATED, () -> LocalDateTime.now(), LocalDateTime.class);
        }

        if(metaObject.hasSetter(CREATE_USER_ID)) {
            Object fieldValByName = getFieldValByName(CREATE_USER_ID, metaObject);
            if(fieldValByName == null) {
                setFieldValByName(CREATE_USER_ID, AuthUtils.getUserId(), metaObject);
            }
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATED)) {
            this.strictUpdateFill(metaObject, UPDATED, () -> LocalDateTime.now(), LocalDateTime.class);
        }

        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            Object fieldValByName = getFieldValByName(UPDATE_USER_ID, metaObject);
            if (fieldValByName == null) {
                setFieldValByName(UPDATE_USER_ID, AuthUtils.getUserId(), metaObject);
            }
        }

    }

}
