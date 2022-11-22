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

package com.fastcms.core.template;

import com.fastcms.utils.ApplicationUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Fastcms静态化管理器
 * @author： wjun_java@163.com
 * @date： 2021/9/30
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class FastcmsStaticHtmlManager implements FastcmsStaticHtmlService, InitializingBean {

    private FastcmsStaticHtmlService enableStaticHtmlService = ApplicationUtils.getBean(FakeStaticHtmlService.class);

    List<FastcmsStaticHtmlService> staticHtmlServiceList = Collections.synchronizedList(new ArrayList<>());

    public void addStaticHtmlServices(List<FastcmsStaticHtmlService> staticHtmlServices) {
        staticHtmlServiceList.addAll(staticHtmlServices);
        sort();
    }

    public void addStaticHtmlService(FastcmsStaticHtmlService staticHtmlService) {
        staticHtmlServiceList.add(staticHtmlService);
        sort();
    }

    public void removeStaticHtmlService(FastcmsStaticHtmlService staticHtmlService) {
        staticHtmlServiceList.remove(staticHtmlService);
        sort();
    }

    /**
     * 是否启用伪静态
     * @return
     */
    public boolean isFakeStaticEnable() {

        /**
         * 查询是否有开启真正静态化插件，如果开启了，优先级高于伪静态
         */
        for (int i = 0; i < staticHtmlServiceList.size() - 1; i++) {
            if (staticHtmlServiceList.get(i).isEnable()) {
                return false;
            }
        }

        return staticHtmlServiceList.get(staticHtmlServiceList.size() - 1).isEnable();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, FastcmsStaticHtmlService> staticHtmlServiceMap = ApplicationUtils.getApplicationContext().getBeansOfType(FastcmsStaticHtmlService.class);
        staticHtmlServiceList.addAll(staticHtmlServiceMap.values());
        sort();
    }

    void sort() {
        staticHtmlServiceList.sort(Comparator.comparing(FastcmsStaticHtmlService::getOrder));
    }

    @Override
    public boolean isEnable() {

        for (FastcmsStaticHtmlService fastcmsStaticHtmlService : staticHtmlServiceList) {

            if (fastcmsStaticHtmlService instanceof FakeStaticHtmlService) {
                continue;
            }

            if (fastcmsStaticHtmlService.isEnable()) {
                enableStaticHtmlService = fastcmsStaticHtmlService;
                return true;
            }
        }

        enableStaticHtmlService = ApplicationUtils.getBean(FakeStaticHtmlService.class);
        return false;
    }

    @Override
    public String getFileSuffix() {
        return enableStaticHtmlService.getFileSuffix();
    }

    @Override
    public String getArticleStaticPath() {
        return enableStaticHtmlService.getArticleStaticPath();
    }

    @Override
    public String getPageStaticPath() {
        return enableStaticHtmlService.getPageStaticPath();
    }

    @Override
    public String getCategoryStaticPath() {
        return enableStaticHtmlService.getCategoryStaticPath();
    }

    @Override
    public String getTagStaticPath() {
        return enableStaticHtmlService.getTagStaticPath();
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
