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
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.JsoupUtils;

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
    ArticleInfoVo getArticleById(Long id);

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

    class ArticleVo implements Serializable {
        Long id;
        String title;
        Integer viewCount;
        String status;
        LocalDateTime created;
        String author;
        String url;
        String thumbnail;
        List<ArticleCategory> categoryList;

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

        public String getUrl() {
            return "/a/" + getId();
        }

    }

    class ArticleInfoVo extends Article {
        String author;
        String headImg;
        String headImgUrl;
        String contentHtmlView;
        List<ArticleCategory> categoryList;

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

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public void setContentHtmlView(String contentHtmlView) {
            this.contentHtmlView = contentHtmlView;
        }

        public List<ArticleCategory> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<ArticleCategory> categoryList) {
            this.categoryList = categoryList;
        }

        public String getHeadImgUrl() {
            return FastcmsConstants.STATIC_RESOURCE_PATH + getHeadImg();
        }

        public String getContentHtmlView() {
            return JsoupUtils.parse(contentHtmlView);
        }
    }

}
