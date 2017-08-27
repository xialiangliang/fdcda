package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.model.base.PaginationQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zzq on 2017-08-26.
 */
@Controller
@RequestMapping("/analysis_report")
public class AnalysisReportController {
    private final static Logger logger = LoggerFactory.getLogger(AnalysisReportController.class);


    @RequestMapping
    public String index(Model model, PaginationQuery query) {
        return "/page/analysis_report";
    }
}
