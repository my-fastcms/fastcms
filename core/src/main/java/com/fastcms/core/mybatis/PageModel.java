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
    Long pageNum = 1l;

    /**
     * 每页条数
     */
    Long pageSize = 10l;

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Page toPage() {
        return new Page(getPageNum() == null ? 1l : getPageNum(), getPageSize() == null ? 10l : getPageSize());
    }
}
