package com.dbumama.market.web.core.render.excel.filter;

import java.util.List;

public interface RowFilter {
    boolean doFilter(int rowNum, List<String> list);
}
