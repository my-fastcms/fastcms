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

package com.fastcms.extension;

import com.fastcms.utils.PluginUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转账接口管理器
 * @author： wjun_java@163.com
 * @date： 2022/4/12
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class FastcmsTransferServiceManager implements ApplicationListener<ApplicationReadyEvent> {

    Map<String, FastcmsTransferService> allTransferServiceMap = Collections.synchronizedMap(new HashMap<>());

    public FastcmsTransferService getFastcmsTransferService(String className) {
        return allTransferServiceMap.get(className);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, FastcmsTransferService> fastcmsTransferServiceMap = event.getApplicationContext().getBeansOfType(FastcmsTransferService.class);
        fastcmsTransferServiceMap.keySet().forEach(item -> {
            FastcmsTransferService fastcmsOrderService = fastcmsTransferServiceMap.get(item);
            String name = fastcmsOrderService.getClass().getName();
            if (name.contains("$$")) {
                name = name.substring(0, name.indexOf("$$"));
            }
            allTransferServiceMap.putIfAbsent(name, fastcmsOrderService);
        });

        List<FastcmsTransferService> extensions = PluginUtils.getExtensions(FastcmsTransferService.class);
        extensions.forEach(item -> allTransferServiceMap.putIfAbsent(item.getClass().getName(), item));
    }

}
