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
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.utils.ConfigUtils;

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
     * @throws Exception
     */
    void saveArticle(Article article) throws Exception;

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
     * 网站获取文章分页列表
      * @param pageParam
     * @param queryWrapper
     * @return
     */
    Page<ArticleVo> pageArticleByCategoryId(Page pageParam, QueryWrapper queryWrapper);

    /**
     * 根据分类id查询文章列表
     * @param queryWrapper
     * @return
     */
    List<ArticleVo> getArticleListByCategoryId(QueryWrapper queryWrapper);

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
    class ArticleVo implements Serializable {
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
         * 文章地址
         */
        String url;
        /**
         * 缩略图
         */
        String thumbnail;
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

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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

        public String getUrl() {
            return ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN) + "/a/" + getId();
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

}
