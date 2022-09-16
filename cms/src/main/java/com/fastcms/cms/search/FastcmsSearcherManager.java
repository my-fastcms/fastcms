package com.fastcms.cms.search;

import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.PluginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * fastcms搜索管理器
 * @author： wjun_java@163.com
 * @date： 2022/03/04
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class FastcmsSearcherManager implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private LuceneSearcher luceneSearcher;

    /**
     * 从小到大排序
     * luceneSearcher优先级最低
     * 默认为luceneSearcher
     */
    List<FastcmsSearcher> fastcmsSearcherList = Collections.synchronizedList(new ArrayList<>());

    public FastcmsSearcher getSearcher() {
        //获取插件中的搜索器
        List<FastcmsSearcher> extensions = PluginUtils.getExtensions(FastcmsSearcher.class);
        fastcmsSearcherList.addAll(extensions);

        fastcmsSearcherList.stream().sorted(Comparator.comparing(FastcmsSearcher::getOrder));
        for (FastcmsSearcher fastcmsSearcher : fastcmsSearcherList) {
            if (fastcmsSearcher.isEnable()) {
                return fastcmsSearcher;
            }
        }
        return luceneSearcher;
    }

    public void addSearcher(FastcmsSearcher fastcmsSearcher) {
        fastcmsSearcherList.add(fastcmsSearcher);
    }

    public void removeSearcher(FastcmsSearcher fastcmsSearcher) {
        fastcmsSearcherList.remove(fastcmsSearcher);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, FastcmsSearcher> fastcmsSearcherMap = ApplicationUtils.getApplicationContext().getBeansOfType(FastcmsSearcher.class);
        fastcmsSearcherList.addAll(fastcmsSearcherMap.values());
    }

}
