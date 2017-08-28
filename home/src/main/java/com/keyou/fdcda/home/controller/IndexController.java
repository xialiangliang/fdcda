package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.utils.SessionUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import com.keyou.fdcda.home.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by zzq on 2017-07-08.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private UrlConfig urlConfig;


    @RequestMapping
    public String index(Model model) {
        
        return "/page/index";
    }
}
