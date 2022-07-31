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

/**
 * fastcms数据权限类别
 * @author： wjun_java@163.com
 * @date： 2022/7/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public enum DataScopeType {

    /**
     * 查看全部数据
     */
    ALL,

    /**
     * 查看自己数据，默认权限
     */
    SELF,

    /**
     * 本级数据
     */
    SELF_LEVEL,

    /**
     * 本机以及所有下级数据
     */
    ALL_LEVEL

}
