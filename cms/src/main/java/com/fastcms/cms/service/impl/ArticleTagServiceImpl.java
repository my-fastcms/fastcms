package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.ArticleTag;
import com.fastcms.cms.mapper.ArticleTagMapper;
import com.fastcms.cms.service.IArticleTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章标签服务实现类
 * @author wjun_java@163.com
 * @since 2021-12-16
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

    @Override
    public ArticleTag getByName(String tagName) {
        ArticleTag one = getOne(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getTagName, tagName));
        if(one == null) {
            one = new ArticleTag();
            one.setTagName(tagName);
        }
        return one;
    }

    @Override
    @Transactional
    public void deleteByTagId(Long articleTagId) {
        removeById(articleTagId);
        getBaseMapper().deleteRelationByTagId(articleTagId);
    }

    @Override
    public List<ArticleTag> getArticleTagListByArticleId(Long articleId) {
        return getBaseMapper().getArticleTagListByArticleId(articleId);
    }

}
