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
package com.fastcms.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.ArticleTag;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.core.template.StaticPathHelper;
import com.fastcms.core.utils.AttachUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface IArticleService extends IService<Article> {

    /**
     * 保存文章信息
     * @param article
     * @throws FastcmsException
     */
    boolean saveArticle(Article article) throws FastcmsException;

    /**
     * 获取文章
     * @param articleId
     * @return
     */
    Article getArticle(Long articleId);

    /**
     * 分页查询文章列表数据
     * @param pageParam
     * @param queryWrapper
     * @return
     */
    Page<ArticleVo> pageArticle(Page pageParam, QueryWrapper queryWrapper);

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    ArticleInfoVo getArticleDetail(Long id);

    /**
     * 根据分类获取网站文章分页列表
      * @param pageParam
     * @param queryWrapper
     * @return
     */
    Page<ArticleVo> pageArticleByCategoryId(Page pageParam, QueryWrapper queryWrapper);

    /**
     * 根据分类查询文章列表
     * @param categoryId    查询具体分类
     * @param includeIds    查询包含分类
     * @param excludeIds    查询排查分类
     * @param count         查询数量
     * @param orderBy       排序条件
     * @return
     */
    List<ArticleVo> getArticleListByCategoryId(Long categoryId, List<Long> includeIds, List<Long> excludeIds, Integer count, String orderBy);

    /**
     * 根据分类ID查询文章列表
     * @param categoryId    查询具体分类
     * @param count         查询数量
     * @param orderBy       排序条件
     * @return
     */
    List<ArticleVo> getArticleListByCategoryId(Long categoryId, Integer count, String orderBy);

    /**
     * 根据分类id查询文章列表
     * @param categoryId
     * @param orderBy
     * @return
     */
    List<ArticleVo> getArticleListByCategoryId(Long categoryId, String orderBy);

    /**
     * 根据标签获取网站文章分页列表
     * @param pageParam
     * @param queryWrapper
     * @return
     */
    Page<ArticleVo> pageArticleByTagId(Page pageParam, QueryWrapper queryWrapper);

    /**
     * 根据标签获取网站文章列表
     * @param tagId
     * @param includeIds
     * @param excludeIds
     * @param count
     * @param orderBy
     * @return
     */
    List<ArticleVo> getArticleListByTagId(Long tagId, List<Long> includeIds, List<Long> excludeIds, Integer count, String orderBy);

    /**
     * 根据标签id获取文章列表
     * @param tagId
     * @return
     */
    List<ArticleVo> getArticleListByTagId(Long tagId, Integer count, String orderBy);

    /**
     * 更新文章浏览数量
     * @param id
     * @param count
     */
    void updateViewCount(Long id, Long count);

    /**
     * 获取文章统计数量
     * @return
     */
    ArticleStatVo getArticleStatData();

    /**
     * 文章数量统计
     */
    class ArticleStatVo implements Serializable {
        /**
         * 文章总数
         */
        Long totalCount;
        /**
         * 当日文章总数
         */
        Long todayCount;
        /**
         * 文章总浏览量
         */
        Long totalViewCount;
        /**
         * 当日文章总浏览量
         */
        Long todayViewCount;

        public Long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Long totalCount) {
            this.totalCount = totalCount;
        }

        public Long getTodayCount() {
            return todayCount;
        }

        public void setTodayCount(Long todayCount) {
            this.todayCount = todayCount;
        }

        public Long getTotalViewCount() {
            return totalViewCount;
        }

        public void setTotalViewCount(Long totalViewCount) {
            this.totalViewCount = totalViewCount;
        }

        public Long getTodayViewCount() {
            return todayViewCount;
        }

        public void setTodayViewCount(Long todayViewCount) {
            this.todayViewCount = todayViewCount;
        }
    }

    /**
     * 文章
     */
    class ArticleVo implements Serializable, StaticPathHelper {
        /**
         * id
         */
        Long id;
        /**
         * 标题
         */
        String title;
        /**
         * 浏览次数
         */
        Integer viewCount;
        /**
         * 状态
         */
        String status;
        /**
         * 创建时间
         */
        LocalDateTime created;
        /**
         * 作者
         */
        String author;
        /**
         * 文章外部链接
         */
        String outLink;
        /**
         * 缩略图
         */
        String thumbnail;
        /**
         * 文章摘要
         */
        String summary;
        /**
         * 扩展字段
         */
        String jsonExt;
        /**
         * 文章所属分类
         */
        List<ArticleCategory> categoryList;
        /**
         * 文章所属标签
         */
        List<ArticleTag> tagList;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getViewCount() {
            return viewCount;
        }

        public void setViewCount(Integer viewCount) {
            this.viewCount = viewCount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getCreated() {
            return created;
        }

        public void setCreated(LocalDateTime created) {
            this.created = created;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getJsonExt() {
            return jsonExt;
        }

        public void setJsonExt(String jsonExt) {
            this.jsonExt = jsonExt;
        }

        public List<ArticleCategory> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<ArticleCategory> categoryList) {
            this.categoryList = categoryList;
        }

        public List<ArticleTag> getTagList() {
            return tagList;
        }

        public void setTagList(List<ArticleTag> tagList) {
            this.tagList = tagList;
        }

        public String getOutLink() {
            return outLink;
        }

        public void setOutLink(String outLink) {
            this.outLink = outLink;
        }

        @Override
        public String getUrl() {
            if (StringUtils.isNotBlank(getOutLink())) {
                return getOutLink();
            }

            String url = getWebSiteDomain().concat(getArticleStaticPath()) + getId();
            if (isEnable()) {
                url = url + getStaticSuffix();
            }

            return url;
        }

        public String getStatusStr() {
            return Article.ArticleStatus.getValue(getStatus());
        }

        public String getThumbnailUrl() {
            return AttachUtils.getAttachFileDomain() + getThumbnail();
        }
    }

    /**
     * 文章详情
     */
    class ArticleInfoVo extends Article {
        String author;
        String headImg;
        List<ArticleCategory> categoryList;
        List<ArticleTag> tagList;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public List<ArticleCategory> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<ArticleCategory> categoryList) {
            this.categoryList = categoryList;
        }

        public List<ArticleTag> getTagList() {
            return tagList;
        }

        public void setTagList(List<ArticleTag> tagList) {
            this.tagList = tagList;
        }

    }

    interface ArticleI18n {
        String CMS_ARTICLE_IS_NOT_EXIST = "fastcms.cms.article.is.not.exist";
        String CMS_ARTICLE_CHILDREN_NOT_DELETE = "fastcms.cms.article.children.not.delete";
        String CMS_ARTICLE_CHILDREN_COMMENT_NOT_DELETE = "fastcms.cms.article.children.comment.not.delete";
        String CMS_ARTICLE_IS_ALLOW_SELF_UPDATE = "fastcms.cms.article.is.allow.self.update";
        String CMS_ARTICLE_IS_ALLOW_SELF_DELETE = "fastcms.cms.article.is.allow.self.delete";

        String CMS_ARTICLE_COMMENT_DISABLE = "fastcms.cms.article.comment.disable";
        String CMS_ARTICLE_COMMENT_ARTICLE_ID_NOT_ALLOW_NULL = "fastcms.cms.article.comment.article.id.is.not.allow.null";
        String CMS_ARTICLE_COMMENT_CONTENT_IS_NOT_ALLOW_NULL = "fastcms.cms.article.comment.content.is.not.allow.null";
        String CMS_ARTICLE_COMMENT_IS_DISABLE = "fastcms.cms.article.comment.is.not.allow.comment";

    }

}
