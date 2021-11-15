package com.fastcms.core.mybatis;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;

/**
 * wjun_java@163.com
 */
public class PageModel implements Serializable {

    /**
     * 页码
     */
    Long page = 1l;

    /**
     * 每页条数
     */
    Long pageSize = 10l;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Page toPage() {
        return new Page(getPage() == null ? 1l : getPage(), getPageSize() == null ? 10l : getPageSize());
    }
}
