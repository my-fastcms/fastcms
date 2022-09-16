package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.cms.entity.ArticleTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章标签Mapper 接口
 * @author wjun_java@163.com
 * @since 2021-12-16
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    void deleteRelationByTagId(Long articleTagId);

    List<ArticleTag> getArticleTagListByArticleId(@Param("articleId") Long articleId);

}
