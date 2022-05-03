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
package com.fastcms.web.security;

import com.fastcms.common.auth.model.Permission;
import com.fastcms.common.auth.model.User;
import com.fastcms.common.exception.AccessException;

/**
 *  @author： wjun_java@163.com
 *  * @date： 2021/10/24
 *  * @description：
 *  * @modifiedBy：
 *  * @version: 1.0
 */
public interface AuthManager {

    /**
     * 发起授权请求
     * 或者直接获取token
     * @param username
     * @param password
     * @param code
     * @return 返回认证用户
     * @throws AccessException
     */
    FastcmsUser login(String username, String password, String code) throws AccessException;

    /**
     * 通过授权用户以及用户访问资源，检查当前用户是否有授权
     * @param permission
     * @param user
     * @throws AccessException
     */
    void auth(Permission permission, User user) throws AccessException;

}
