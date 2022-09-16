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

package com.fastcms.common.auth;

import com.fastcms.common.auth.parser.DefaultResourceParser;
import com.fastcms.common.auth.parser.ResourceParser;
import com.fastcms.common.utils.StrUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * api权限标记注解
 * @author： wjun_java@163.com
 * @date： 2021/4/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    
    /**
     * The action type of the request.
     *
     * @return action type, default READ
     */
    ActionTypes action() default ActionTypes.READ;
    
    /**
     * The name of resource related to the request.
     *
     * @return resource name
     */
    String resource() default StrUtils.EMPTY;

    /**
     * rest api name
     * @return
     */
    String name() default StrUtils.EMPTY;
    
    /**
     * Resource name parser. Should have lower priority than resource().
     *
     * @return class type of resource parser
     */
    Class<? extends ResourceParser> parser() default DefaultResourceParser.class;
}
