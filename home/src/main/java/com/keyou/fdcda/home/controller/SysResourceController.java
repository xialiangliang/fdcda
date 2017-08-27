package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.model.base.PaginationQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zzq on 2017-08-27.
 */
@Controller
@RequestMapping("/sys_resource")
public class SysResourceController {
    private final static Logger logger = LoggerFactory.getLogger(SysResourceController.class);


    @RequestMapping
    public String index(Model model, PaginationQuery query) {
        return "/page/sys_resource";
    }
}
