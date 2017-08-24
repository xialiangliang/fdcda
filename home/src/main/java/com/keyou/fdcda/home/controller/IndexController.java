package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.model.User;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.service.UserService;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzq on 2017-07-08.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UrlConfig urlConfig;

    @RequestMapping
    public String index(Model model) {
        logger.info(urlConfig.getWebPath());
        User user = userService.test();
        model.addAttribute("info", user);
        redisService.set("111", user, 5000);
        return "/page/index";
    }
    
    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> index2() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "Success");
        map.put("data", redisService.get("111", User.class));
        return map;
    }
}
