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
package com.fastcms.cache;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/13
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String ROLE_PERMISSION_CACHE_NAME = "role_permissions";
    public static final String USER_MENU_PERMISSION_CACHE_NAME = "user_menu_permissions";
    public static final String ADMIN_INDEX_DATA_CACHE_NAME = "admin_index_data";

    @Bean
    public CacheManager cacheManager() throws URISyntaxException {
        URL myUrl = getClass().getResource("/ehcache.xml");
        CachingProvider cachingProvider = Caching.getCachingProvider(EhcacheCachingProvider.class.getName());
        javax.cache.CacheManager ehcacheCacheManager = cachingProvider.getCacheManager(myUrl.toURI(), getClass().getClassLoader());
        return new JCacheCacheManager(ehcacheCacheManager);
    }

}
