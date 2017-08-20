package com.keyou.fdcda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.keyou.fdcda.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wataru on 2017-07-08.
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    UserService userService;

    @RequestMapping("/index")
    public ModelAndView index() {
//        return userService.test();
        ModelAndView model = new ModelAndView();
        model.addObject("info", userService.test());
        model.setViewName("index");
        return model;
    }

    @RequestMapping("/index/index")
    @ResponseBody
    public Map<String, Object> index2() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "Success");
        map.put("data", userService.test());
        return map;
    }
}
