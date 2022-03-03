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
package com.fastcms.lucene.autoconfigure;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author： wjun_java@163.com
 * @date： 2022/03/03
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
public class LuceneAutoConfiguration {

    /**
     * lucene索引,存放位置
     */
    private static final String LUCENE_INDEX_PATH = "lucene/indexDir/";

    /**
     * 创建一个 Analyzer 实例
     */
    @Bean
    public Analyzer analyzer() {
        return new SmartChineseAnalyzer();
    }

    /**
     * 索引位置
     */
    @Bean
    public Directory directory() throws IOException {
        Path path = Paths.get(LUCENE_INDEX_PATH);
        File file = path.toFile();
        if (!file.exists()) {
            //如果文件夹不存在,则创建
            file.mkdirs();
        }
        return FSDirectory.open(path);
    }

    /**
     * 创建indexWriter
     */
    @Bean
    public IndexWriter indexWriter(Directory directory, Analyzer analyzer) throws IOException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        // 清空索引
        indexWriter.deleteAll();
        indexWriter.commit();
        return indexWriter;
    }

    /**
     * SearcherManager管理
     * ControlledRealTimeReopenThread创建一个守护线程，如果没有主线程这个也会消失，
     * 这个线程作用就是定期更新让SearchManager管理的search能获得最新的索引库，下面是每25S执行一次。
     */
    @Bean
    public SearcherManager searcherManager(IndexWriter indexWriter) throws IOException {
        SearcherManager searcherManager = new SearcherManager(indexWriter, false, false, new SearcherFactory());
        ControlledRealTimeReopenThread cRTReopenThead = new ControlledRealTimeReopenThread(indexWriter, searcherManager, 5.0, 0.025);
        cRTReopenThead.setDaemon(true);
        //线程名称
        cRTReopenThead.setName("更新IndexReader线程");
        // 开启线程
        cRTReopenThead.start();
        return searcherManager;
    }

}
