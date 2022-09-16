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

package com.fastcms.common.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class MapUtil {
    
    /**
     * Null-safe check if the specified Dictionary is empty.
     *
     * <p>Null returns true.
     *
     * @param map the collection to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }
    
    /**
     * Null-safe check if the specified Dictionary is empty.
     *
     * <p>Null returns true.
     *
     * @param coll the collection to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(Dictionary coll) {
        return (coll == null || coll.isEmpty());
    }
    
    /**
     * Null-safe check if the specified Dictionary is not empty.
     *
     * <p>Null returns false.
     *
     * @param map the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
    
    /**
     * Null-safe check if the specified Dictionary is not empty.
     *
     * <p>Null returns false.
     *
     * @param coll the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(Dictionary coll) {
        return !isEmpty(coll);
    }
    
    /**
     * Put into map if value is not null.
     *
     * @param target target map
     * @param key    key
     * @param value  value
     */
    public static void putIfValNoNull(Map target, Object key, Object value) {
        Objects.requireNonNull(key, "key");
        if (value != null) {
            target.put(key, value);
        }
    }
    
    /**
     * Put into map if value is not empty.
     *
     * @param target target map
     * @param key    key
     * @param value  value
     */
    public static void putIfValNoEmpty(Map target, Object key, Object value) {
        Objects.requireNonNull(key, "key");
        if (value instanceof String) {
            if (StringUtils.isNotEmpty((String) value)) {
                target.put(key, value);
            }
            return;
        }
        if (value instanceof Collection) {
            if (CollectionUtils.isNotEmpty((Collection) value)) {
                target.put(key, value);
            }
            return;
        }
        if (value instanceof Map) {
            if (isNotEmpty((Map) value)) {
                target.put(key, value);
            }
            return;
        }
        if (value instanceof Dictionary) {
            if (isNotEmpty((Dictionary) value)) {
                target.put(key, value);
            }
        }
    }
    
    /**
     * remove value, Thread safety depends on whether the Map is a thread-safe Map.
     *
     * @param map map
     * @param key key
     * @param removeJudge judge this key can be remove
     * @param <K> key type
     * @param <V> value type
     * @return value
     */
    public static <K, V> V removeKey(Map<K, V> map, K key, Predicate<V> removeJudge) {
        return map.computeIfPresent(key, (k, v) -> removeJudge.test(v) ? null : v);
    }
}
