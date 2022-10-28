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

package com.fastcms.cms.order;

import com.fastcms.cms.entity.Article;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.PluginUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2022/10/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class FastcmsArticlePriceServiceManager implements ApplicationListener<ApplicationReadyEvent> {

    Map<String, IArticlePriceService> allArticlePriceServiceMap = Collections.synchronizedMap(new HashMap<>());

    public BigDecimal getPrice(Article article) {
        IArticlePriceService articlePriceService = allArticlePriceServiceMap.get(article.getPriceClass());
        return articlePriceService == null ? null : articlePriceService.getPrice(article);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, IArticlePriceService> articlePriceServiceMap = ApplicationUtils.getApplicationContext().getBeansOfType(IArticlePriceService.class);
        articlePriceServiceMap.keySet().forEach(item -> {
            IArticlePriceService fastcmsOrderService = articlePriceServiceMap.get(item);
            String name = fastcmsOrderService.getClass().getName();
            if (name.contains("$$")) {
                name = name.substring(0, name.indexOf("$$"));
            }
            allArticlePriceServiceMap.putIfAbsent(name, fastcmsOrderService);
        });

        List<IArticlePriceService> extensions = PluginUtils.getExtensions(IArticlePriceService.class);
        extensions.forEach(item -> allArticlePriceServiceMap.putIfAbsent(item.getClass().getName(), item));
    }

}
