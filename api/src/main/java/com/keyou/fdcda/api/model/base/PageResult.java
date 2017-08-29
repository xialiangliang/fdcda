package com.keyou.fdcda.api.model.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zzq on 2017-08-26.
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rowCount;
    private List<T> rows;
    private int pageSize;
    private int rowsPerPage = 15;
    private int curPageNum = 1;
    private String queryParameters;

    public PageResult() {
    }

    public PageResult(List<T> rows, int rowCount, PaginationQuery query) {
        this.rows = rows;
        this.rowCount = rowCount;
        this.rowsPerPage = query.getRowsPerPage();
        this.curPageNum = query.getPageIndex();
        this.queryParameters = query.getQueryParameters();
        this.countPageSize();
    }

    public Paginator getPaginator() {
        return new Paginator(this.curPageNum, this.rowsPerPage, this.rowCount);
    }

    public int getCurPageNum() {
        return this.curPageNum;
    }

    public void setCurPageNum(int curPageNum) {
        this.curPageNum = curPageNum;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.countPageSize();
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    private void countPageSize() {
        if(this.rowsPerPage == 0) {
            this.rowsPerPage = 15;
        }

        if(this.rowCount % this.rowsPerPage == 0) {
            this.pageSize = this.rowCount / this.rowsPerPage;
        } else {
            this.pageSize = this.rowCount / this.rowsPerPage + 1;
        }

        if(this.curPageNum > this.pageSize) {
            this.curPageNum = this.pageSize;
        }

        if(this.curPageNum < 1) {
            this.curPageNum = 1;
        }

    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowsPerPage() {
        return this.rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public String getQueryParameters() {
        return this.queryParameters;
    }

    public void setQueryParameters(String queryParameters) {
        this.queryParameters = queryParameters;
    }

    public static boolean isNotNull(PageResult page) {
        boolean result = false;
        if(page != null && page.getRows() != null && page.getRows().size() > 0) {
            result = true;
        }

        return result;
    }

}
