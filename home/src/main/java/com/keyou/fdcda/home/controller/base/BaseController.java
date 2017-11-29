package com.keyou.fdcda.home.controller.base;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import org.slf4j.Logger;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zzq on 2017-08-28.
 */
public class BaseController {
    public void commonError(Logger logger, Exception e, String msg, Map<String, Object> map) {
        map.put(Constants.SUCCESS, false);
        map.put(Constants.MESSAGE, msg + ":" + e.getMessage());
        logger.error(msg, e);
    }

    public void commonError(Logger logger, Exception e, String msg, Model model) {
        model.addAttribute(Constants.SUCCESS, false);
        model.addAttribute(Constants.MESSAGE, msg + ":" + e.getMessage());
        logger.error(msg, e);
    }
    
    public SysUser getUser(HttpServletRequest request) {
        return (SysUser) request.getSession().getAttribute(Constants.SESSION_USER);
    }
    
    protected void formatPageQuery(PaginationQuery query, Integer page, Integer limit) {
        if (page != null || page > 0) {
            query.setPageIndex(page);
        }
        if (limit != null || limit > 0) {
            query.setRowsPerPage(limit);
        }
    }
}
