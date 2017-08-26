package com.keyou.fdcda.api.model.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zzq on 2017-08-26.
 */
public class PageResult<T> implements Serializable {
    private static final Integer DEFAULT_ROWS_PER_PAGE = 15;
    private Integer pageIndex;
    private Integer rowsPerPage;
    private Long rowCount;
    private Integer pageSize;
    private List<T> rows;
    private PaginationQuery query;
    
    public PageResult(List<T> rows, PaginationQuery query, Long rowCount) {
        this.pageIndex = query.getPageIndex();
        this.rowsPerPage = query.getRowsPerPage() == null ? DEFAULT_ROWS_PER_PAGE : query.getRowsPerPage();
        this.rowCount = rowCount;
        this.pageSize = (rowCount > 0 ? rowCount.intValue() - 1 : 1) / this.pageIndex;
        this.rows = rows;
        this.query = query;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public Long getRowCount() {
        return rowCount;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public PaginationQuery getQuery() {
        return query;
    }

    public void setQuery(PaginationQuery query) {
        this.query = query;
    }
}
