package com.keyou.fdcda.api.model.base;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zzq on 2017-08-26.
 */
public class PaginationQuery implements Serializable {
        private static final long serialVersionUID = 1L;
        public static final int DESCENDING = 1;
        public static final int ASCENDING = 2;
        public static final int DEFAULT_ROWS_PER_PAGE = 15;
        private int rowsPerPage = 15;
        protected int pageIndex = 1;
        private int orderBy;
        private Map<String, Object> queryData = new HashMap();

        public PaginationQuery() {
        }

        public PaginationQuery(int pageNo, int pageSize) {
            this.pageIndex = pageNo;
            this.rowsPerPage = pageSize;
        }

        public int getRowsPerPage() {
            if(this.rowsPerPage == 0) {
                this.rowsPerPage = 15;
            }

            return this.rowsPerPage;
        }

        public void setRowsPerPage(int pageSize) {
            this.rowsPerPage = pageSize;
        }

        public int getPageIndex() {
            return this.pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            if(pageIndex < 1) {
                this.pageIndex = 1;
            } else {
                this.pageIndex = pageIndex;
            }

        }

        public int getOrderBy() {
            return this.orderBy;
        }

        public void setOrderBy(int orderBy) {
            this.orderBy = orderBy;
        }

        public Map<String, Object> getQueryData() {
            return this.queryData;
        }

        public void setQueryData(Map<String, Object> queryData) {
            this.queryData = queryData;
        }

        public String getQueryParameters() {
            if(this.queryData.containsKey("startRecord")) {
                this.queryData.remove("startRecord");
            }

            if(this.queryData.containsKey("endRecord")) {
                this.queryData.remove("endRecord");
            }

            StringBuffer sb = new StringBuffer();
            if (this.rowsPerPage != DEFAULT_ROWS_PER_PAGE) {
                this.queryData.put("rowsPerPage", this.rowsPerPage);
            }
            if(!this.queryData.isEmpty()) {
                Iterator i = this.queryData.keySet().iterator();

                while(i.hasNext()) {
                    String key = (String)i.next();
                    if(null != this.queryData.get(key)) {
                        sb.append(key);
                        sb.append("=");
                        sb.append(this.queryData.get(key));
                        if(i.hasNext()) {
                            sb.append("&");
                        }
                    }
                }
            }

            String str = sb.toString();
            if(str != null && !"".equals(str)) {
                if(str.lastIndexOf("&") == str.length() - 1) {
                    str = str.substring(0, str.length() - 1);
                }

                return str;
            } else {
                return null;
            }
        }

        public void addQueryData(String key, Object value) {
            if(null != key && !"".equals(key)) {
                this.queryData.put(key, value);
            }

        }

        public void removeQueryData(String key) {
            if(null != key && !"".equals(key)) {
                this.queryData.remove(key);
            }

        }

        public void updateQueryData(String key, Object value) {
            if(null != key && !"".equals(key)) {
                this.queryData.remove(key);
            }

            if(null != key && !"".equals(key)) {
                this.queryData.put(key, value);
            }

        }
}
