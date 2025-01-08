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

package com.fastcms.cms.site;

import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.Menu;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.site.Site;
import com.fastcms.utils.CollectionUtils;
import com.fastcms.utils.PluginUtils;
import com.fastcms.utils.RequestUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2023/2/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
@Primary
public class DefaultSiteManager implements SiteManager, ApplicationListener<ApplicationStartedEvent> {

    Set<SiteManager> siteManagerList = Collections.synchronizedSet(new HashSet<>());

    List<Site> siteList = Collections.synchronizedList(new ArrayList<>());

    Map<String, Site> domainSiteMap = new ConcurrentHashMap<>();

    Map<String, Site> pathSiteMap = new ConcurrentHashMap<>();

    @Override
    public Site getSite(HttpServletRequest request) {
        String baseUrl = RequestUtils.getBaseUrl(request);
        Site site = domainSiteMap.get(baseUrl);
        if (site != null) {
            return site;
        }
        String requestURI = request.getRequestURI();
        String[] urlArr = requestURI.split("/");
        List<String> urls = Arrays.asList(urlArr).stream().filter(item -> StrUtils.isNotBlank(item)).collect(Collectors.toList());
        baseUrl = CollectionUtils.isNotEmpty(urls) ? urls.get(0) : "";
        return pathSiteMap.get(baseUrl);
    }

    @Override
    public List<Site> loadSites() {
        resetSiteData();
        List<SiteManager> extensions = PluginUtils.getExtensions(SiteManager.class);
        siteManagerList.addAll(extensions);
        siteManagerList.forEach(item -> siteList.addAll(item.loadSites()));

        domainSiteMap.putAll(siteList.stream().filter(item -> StrUtils.isNotBlank(item.getDomain())).collect(Collectors.toMap(Site::getDomain, t -> t)));
        pathSiteMap.putAll(siteList.stream().filter(item -> StrUtils.isNotBlank(item.getPath())).collect(Collectors.toMap(Site::getPath, t -> t)));
        return siteList;
    }

    @Override
    public List<Menu> loadSiteMenus(Site site) {
        for (SiteManager siteManager : siteManagerList) {
            return siteManager.loadSiteMenus(site);
        }
        return null;
    }

    @Override
    public List<Article> loadSiteArticles(Site site) {
        for (SiteManager siteManager : siteManagerList) {
            return siteManager.loadSiteArticles(site);
        }
        return null;
    }

    @Override
    public List<ArticleCategory> loadSiteCategories(Site site) {
        for (SiteManager siteManager : siteManagerList) {
            return siteManager.loadSiteCategories(site);
        }
        return null;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Map<String, SiteManager> siteManagerMap = event.getApplicationContext().getBeansOfType(SiteManager.class);
        siteManagerList.addAll(siteManagerMap.values().stream().filter(item -> !(item instanceof DefaultSiteManager)).collect(Collectors.toList()));
        loadSites();
    }

    void resetSiteData() {
        this.siteList.clear();
        this.domainSiteMap.clear();
        this.pathSiteMap.clear();
    }

}
