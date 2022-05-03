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

package com.fastcms.common.auth.model;

import java.io.Serializable;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Resource implements Serializable {
    
    public static final String SPLITTER = ":";
    
    public static final String ANY = "*";
    
    private static final long serialVersionUID = 925971662931204553L;
    
    /**
     * The unique key of resource.
     */
    private String key;
    
    public Resource(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
    
    public String parseName() {
        return key.substring(0, key.lastIndexOf(SPLITTER));
    }
    
    @Override
    public String toString() {
        return "Resource{" + "key='" + key + '\'' + '}';
    }
}
