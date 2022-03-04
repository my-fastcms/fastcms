package com.fastcms.cms.search;

import com.fastcms.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
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
public class FastcmsSearcherManager implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private LuceneSearcher luceneSearcher;

    /**
     * 从小到大排序
     * luceneSearcher优先级最低
     * 默认为luceneSearcher
     */
    List<FastcmsSearcher> fastcmsSearcherList = Collections.synchronizedList(new ArrayList<>());

    public FastcmsSearcher getSearcher() {
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
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Map<String, FastcmsSearcher> configListenerMap = ApplicationUtils.getApplicationContext().getBeansOfType(FastcmsSearcher.class);
        fastcmsSearcherList.addAll(configListenerMap.values());
    }

}
