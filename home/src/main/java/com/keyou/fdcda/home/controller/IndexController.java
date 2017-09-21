package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.constants.RedisConstants;
import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.utils.RandomUtil;
import com.keyou.fdcda.api.utils.SessionUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import com.keyou.fdcda.home.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("modifyPassword")
    public String modifyPassword(Model model, HttpServletRequest request) {
        String salt = RandomUtil.produceString(64);
        model.addAttribute("salt", salt);
        SysUser sysUser = (SysUser) request.getSession().getAttribute(Constants.SESSION_USER);
        redisService.set(RedisConstants.MODIFY_PASSWORD_SALT + sysUser.getId(), salt, 10 * 1000);
        return "/page/modifyPassword";
    }


    @RequestMapping(value="modifyPassword/confirm", method= RequestMethod.POST)
    public Map<String, Object> modifyPasswordConfirm() {

        return null;
    }
}
