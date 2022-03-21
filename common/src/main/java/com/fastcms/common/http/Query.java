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

package com.fastcms.common.http;

import com.fastcms.common.utils.MapUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author： wjun_java@163.com
 * @date： 2022/03/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Query {
    
    private boolean isEmpty = true;
    
    public static final Query EMPTY = Query.newInstance();
    
    private Map<String, Object> params;
    
    private static final String DEFAULT_ENC = "UTF-8";
    
    public Query() {
        params = new LinkedHashMap<>();
    }
    
    public static Query newInstance() {
        return new Query();
    }
    
    /**
     * Add query parameter.
     *
     * @param key   key
     * @param value value
     * @return this query
     */
    public Query addParam(String key, Object value) {
        isEmpty = false;
        params.put(key, value);
        return this;
    }
    
    public Object getValue(String key) {
        return params.get(key);
    }
    
    /**
     * Add all parameters as query parameter.
     *
     * @param params parameters
     * @return this query
     */
    public Query initParams(Map<String, String> params) {
        if (MapUtil.isNotEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                addParam(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }
    
    /**
     * Add query parameters from KV list. KV list: odd index is key, even index is value.
     *
     * @param list KV list
     */
    public void initParams(List<String> list) {
        if ((list.size() & 1) != 0) {
            throw new IllegalArgumentException("list size must be a multiple of 2");
        }
        for (int i = 0; i < list.size(); ) {
            addParam(list.get(i++), list.get(i++));
        }
    }
    
    /**
     * Print query as a http url param string. Like K=V&K=V.
     *
     * @return http url param string
     */
    public String toQueryUrl() {
        StringBuilder urlBuilder = new StringBuilder();
        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        int i = entrySet.size();
        for (Map.Entry<String, Object> entry : entrySet) {
            try {
                if (null != entry.getValue()) {
                    urlBuilder.append(entry.getKey()).append('=')
                            .append(URLEncoder.encode(String.valueOf(entry.getValue()), DEFAULT_ENC));
                    if (i > 1) {
                        urlBuilder.append('&');
                    }
                }
                i--;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        
        return urlBuilder.toString();
    }
    
    public void clear() {
        isEmpty = false;
        params.clear();
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }
    
}
