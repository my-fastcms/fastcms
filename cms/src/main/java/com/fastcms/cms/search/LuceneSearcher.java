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
package com.fastcms.cms.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.utils.ConfigUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Lucene搜索器
 * @author： wjun_java@163.com
 * @date： 2022/03/04
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class LuceneSearcher implements FastcmsSearcher {

    public static final String HIGH_LIGHT_CLASS = "search-highlight";

    private static final Logger LOG = LoggerFactory.getLogger(LuceneSearcher.class);

    private static final String ARTICLE_ID = "aid";
    private static final String ARTICLE_TEXT = "text";
    private static final String ARTICLE_TITLE = "title";
    private static final String ARTICLE_CONTENT = "content";
    private static final String ARTICLE_CREATED = "created";

    /**
     * 是否启用lucene搜索器
     */
    private static final String ENABLE_LUCENE_SEARCHER = "enable_lucene_searcher";

    @Autowired
    private Analyzer analyzer;

    @Autowired
    private Directory directory;

    @Override
    public void addIndex(Article article) {
        IndexWriter writer = null;
        try {
            writer = createIndexWriter();
            Document doc = createDocument(article);
            writer.addDocument(doc);
        } catch (Exception e) {
            LOG.error(e.toString(), e);
        } finally {
            quietlyClose(writer);
        }
    }

    @Override
    public void deleteIndex(Object id) {
        IndexWriter writer = null;
        try {
            writer = createIndexWriter();
            writer.deleteDocuments(new Term(ARTICLE_ID, id.toString()));
        } catch (Exception e) {
            LOG.error(e.toString(), e);
        } finally {
            quietlyClose(writer);
        }
    }

    @Override
    public void updateIndex(Article article) {
        deleteIndex(article.getId());
        addIndex(article);
    }

    @Override
    public Page<Article> search(String keyword, int pageNum, int pageSize) {
        IndexReader indexReader = null;
        try {
            //Bug fix ,查询关键字使用一下 QueryParser.escape(keyword),例如：keyword=I/O,否则buildQuery时会出现异常
            keyword = QueryParser.escape(keyword);
            indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            Query query = buildQuery(keyword);

            ScoreDoc lastScoreDoc = getLastScoreDoc(pageNum, pageSize, query, indexSearcher);
            TopDocs topDocs = indexSearcher.searchAfter(lastScoreDoc, query, pageSize);

            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font class=\"" + HIGH_LIGHT_CLASS + "\">", "</font>");
            Highlighter highlighter = new Highlighter(formatter, new QueryScorer(query));
            highlighter.setTextFragmenter(new SimpleFragmenter(100));

            List<Article> articles = toArticleList(indexSearcher, topDocs, highlighter);
            int totalRow = getTotalRow(indexSearcher, query);
            return newPage(pageNum, pageSize, totalRow, articles);
        } catch (Exception e) {
            LOG.error(e.toString(), e);
        } finally {
            quietlyClose(indexReader);
        }
        return null;
    }

    @Override
    public boolean isEnable() {
        try {
            String config = ConfigUtils.getConfig(ENABLE_LUCENE_SEARCHER);
            return Boolean.parseBoolean(config);
        } catch (Exception e) {
            return true;
        }
    }

    private ScoreDoc getLastScoreDoc(int pageIndex, int pageSize, Query query, IndexSearcher indexSearcher) throws Exception {
        if (pageIndex == 1) {
            return null; // 如果是第一页返回空
        }
        int num = pageSize * (pageIndex - 1); // 获取上一页的数量
        TopDocs tds = indexSearcher.search(query, num);
        return tds.scoreDocs[num - 1];
    }

    public int getTotalRow(IndexSearcher searcher, Query query) throws Exception {
        TopDocs topDocs = searcher.search(query, 1000);
        if (topDocs == null || topDocs.scoreDocs == null || topDocs.scoreDocs.length == 0) {
            return 0;
        }
        ScoreDoc[] docs = topDocs.scoreDocs;
        return docs.length;
    }

    private Page<Article> newPage(int pageNum, int pageSize, int totalRow, List<Article> articles) {
        return new Page<Article>(pageNum, pageSize, totalRow).setRecords(articles);
    }

    private Document createDocument(Article article) {
        Document doc = new Document();
        doc.add(new StringField(ARTICLE_ID, article.getId().toString(), Field.Store.YES));
        doc.add(new TextField(ARTICLE_CONTENT, article.getContent(), Field.Store.YES));
        doc.add(new TextField(ARTICLE_TEXT, article.getText(), Field.Store.YES));
        doc.add(new TextField(ARTICLE_TITLE, article.getTitle(), Field.Store.YES));
        LocalDateTime createdDate = article.getUpdated() == null ? LocalDateTime.now() : article.getUpdated();
        Date created = Date.from(createdDate.atZone(ZoneId.systemDefault()).toInstant());
        doc.add(new StringField(ARTICLE_CREATED, DateTools.dateToString(created, DateTools.Resolution.YEAR), Field.Store.NO));
        return doc;
    }

    private IndexWriter createIndexWriter() throws Exception {
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        return new IndexWriter(directory, iwc);
    }

    private Query buildQuery(String keyword) {
        try {
            //这里使用text，防止搜索出html的tag或者tag中属性
            QueryParser queryParser1 = new QueryParser(ARTICLE_TEXT, analyzer);
            Query termQuery1 = queryParser1.parse(keyword);
            BooleanClause booleanClause1 = new BooleanClause(termQuery1, BooleanClause.Occur.SHOULD);

            QueryParser queryParser2 = new QueryParser(ARTICLE_TITLE, analyzer);
            Query termQuery2 = queryParser2.parse(keyword);
            BooleanClause booleanClause2 = new BooleanClause(termQuery2, BooleanClause.Occur.SHOULD);

            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            builder.add(booleanClause1).add(booleanClause2);

            return builder.build();
        } catch (ParseException e) {
            LOG.error(e.toString(), e);
        }
        return null;
    }

    private List<Article> toArticleList(IndexSearcher searcher, TopDocs topDocs, Highlighter highlighter) throws Exception {
        List<Article> articles = new ArrayList<>();
        for (ScoreDoc item : topDocs.scoreDocs) {

            Document doc = searcher.doc(item.doc);

            Article article = new Article();
            String title = doc.get(ARTICLE_TITLE);
            String content = doc.get(ARTICLE_CONTENT);
            article.setId(Long.valueOf(doc.get(ARTICLE_ID)));
            article.setTitle(title);
            article.setContentHtml(content);

            //关键字高亮
            try {
                String highlightTitle = highlighter.getBestFragment(analyzer, ARTICLE_TITLE, title);
                article.setHighlightTitle(highlightTitle);
            } catch (InvalidTokenOffsetsException e) {
                // ignore
            }

            try {
                String text = article.getText();
                String highlightContent = highlighter.getBestFragment(analyzer,ARTICLE_CONTENT, text);
                article.setHighlightContent(highlightContent);
            } catch (InvalidTokenOffsetsException e) {
                // ignore
            }

            articles.add(article);
        }
        return articles;
    }

    public static void quietlyClose(AutoCloseable... autoCloseables) {
        for (AutoCloseable closeable : autoCloseables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }


}
