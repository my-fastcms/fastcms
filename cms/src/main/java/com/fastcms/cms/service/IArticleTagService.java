package com.fastcms.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.ArticleTag;

import java.util.List;

/**
 * 文章标签服务类
 * @author wjun_java@163.com
 * @since 2021-12-16
 */
public interface IArticleTagService extends IService<ArticleTag> {

    /**
     * 根据名称查找标签
     * 没有找到就new一个
     * @param tagName
     * @return
     */
    ArticleTag getByName(String tagName);

    /**
     * 删除分类
     * @param articleTagId
     */
    void deleteByTagId(Long articleTagId);

    /**
     * 获取文章已设置的分类
     * @param articleId
     * @return
     */
    List<ArticleTag> getArticleTagListByArticleId(Long articleId);

}
