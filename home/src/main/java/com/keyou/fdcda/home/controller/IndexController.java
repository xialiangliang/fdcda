package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.service.UserService;
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
 * Created by Wataru on 2017-07-08.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    UserService userService;

    @RequestMapping("")
    public String index(Model model) {
        logger.info("logback");
        model.addAttribute("info", userService.test());
        return "index";
    }
    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> index2() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "Success");
        map.put("data", userService.test());
        return map;
    }
}
