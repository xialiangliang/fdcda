package com.keyou.fdcda.api.model.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzq on 2017-08-26.
 */
public class PaginationQuery implements Serializable {
    static final long serialVersionUID = 1L;
    
    private Map<String, Object> queryData = new HashMap<>();
    
    private Integer pageIndex;
    
    private Integer rowsPerPage;

    private static final Integer DEFAULT_ROWS_PER_PAGE = 15;

    public Map<String, Object> getQueryData() {
        return queryData;
    }

    public void setQueryData(Map<String, Object> queryData) {
        this.queryData = queryData;
    }

    public Integer getPageIndex() {
        return pageIndex == null ? 1 : pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage == null ? DEFAULT_ROWS_PER_PAGE : rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
    
    public void addQueryData(String key, Object value) {
        queryData.put(key, value);
    }
}
